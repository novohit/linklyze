package com.wyu.plato.link.consumer;

import com.rabbitmq.client.Channel;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.enums.MessageEventType;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.model.bo.CustomMessage;
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
public class LinkEventMQConsumer {

    @Autowired
    private LinkService linkService;

    // 创建事件监听 ================================================================================

    /**
     * C端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.CREATE_LINK_QUEUE)
    public void addLinkHandler(CustomMessage customMessage, Message message, Channel channel) {
        log.info("C端消费者监听到创建事件 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_CREATE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            this.linkService.handleCreate(customMessage);
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("C端消费成功 创建事件");
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
        log.info("B端消费者监听到创建事件 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_MAPPING_CREATE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            this.linkService.handleCreate(customMessage);
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("B端消费成功 创建事件");
    }


    // 更新事件监听 ================================================================================

    /**
     * C端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.UPDATE_LINK_QUEUE)
    public void updateLinkHandler(CustomMessage customMessage, Message message, Channel channel) {
        log.info("C端消费者监听到更新事件 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_UPDATE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("C端消费成功 更新事件");
    }

    /**
     * B端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.UPDATE_LINK_MAPPING_QUEUE)
    public void updateLinkMappingHandler(CustomMessage customMessage, Message message, Channel channel) {
        log.info("B端消费者监听到更新事件 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_MAPPING_UPDATE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();

        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("B端消费成功 更新事件");
    }


    // 删除事件监听 ================================================================================

    /**
     * C端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.DELETE_LINK_QUEUE)
    public void deleteLinkHandler(CustomMessage customMessage, Message message, Channel channel) {
        log.info("C端消费者监听到删除事件 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_DELETE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("C端消费成功 删除事件");
    }

    /**
     * B端消费者
     *
     * @param customMessage
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConfig.DELETE_LINK_MAPPING_QUEUE)
    public void deleteLinkMappingHandler(CustomMessage customMessage, Message message, Channel channel) {
        log.info("B端消费者监听到删除事件 message:[{}]", message);
        try {
            customMessage.setEventType(MessageEventType.LINK_MAPPING_DELETE);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();

        } catch (Exception e) {
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("B端消费成功 删除事件");
    }
}
