package com.tianhy.study.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: thy
 * @Date: 2020/2/3 16:42
 * @Desc:
 */
@Component
@RabbitListener(queues = "SPRING_BOOT_QUEUE")
public class RabbitConsumer {

    @RabbitHandler
    public void process(Message message) {
        System.out.println("Revc msg :" + message);
    }

    //获取channel参数,引入rabbitmq.client
    @RabbitHandler
    public void process1(Message message, Channel channel) throws IOException {
        System.out.println("Revc msg :" + message);
        //调用ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
