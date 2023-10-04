package com.linklyze.order.consumer;

import com.linklyze.order.config.RabbitMQConfig;
import com.linklyze.order.service.ProductOrderService;
import com.linklyze.common.enums.BizCodeEnum;
import com.linklyze.common.exception.BizException;
import com.linklyze.common.model.bo.CustomMessage;
import com.linklyze.order.service.TrafficService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023/10/3
 */
@Component
@Slf4j
public class OrderEventConsumer {

    private final ProductOrderService productOrderService;

    private final TrafficService trafficService;

    public OrderEventConsumer(ProductOrderService productOrderService, TrafficService trafficService) {
        this.productOrderService = productOrderService;
        this.trafficService = trafficService;
    }

    @RabbitListener(queues = {RabbitMQConfig.ORDER_STATE_UPDATE_QUEUE})
    public void handlerOrder(CustomMessage customMessage, Message message, Channel channel) {
        log.info("消费者监听到订单状态更新事件");
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            productOrderService.changeOrderState(customMessage);
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.ACCOUNT_TRAFFIC_UPDATE_QUEUE})
    public void handlerTraffic(CustomMessage customMessage, Message message, Channel channel) {
        log.info("消费者监听到账户流量更新事件");
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            trafficService.changeTraffic(customMessage);
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
    }
}
