package com.wyu.plato.link.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author novo
 * @since 2023-03-21
 */
@Configuration
public class RabbitMQConfig {

    public static final String LINK_EVENT_EXCHANGE = "short_link.event.exchange";

    // 创建配置 ================================================================================

    public static final String CREATE_LINK_QUEUE = "short_link.create.link.queue";

    public static final String CREATE_LINK_MAPPING_QUEUE = "short_link.create.link_mapping.queue";

    public static final String CREATE_LINK_ROUTING_KEY = "short_link.create.link.routing.key";

    //public static final String CREATE_LINK_MAPPING_ROUTING_KEY = "short_link.create.link_mapping.routing.key";

    public static final String CREATE_LINK_BINDING_KEY = "short_link.create.*.routing.key";

    /**
     * 短链创建队列
     *
     * @return
     */
    @Bean
    public Queue addLinkQueue() {
        return new Queue(CREATE_LINK_QUEUE, true, false, false);
    }

    /**
     * 短链映射创建队列
     *
     * @return
     */
    @Bean
    public Queue addLinkMappingQueue() {
        return new Queue(CREATE_LINK_MAPPING_QUEUE, true, false, false);
    }

    @Bean
    public TopicExchange linkEventExchange() {
        return new TopicExchange(LINK_EVENT_EXCHANGE, true, false);
    }

    @Bean
    public Binding addLinkBinding() {
        return BindingBuilder.bind(addLinkQueue())
                .to(linkEventExchange())
                .with(CREATE_LINK_BINDING_KEY);
    }

    @Bean
    public Binding addLinkMappingBinding() {
        return BindingBuilder.bind(addLinkMappingQueue())
                .to(linkEventExchange())
                .with(CREATE_LINK_BINDING_KEY);
    }


    // 更新配置 ================================================================================

    public static final String UPDATE_LINK_QUEUE = "short_link.update.link.queue";

    public static final String UPDATE_LINK_MAPPING_QUEUE = "short_link.update.link_mapping.queue";

    public static final String UPDATE_LINK_ROUTING_KEY = "short_link.update.link.routing.key";

    public static final String UPDATE_LINK_BINDING_KEY = "short_link.update.*.routing.key";

    /**
     * 短链更新队列
     *
     * @return
     */
    @Bean
    public Queue updateLinkQueue() {
        return new Queue(UPDATE_LINK_QUEUE, true, false, false);
    }

    /**
     * 短链映射更新队列
     *
     * @return
     */
    @Bean
    public Queue updateLinkMappingQueue() {
        return new Queue(UPDATE_LINK_MAPPING_QUEUE, true, false, false);
    }


    @Bean
    public Binding updateLinkBinding() {
        return BindingBuilder.bind(addLinkQueue())
                .to(linkEventExchange())
                .with(UPDATE_LINK_BINDING_KEY);
    }

    @Bean
    public Binding updateLinkMappingBinding() {
        return BindingBuilder.bind(addLinkMappingQueue())
                .to(linkEventExchange())
                .with(UPDATE_LINK_BINDING_KEY);
    }

}
