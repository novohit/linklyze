package com.linklyze.order.service.impl;

import com.linklyze.order.config.RabbitMQConfig;
import com.linklyze.order.mapper.ProductOrderMapper;
import com.linklyze.order.service.strategy.PayCallBackResponse;
import com.linklyze.order.service.strategy.PayCallbackHandler;
import com.linklyze.common.enums.MessageEventType;
import com.linklyze.common.enums.PayType;
import com.linklyze.common.model.bo.CustomMessage;
import com.linklyze.common.util.JsonUtil;
import com.linklyze.common.util.uuid.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PayCallBackHandlerImpl implements PayCallbackHandler {

    private final ProductOrderMapper productOrderMapper;

    private final RabbitTemplate rabbitTemplate;

    private final StringRedisTemplate redisTemplate;

    public PayCallBackHandlerImpl(ProductOrderMapper productOrderMapper, RabbitTemplate rabbitTemplate, StringRedisTemplate redisTemplate) {
        this.productOrderMapper = productOrderMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public PayCallBackResponse handle(Map<String, String> paramMap) {
        String outTradeNo = paramMap.get("out_trade_no");
        String totalAmount = paramMap.get("total_amount");
        String subject = paramMap.get("subject");
        String body = paramMap.get("body");
        Long accountNo = Long.valueOf(paramMap.get("passback_params"));
        CustomMessage message = CustomMessage.builder()
                .accountNo(accountNo)
                .bizId(outTradeNo)
                .content(JsonUtil.obj2Json(paramMap))
                .messageId(IDUtil.fastUUID())
                .eventType(MessageEventType.ORDER_PAID)
                .build();
        // 上游第三方支付回调消息不是幂等的，有可能发送多次消息，需要下游在业务或数据库上保证幂等性
        Boolean success = redisTemplate.opsForValue().setIfAbsent(outTradeNo, "SUCCESS", 12, TimeUnit.HOURS);
        if (Boolean.TRUE.equals(success)) {
            // 异步修改订单状态和下发流量包
            log.info(
                    "订单支付成功并发送异步消息，订单号：{}，支付方式：{}，订单详情：{}，订单金额：{}，\n message：{}",
                    outTradeNo,
                    PayType.ALI_PAY_PC,
                    subject,
                    totalAmount,
                    message
            );
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EVENT_EXCHANGE, RabbitMQConfig.ORDER_PAY_SUCCESS_ROUTING_KEY, message);
            return new PayCallBackResponse("success");
        } else {
            log.error(
                    "订单已处理，订单号：{}，支付方式：{}，订单详情：{}，订单金额：{}",
                    outTradeNo,
                    PayType.ALI_PAY_PC,
                    subject,
                    totalAmount
            );
            return new PayCallBackResponse("fail");
        }
    }
}
