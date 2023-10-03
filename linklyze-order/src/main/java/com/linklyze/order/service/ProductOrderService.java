package com.linklyze.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linklyze.order.api.request.PlaceOrderRequest;
import com.linklyze.order.api.request.ProductOrderPageRequest;
import com.linklyze.order.model.ProductOrderDO;
import com.linklyze.common.model.bo.CustomMessage;

/**
 * @author novo
 * @since 2023-09-19
 */
public interface ProductOrderService extends IService<ProductOrderDO> {

    int create(ProductOrderDO productOrderDO);

    ProductOrderDO findByOutTradeNo(String outTradeNo);

    int updateState(String outTradeNo, String newState, String oldState);

    Page<ProductOrderDO> page(ProductOrderPageRequest pageRequest);

    String placeOrder(PlaceOrderRequest request);

    void changeOrderState(CustomMessage customMessage);
}
