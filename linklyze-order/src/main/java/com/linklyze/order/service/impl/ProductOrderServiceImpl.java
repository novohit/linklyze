package com.linklyze.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.order.api.request.PlaceOrderRequest;
import com.linklyze.order.api.request.ProductOrderPageRequest;
import com.linklyze.order.mapper.TrafficPackageMapper;
import com.linklyze.order.model.ProductOrderDO;
import com.linklyze.order.mapper.ProductOrderMapper;
import com.linklyze.order.model.TrafficPackageDO;
import com.linklyze.order.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linklyze.order.service.strategy.PayStrategyFactory;
import com.linklyze.common.LocalUserThreadHolder;
import com.linklyze.common.constant.Constants;
import com.linklyze.common.enums.MessageEventType;
import com.linklyze.common.enums.PayStateEnum;
import com.linklyze.common.exception.BizException;
import com.linklyze.order.service.strategy.PayRequest;
import com.linklyze.common.model.bo.CustomMessage;
import com.linklyze.common.util.CommonUtil;
import com.linklyze.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author novo
 * @since 2023-09-19
 */
@Service
@Slf4j
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {

    private final ProductOrderMapper productOrderMapper;

    private final TrafficPackageMapper trafficPackageMapper;

    private final PayStrategyFactory payStrategyFactory;

    public ProductOrderServiceImpl(ProductOrderMapper productOrderMapper,
                                   TrafficPackageMapper trafficPackageMapper,
                                   PayStrategyFactory payStrategyFactory) {
        this.productOrderMapper = productOrderMapper;
        this.trafficPackageMapper = trafficPackageMapper;
        this.payStrategyFactory = payStrategyFactory;
    }

    @Override
    public int create(ProductOrderDO productOrderDO) {
        return this.productOrderMapper.insert(productOrderDO);
    }

    /**
     * 这里的 accountNo 要为参数传入，因为会被消费者调用
     * @param accountNo
     * @param outTradeNo
     * @return
     */
    @Override
    public ProductOrderDO findByOutTradeNo(Long accountNo, String outTradeNo) {
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
     *
     * @param request
     */
    @Override
    @Transactional
    public String placeOrder(PlaceOrderRequest request) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        TrafficPackageDO trafficPackageDO = trafficPackageMapper.selectById(request.getProductId());
        if (trafficPackageDO == null) {
            throw new BizException("流量包套餐不存在");
        }
        // 计算价格
        checkPrice(request, trafficPackageDO);
        // 订单入库
        String orderOutTradeNo = CommonUtil.getStringNumRandom(32);
        saveOrderToDB(orderOutTradeNo, request, trafficPackageDO);
        // 调起支付
        PayRequest payRequest = PayRequest.builder()
                .orderOutTradeNo(orderOutTradeNo)
                .accountNo(accountNo)
                .actualPayAmount(request.getActualPayAmount())
                .payType(request.getPayType())
                .title(trafficPackageDO.getTitle())
                .description(trafficPackageDO.getDetail())
                .timeOut(Constants.PLACE_ORDER_TIME_OUT)
                .build();
        return payStrategyFactory.chooseStrategy(request.getPayType())
                .pay(payRequest)
                .getBody();
    }

    @Override
    public void changeOrderState(CustomMessage message) {
        Long accountNo = message.getAccountNo();
        if (MessageEventType.ORDER_PAID.equals(message.getEventType())) {
            // 支付成功
            String outTradeNo = message.getBizId();
            int rows = productOrderMapper.update(null, new UpdateWrapper<ProductOrderDO>()
                    .lambda()
                    .eq(ProductOrderDO::getAccountNo, accountNo)
                    .eq(ProductOrderDO::getOutTradeNo, outTradeNo)
                    .eq(ProductOrderDO::getState, PayStateEnum.NEW)
                    .set(ProductOrderDO::getPayTime, LocalDateTime.now())
                    .set(ProductOrderDO::getState, PayStateEnum.PAID));
            if (rows > 0) {
                log.info(
                        "订单状态更新成功 NEW->PAID，订单号：{}",
                        outTradeNo
                );
            } else {
                log.error(
                        "订单状态更新失败 NEW->PAID，订单号：{}",
                        outTradeNo
                );
            }
        } else if (MessageEventType.ORDER_CANCEL.equals(message.getEventType())) {
            // 订单超时取消
        } else {

        }
    }

    private void saveOrderToDB(String orderOutTradeNo, PlaceOrderRequest request, TrafficPackageDO trafficPackageDO) {
        Long accountNo = LocalUserThreadHolder.getLocalUserNo();
        ProductOrderDO productOrderDO = new ProductOrderDO();
        BeanUtils.copyProperties(request, productOrderDO);
        productOrderDO.setAccountNo(accountNo);
        productOrderDO.setProductSnapshot(JsonUtil.obj2Json(trafficPackageDO));
        productOrderDO.setOutTradeNo(orderOutTradeNo);
        productOrderDO.setProductTitle(trafficPackageDO.getTitle());
        productOrderDO.setProductAmount(trafficPackageDO.getAmount());
        productOrderDO.setState(PayStateEnum.NEW);
        productOrderMapper.insert(productOrderDO);
    }

    private void checkPrice(PlaceOrderRequest request, TrafficPackageDO trafficPackageDO) {
        BigDecimal serverTotal = BigDecimal.valueOf(request.getBuyNum()).multiply(trafficPackageDO.getAmount());
        if (serverTotal.compareTo(request.getActualPayAmount()) != 0) {
            throw new BizException("价格已经变动，请重新下单");
        }
    }
}
