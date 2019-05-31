package com.d2c.store.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cai
 */
@Configuration
public class RabbitmqConfig {

    public final static String EXCHANGE_NAME = "delayed.exchange";
    //
    public final static String ACCOUNT_QUEUE_NAME = "delayed.account.expired";

    @Bean
    public CustomExchange exchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //参数二为类型：必须是x-delayed-message
        return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean(name = "accountQueue")
    public Queue accountQueue() {
        return new Queue(ACCOUNT_QUEUE_NAME);
    }

    @Bean
    public Binding bindingOrder(@Qualifier("accountQueue") Queue queue, CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ACCOUNT_QUEUE_NAME).noargs();
    }

}
