package com.tianhy.study.ttl;

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


/**
 * @Author: thy
 * @Date: 2020/2/1 1:18
 * @Desc: TTL配置类
 */
//@Configuration
public class TTLConfig {

    //连接工厂
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
//        cachingConnectionFactory.setVirtualHost("/test_ttl");
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

    //设置队列的TTL
    @Bean("ttlQueue")
    public Queue ttlQueue() {
        HashMap<String, Object> args = new HashMap<>();
        //TEST_TTL_QUEUE队列中的消息10s未消费过期
        args.put("x-message-ttl", 10000);
        return new Queue("TEST_TTL_QUEUE", true, false, false, args);
    }

    @Bean("expireQueue")
    public Queue expireQueue() {
        HashMap<String, Object> args = new HashMap<>();
        //TEST_EXPIRE_QUEUE队列中的消息20s未消费被删除
        args.put("x-expire", 20000);
        return new Queue("TEST_EXPIRE_QUEUE", true, false, false, args);
    }

    //创建交换器
    @Bean("ttlExchange")
    public DirectExchange ttlExchange() {
        return new DirectExchange("TEST_TTL_EXCHANGE", true, false, new HashMap<>());
    }

    //绑定
    @Bean
    public Binding queueBindExchangeWithRoutingKey(@Qualifier("ttlQueue") Queue queue, @Qualifier("ttlExchange") DirectExchange exchange) {
        //通过路由键，将队列和交换器绑定
        return BindingBuilder.bind(queue).to(exchange).with("test.ttl");
    }


}

