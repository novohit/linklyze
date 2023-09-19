package com.linklyze.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linklyze.account.api.v1.request.ProductOrderPageRequest;
import com.linklyze.account.model.ProductOrderDO;

/**
 * @author novo
 * @since 2023-09-19
 */
public interface ProductOrderService extends IService<ProductOrderDO> {

    int create(ProductOrderDO productOrderDO);

    ProductOrderDO findByOutTradeNo(String outTradeNo);

    int updateState(String outTradeNo, String newState, String oldState);

    Page<ProductOrderDO> page(ProductOrderPageRequest pageRequest);

}
