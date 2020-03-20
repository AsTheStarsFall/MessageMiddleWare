package com.tianhy.study.dlx.ttl;

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
 * @Date: 2020/2/1 11:17
 * @Desc:
 */
//@Configuration
public class DLXConfig {

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

    //正常的交换器
    @Bean("oriExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("ORI_EXCHANGE", true, false, new HashMap<>());
    }

    //DLX交换器
    @Bean("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange("DIRECT_EXCHANGE", true, false, new HashMap<>());
    }

    //正常的队列
    @Bean("oriQueue")
    public Queue oriQueue() {
        Map<String, Object> agrs = new HashMap<>();
        //10s后消息变死信
        agrs.put("x-message-ttl", 10000);
        //队列中消息变死信后，转发给死信交换器
        agrs.put("x-dead-letter-exchange", "DIRECT_EXCHANGE");
        //指定路由键，
        agrs.put("x-dead-letter-routing-key", "routingkey");
        return new Queue("QUEUE_ORI", true, false, false, agrs);
    }

    //ori bind
    @Bean
    public Binding oriBinding(@Qualifier("oriQueue") Queue oriQueue,
                              @Qualifier("directExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(oriQueue).to(fanoutExchange);
    }


    //DLX 队列
    @Bean("dlxQueue")
    public Queue dlxQueue() {
        return new Queue("QUEUE_DEAD_LETTER", true, false, false, new HashMap<>());
    }

    //DLX bind
    @Bean
    public Binding dlxBinding(@Qualifier("dlxQueue") Queue dlxQueue,
                              @Qualifier("directExchange") DirectExchange directExchange) {
        //dlx 队列和交换器绑定，指定路由键
        return BindingBuilder.bind(dlxQueue).to(directExchange).with("routingkey");
    }

    @Bean
    public Binding dlxBinding1(@Qualifier("dlxQueue") Queue dlxQueue, @Qualifier("directExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(dlxQueue).to(topicExchange).with("#"); //无条件路由
    }
}
