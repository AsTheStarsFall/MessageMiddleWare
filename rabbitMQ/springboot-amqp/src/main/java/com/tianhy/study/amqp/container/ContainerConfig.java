package com.tianhy.study.amqp.container;

import com.tianhy.study.util.ResourceUtil;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @Author: thy
 * @Date: 2020/2/2 20:59
 * @Desc:
 */
@Configuration
public class ContainerConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        return factory;
    }


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        return admin;
    }

    @Bean("secondQueue")
    public Queue getSecondQueue() {
        return new Queue("BASIC_SECOND_QUEUE");
    }

    @Bean("thirdQueue")
    public Queue getThirdQueue() {
        return new Queue("BASIC_THIRD_QUEUE");
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        //设置要监听的队列
        container.setQueues(getSecondQueue(), getThirdQueue());
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);//签收模式
        //消费者的标签策略
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                System.out.println("设置标签名称");
                return queue + "_" + UUID.randomUUID().toString();
            }
        });
        return container;
    }


    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());//消息转换器
        /**
         * NONE：自动应答
         * AUTO：如果方法未抛出异常，则ack
         * MANUAL：手动应答
         */
        factory.setAcknowledgeMode(AcknowledgeMode.NONE); //应答模式
        factory.setAutoStartup(true);

        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);

        factory.setTransactionManager(new RabbitTransactionManager(connectionFactory));

        return factory;

    }

}
