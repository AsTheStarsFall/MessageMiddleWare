package com.tianhy.study.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: thy
 * @Date: 2020/2/3 11:29
 * @Desc:
 */
@Service
public class MessageProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(Object msg) {

        rabbitTemplate.convertAndSend("SPRING_DIRECT_EXCHANGE", "SPRING_KEY", msg);
    }
}
