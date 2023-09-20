package com.linklyze.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linklyze.account.api.v1.request.ProductOrderPageRequest;
import com.linklyze.account.model.ProductOrderDO;
import com.linklyze.account.mapper.ProductOrderMapper;
import com.linklyze.account.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linklyze.common.LocalUserThreadHolder;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-09-19
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {

    private final ProductOrderMapper productOrderMapper;

    public ProductOrderServiceImpl(ProductOrderMapper productOrderMapper) {
        this.productOrderMapper = productOrderMapper;
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
        Page<ProductOrderDO> response = this.productOrderMapper
                .selectPage(request,
                        new QueryWrapper<ProductOrderDO>().lambda()
                                .eq(ProductOrderDO::getAccountNo, accountNo)
                                .eq(ProductOrderDO::getState, pageRequest.getState())
                                .orderByDesc(ProductOrderDO::getCreateTime));
        return response;
    }
}
