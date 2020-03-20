package com.tianhy.study.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/3 16:42
 * @Desc:
 */
@Component
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(){
        String msg ="nice springboot rabbitmq";
        rabbitTemplate.convertAndSend("SPRING_BOOT_EXCHANGE","sb_key",msg);

    }
}
