package com.tianhy.study.sample;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/1 17:50
 * @Desc:
 */
//@Component
//@RabbitListener(queues = "sb_DELAY_QUEUE")
public class TestConsumer {

    @RabbitHandler
    public void process(String msg, Channel channel) {
        System.out.println();
    }
}
