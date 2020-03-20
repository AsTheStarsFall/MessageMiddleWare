package com.tianhy.study.ttl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: thy
 * @Date: 2020/2/1 1:51
 * @Desc:
 */
//@ComponentScan(basePackages = "com.tianhy.study.ttl")
public class TTLSender {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TTLSender.class);

        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        //设置消息属性
        MessageProperties messageProperties = new MessageProperties();
        //消息过期时间 TTL=4s
        messageProperties.setExpiration("4000");
        //持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

        //消息内容
        String ttlMsg = "This message will be expired after 4 second";
        //封装消息
        Message message = new Message(ttlMsg.getBytes(), messageProperties);
        //消息自身4s后过期
        rabbitTemplate.convertAndSend("TEST_TTL_EXCHANGE", "test.ttl", message);
        //随着队列过期而过期
        rabbitTemplate.convertAndSend("TEST_TTL_EXCHANGE", "test.ttl", "This message");


    }
}
