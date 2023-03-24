package com.wyu.plato.link.consumer;

import com.rabbitmq.client.Channel;
import com.wyu.plato.common.enums.MessageEventType;
import com.wyu.plato.common.model.CustomMessage;
import com.wyu.plato.link.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-03-21
 */

@Component
@Slf4j
public class LinkAddMQConsumer {

    /**
     * C端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.CREATE_LINK_QUEUE)
    public void addLinkHandler(CustomMessage customMessage, Message message, Channel channel) {
        customMessage.setEventType(MessageEventType.LINK_CREATE);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("创建link");
    }

    /**
     * B端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.CREATE_LINK_MAPPING_QUEUE)
    public void addLinkMappingHandler(CustomMessage customMessage, Message message, Channel channel) {
        customMessage.setEventType(MessageEventType.LINK_MAPPING_CREATE);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("创建link_mapping");
    }
}
