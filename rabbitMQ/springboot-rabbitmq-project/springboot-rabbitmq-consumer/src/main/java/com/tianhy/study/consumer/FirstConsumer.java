package com.tianhy.study.consumer;

import com.tianhy.study.entity.Merchant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/2 22:58
 * @Desc:
 */
@Component
@PropertySource("rabbitmq-config.properties")
@RabbitListener(queues = "${com.tianhy.firstqueue}", containerFactory = "simpleRabbitListenerContainerFactory")
public class FirstConsumer {

    @RabbitHandler
    public void process(@Payload Merchant merchant) {
        System.out.println("First queue recv msg:" + merchant.toString());
    }
}
