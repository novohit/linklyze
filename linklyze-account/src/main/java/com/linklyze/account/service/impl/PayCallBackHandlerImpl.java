package com.linklyze.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.linklyze.account.config.RabbitMQConfig;
import com.linklyze.account.mapper.ProductOrderMapper;
import com.linklyze.account.model.ProductOrderDO;
import com.linklyze.account.service.strategy.PayCallBackResponse;
import com.linklyze.account.service.strategy.PayCallbackHandler;
import com.linklyze.common.enums.MessageEventType;
import com.linklyze.common.enums.PayStateEnum;
import com.linklyze.common.enums.PayType;
import com.linklyze.common.model.bo.CustomMessage;
import com.linklyze.common.util.JsonUtil;
import com.linklyze.common.util.uuid.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class PayCallBackHandlerImpl implements PayCallbackHandler {

    private final ProductOrderMapper productOrderMapper;

    private final RabbitTemplate rabbitTemplate;

    public PayCallBackHandlerImpl(ProductOrderMapper productOrderMapper, RabbitTemplate rabbitTemplate) {
        this.productOrderMapper = productOrderMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public PayCallBackResponse handle(Map<String, String> paramMap) {
        String outTradeNo = paramMap.get("out_trade_no");
        String totalAmount = paramMap.get("total_amount");
        String subject = paramMap.get("subject");
        String body = paramMap.get("body");
        String accountNo = paramMap.get("account_no");
        CustomMessage message = CustomMessage.builder()
                .accountNo(Long.valueOf(accountNo))
                .content(JsonUtil.obj2Json(paramMap))
                .messageId(IDUtil.fastUUID())
                .eventType(MessageEventType.ORDER_PAY_SUCCESS)
                .build();
        // 异步修改订单状态和下发流量包
        log.info("向MQ发送消息,message:[{}]", message);
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EVENT_EXCHANGE, RabbitMQConfig.ORDER_PAY_SUCCESS_ROUTING_KEY, message);
        log.info(
                "订单支付成功，订单号：{}，支付方式：{}，订单详情：{}，订单金额：{}",
                outTradeNo,
                PayType.ALI_PAY_PC,
                subject,
                totalAmount
        );
        return new PayCallBackResponse("success");
    }
}
