package com.tianhy.study.dlx.delay;

import com.tianhy.study.util.ResourceUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: thy
 * @Date: 2020/2/1 16:44
 * @Desc: 延迟插件-配置类
 */
@Configuration
public class DelayPluginConfig {
    //连接工厂
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    //RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean("delayExchange")
    public CustomExchange exchange() {
        //声明x-delayed-message类型的 交换器
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-delayed-type", "direct");
        return new CustomExchange("sb_DELAY_EXCHANGE", "x-delayed-message", true, false, argss);
    }

    @Bean("delayQueue")
    public Queue queue() {
        return new Queue("sb_DELAY_QUEUE", true, false, false, new HashMap<>());
    }

    @Bean
    public Binding binding(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#").noargs();
    }


}
