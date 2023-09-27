package com.linklyze.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.account.api.v1.request.PlaceOrderRequest;
import com.linklyze.account.api.v1.request.ProductOrderPageRequest;
import com.linklyze.account.mapper.TrafficMapper;
import com.linklyze.account.mapper.TrafficPackageMapper;
import com.linklyze.account.model.ProductOrderDO;
import com.linklyze.account.mapper.ProductOrderMapper;
import com.linklyze.account.model.TrafficPackageDO;
import com.linklyze.account.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linklyze.common.LocalUserThreadHolder;
import com.linklyze.common.constant.Constants;
import com.linklyze.common.exception.BizException;
import com.linklyze.common.model.bo.PayParam;
import com.linklyze.common.util.CommonUtil;
import com.linklyze.common.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author novo
 * @since 2023-09-19
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {

    private final ProductOrderMapper productOrderMapper;

    private final TrafficPackageMapper trafficPackageMapper;

    public ProductOrderServiceImpl(ProductOrderMapper productOrderMapper, TrafficPackageMapper trafficPackageMapper) {
        this.productOrderMapper = productOrderMapper;
        this.trafficPackageMapper = trafficPackageMapper;
    }

    @Override
    public int create(ProductOrderDO productOrderDO) {
        return this.productOrderMapper.insert(productOrderDO);
    }

    @Override
    public ProductOrderDO findByOutTradeNo(String outTradeNo) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        return this.productOrderMapper.selectOne(new QueryWrapper<ProductOrderDO>()
                .lambda()
                .eq(ProductOrderDO::getAccountNo, accountNo)
                .eq(ProductOrderDO::getOutTradeNo, outTradeNo));
    }

    @Override
    public int updateState(String outTradeNo, String newState, String oldState) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        return this.productOrderMapper.update(null, new UpdateWrapper<ProductOrderDO>()
                .lambda()
                .eq(ProductOrderDO::getAccountNo, accountNo)
                .eq(ProductOrderDO::getOutTradeNo, outTradeNo)
                .eq(ProductOrderDO::getState, oldState).set(ProductOrderDO::getState, newState));
    }

    @Override
    public Page<ProductOrderDO> page(ProductOrderPageRequest pageRequest) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        Page<ProductOrderDO> request = new Page<>(pageRequest.getPage(), pageRequest.getSize());
        LambdaQueryWrapper<ProductOrderDO> wrapper = new QueryWrapper<ProductOrderDO>()
                .lambda()
                .eq(ProductOrderDO::getAccountNo, accountNo)
                .orderByDesc(ProductOrderDO::getCreateTime);
        if (pageRequest.getState() != null) {
            wrapper.eq(ProductOrderDO::getState, pageRequest.getState());
        }
        Page<ProductOrderDO> response = this.productOrderMapper
                .selectPage(request, wrapper);
        return response;
    }

    /**
     * 1. 防止表单重复提交 TODO
     * 2. 计算价格
     * 3. 订单入库
     * @param request
     */
    @Override
    @Transactional
    public void placeOrder(PlaceOrderRequest request) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        TrafficPackageDO trafficPackageDO = trafficPackageMapper.selectById(request.getProductId());
        // 计算价格
        checkPrice(request, trafficPackageDO);
        // 订单入库
        String orderOutTradeNo = CommonUtil.getStringNumRandom(32);
        saveOrderToDB(orderOutTradeNo, request, trafficPackageDO);
        // 调起支付
        PayParam payParam = PayParam.builder()
                .orderOutTradeNo(orderOutTradeNo)
                .accountNo(accountNo)
                .payAmount(request.getPayAmount())
                .payType(request.getPayType())
                .title(trafficPackageDO.getTitle())
                .description(trafficPackageDO.getDetail())
                .timeOut(Constants.PLACE_ORDER_TIME_OUT)
                .build();

    }

    private void saveOrderToDB(String orderOutTradeNo, PlaceOrderRequest request, TrafficPackageDO trafficPackageDO) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        ProductOrderDO productOrderDO = new ProductOrderDO();
        BeanUtils.copyProperties(request, productOrderDO);
        productOrderDO.setAccountNo(accountNo);
        productOrderDO.setProductSnapshot(JsonUtil.obj2Json(trafficPackageDO));
        productOrderDO.setOutTradeNo(orderOutTradeNo);
        productOrderMapper.insert(productOrderDO);
    }

    private void checkPrice(PlaceOrderRequest request, TrafficPackageDO trafficPackageDO) {
        BigDecimal serverTotal = BigDecimal.valueOf(request.getBuyNum()).multiply(trafficPackageDO.getAmount());
        if (serverTotal.compareTo(request.getPayAmount()) != 0) {
            throw new BizException("价格已经变动，请重新下单");
        }
    }
}
