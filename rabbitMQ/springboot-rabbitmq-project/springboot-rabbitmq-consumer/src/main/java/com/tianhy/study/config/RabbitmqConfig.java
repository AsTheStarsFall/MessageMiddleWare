package com.tianhy.study.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: thy
 * @Date: 2020/2/2 21:58
 * @Desc:
 */

@Configuration
@ConfigurationProperties("rabbitmq-config.properties")
public class RabbitmqConfig {


    @Value("${com.tianhy.firstqueue}")
    private String firstQueue;

    @Value("${com.tianhy.secondqueue}")
    private String secondQueue;

    @Value("${com.tianhy.thirdqueue}")
    private String thirdQueue;

    @Value("${com.tianhy.fourthqueue}")
    private String fourthQueue;

    @Value("${com.tianhy.directexchange}")
    private String directExchange;

    @Value("${com.tianhy.topicexchange}")
    private String topicExchange;

    @Value("${com.tianhy.fanoutexchange}")
    private String fanoutExchange;

    // 创建四个队列
    @Bean("vipFirstQueue")
    public Queue getFirstQueue() {
        return new Queue(firstQueue, true, false, false, new LinkedHashMap<>());
    }

    @Bean("vipSecondQueue")
    public Queue getSecondQueue() {
        return new Queue(secondQueue);
    }

    @Bean("vipThirdQueue")
    public Queue getThirdQueue() {
        return new Queue(thirdQueue);
    }

    @Bean("vipFourthQueue")
    public Queue getFourthQueue() {
        return new Queue(fourthQueue);
    }

    // 创建三个交换机
    @Bean("vipDirectExchange")
    public CustomExchange getDirectExchange() {
        //声明x-delayed-message类型的 交换器
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-delayed-type", "direct");
        return new CustomExchange(directExchange, "x-delayed-message", true, false, argss);
//        return new DirectExchange(directExchange);
    }

    @Bean("vipTopicExchange")
    public TopicExchange getTopicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean("vipFanoutExchange")
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    // 定义四个绑定关系
    @Bean
    public Binding bindFirst(@Qualifier("vipFirstQueue") Queue queue, @Qualifier("vipDirectExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("nice.key").noargs();
    }


    @Bean
    public Binding bindSecond(@Qualifier("vipSecondQueue") Queue queue, @Qualifier("vipTopicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.good.*");
    }

    @Bean
    public Binding bindThird(@Qualifier("vipThirdQueue") Queue queue, @Qualifier("vipFanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFourth(@Qualifier("vipFourthQueue") Queue queue, @Qualifier("vipFanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        final CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://admin:123456@119.23.249.97:5672");
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());//消息转换器
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动确认
        factory.setAutoStartup(true);

        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);

        factory.setTransactionManager(new RabbitTransactionManager(connectionFactory));
        return factory;

    }

}
