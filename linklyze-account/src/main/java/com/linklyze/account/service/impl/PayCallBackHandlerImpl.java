package com.linklyze.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.linklyze.account.mapper.ProductOrderMapper;
import com.linklyze.account.model.ProductOrderDO;
import com.linklyze.account.service.strategy.PayCallBackResponse;
import com.linklyze.account.service.strategy.PayCallbackHandler;
import com.linklyze.common.enums.PayStateEnum;
import com.linklyze.common.enums.PayType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class PayCallBackHandlerImpl implements PayCallbackHandler {

    private final ProductOrderMapper productOrderMapper;

    public PayCallBackHandlerImpl(ProductOrderMapper productOrderMapper) {
        this.productOrderMapper = productOrderMapper;
    }

    @Override
    public PayCallBackResponse handle(Map<String, String> paramMap) {
        String outTradeNo = paramMap.get("out_trade_no");
        String totalAmount = paramMap.get("total_amount");
        String subject = paramMap.get("subject");
        String body = paramMap.get("body");
        int rows = productOrderMapper.update(null, new UpdateWrapper<ProductOrderDO>()
                .lambda()
                .eq(ProductOrderDO::getOutTradeNo, outTradeNo)
                .eq(ProductOrderDO::getState, PayStateEnum.NEW)
                .set(ProductOrderDO::getPayTime, LocalDateTime.now())
                .set(ProductOrderDO::getState, PayStateEnum.PAID));
        if (rows > 0) {
            log.info(
                    "订单支付成功，订单号：{}，支付方式：{}，订单详情：{}，订单金额：{}",
                    outTradeNo,
                    PayType.ALI_PAY_PC,
                    subject,
                    totalAmount
            );
            return new PayCallBackResponse("success");
        } else {
            log.error(
                    "订单更新失败，订单号：{}，支付方式：{}，订单详情：{}，订单金额：{}",
                    outTradeNo,
                    PayType.ALI_PAY_PC,
                    subject,
                    totalAmount
            );
            return new PayCallBackResponse("fail");
        }
    }
}
