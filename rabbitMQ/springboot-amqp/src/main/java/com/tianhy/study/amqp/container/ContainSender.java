package com.tianhy.study.amqp.container;

import com.tianhy.study.util.ResourceUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author: thy
 * @Date: 2020/2/2 20:59
 * @Desc:
 */
public class ContainSender {

    public static void main(String[] args) throws URISyntaxException {
        ConnectionFactory connectionFactory = new CachingConnectionFactory(new URI(ResourceUtil.getKey("rabbitmq.uri")));
        //RabbitListenerContainerFactory
        //监听容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //创建一个监听容器
        SimpleMessageListenerContainer container = factory.createListenerContainer();
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1); //最小消费者数
//        container.setMaxConcurrentConsumers();
        container.setQueueNames("BASIC_QUEUE");
        //设置消息监听
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("收到消息");
            }
        });

        container.start();
        AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        amqpTemplate.convertAndSend("BASIC_SECOND_QUEUE", "nice msg");


    }
}
