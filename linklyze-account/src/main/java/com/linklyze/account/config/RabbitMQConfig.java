package com.linklyze.account.config;

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

    // 订单事件交换机
    public static final String ORDER_EVENT_EXCHANGE = "order.event.exchange";

    @Bean
    public TopicExchange orderEventExchange() {
        return new TopicExchange(ORDER_EVENT_EXCHANGE, true, false);
    }


    // 订单支付成功相关队列

    public static final String ORDER_STATE_UPDATE_QUEUE = "order.state.update.queue";

    public static final String ACCOUNT_TRAFFIC_UPDATE_QUEUE = "account.traffic.update.queue";

    public static final String ORDER_PAY_SUCCESS_ROUTING_KEY = "order_state.account_traffic.update.routing.key";

    public static final String ORDER_STATE_UPDATE_BINDING_KEY = "order_state.*.update.routing.key";

    public static final String ACCOUNT_TRAFFIC_UPDATE_BINDING_KEY = "*.account_traffic.update.routing.key";

    /**
     * 订单状态更新队列
     *
     * @return
     */
    @Bean
    public Queue orderStateUpdateQueue() {
        return new Queue(ORDER_STATE_UPDATE_QUEUE, true, false, false);
    }

    /**
     * 账户流量更新队列
     *
     * @return
     */
    @Bean
    public Queue accountTrafficUpdateQueue() {
        return new Queue(ACCOUNT_TRAFFIC_UPDATE_QUEUE, true, false, false);
    }


    @Bean
    public Binding accountTrafficUpdateBinding() {
        return BindingBuilder.bind(accountTrafficUpdateQueue())
                .to(orderEventExchange())
                .with(ACCOUNT_TRAFFIC_UPDATE_BINDING_KEY);
    }

    @Bean
    public Binding orderStateUpdateBinding() {
        return BindingBuilder.bind(orderStateUpdateQueue())
                .to(orderEventExchange())
                .with(ORDER_STATE_UPDATE_BINDING_KEY);
    }
}
