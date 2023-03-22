package com.wyu.plato.link.consumer;

import com.rabbitmq.client.Channel;
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

    @RabbitListener(queues = RabbitMQConfig.CREATE_LINK_QUEUE)
    public void addLinkHandler(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("创建link");
    }

    @RabbitListener(queues = RabbitMQConfig.CREATE_LINK_MAPPING_QUEUE)
    public void addLinkMappingHandler(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("创建link_mapping");
    }
}
