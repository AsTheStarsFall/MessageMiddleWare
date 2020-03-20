package com.tianhy.study.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: thy
 * @Date: 2020/2/3 16:42
 * @Desc: rabbitmq 配置类
 */
@Configuration
public class RabbitConfig {
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://admin:123456@119.23.249.97:5672");
        return factory;
    }

    @Bean("firstQueue")
    public Queue queue() {
        return new Queue("SPRING_BOOT_QUEUE");
    }

    @Bean("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange("SPRING_BOOT_EXCHANGE", true, false);
    }

    @Bean
    public Binding binding(@Qualifier("firstQueue") Queue queue, @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("sb_key");

    }
}
