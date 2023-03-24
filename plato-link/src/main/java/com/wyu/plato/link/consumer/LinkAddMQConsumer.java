package com.wyu.plato.link.consumer;

import com.rabbitmq.client.Channel;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.enums.MessageEventType;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.model.CustomMessage;
import com.wyu.plato.link.config.RabbitMQConfig;
import com.wyu.plato.link.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-03-21
 */

@Component
@Slf4j
public class LinkAddMQConsumer {

    @Autowired
    private LinkService linkService;

    /**
     * C端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.CREATE_LINK_QUEUE)
    public void addLinkHandler(CustomMessage customMessage, Message message, Channel channel) {
        log.info("C端消费者监听 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_CREATE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            this.linkService.handleCreate(customMessage);
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("C端消费成功");
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
        log.info("B端消费者监听 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_MAPPING_CREATE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            this.linkService.handleCreate(customMessage);
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("B端消费成功");
    }
}
