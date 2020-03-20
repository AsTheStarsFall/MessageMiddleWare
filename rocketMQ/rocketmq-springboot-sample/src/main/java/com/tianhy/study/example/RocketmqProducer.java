package com.tianhy.study.example;

import com.tianhy.study.domain.OrderPaidEvent;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: thy
 * @Date: 2020/2/6 15:18
 * @Desc: 消息生产者
 */
@Component
public class RocketmqProducer {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public SendResult send(String msg) {
        //发送一个字符串
        SendResult sendResult = rocketMQTemplate.syncSend("SPRING_BOOT_TOPIC", msg);
        return sendResult;
    }

    public void send1() {

        OrderPaidEvent payLoad = new OrderPaidEvent("NO_0001", new BigDecimal("93.26"));
        Message<OrderPaidEvent> message = MessageBuilder.withPayload(payLoad).build();

        //发送字符串
        rocketMQTemplate.convertAndSend("SPRING_BOOT_TOPIC", "Hello RocketMQ");
        //发送对象
        rocketMQTemplate.send("SPRING_BOOT_TOPIC", message);
        rocketMQTemplate.convertAndSend("SPRING_BOOT_TOPIC", payLoad);

        //同步发送
        rocketMQTemplate.syncSend("SPRING_BOOT_TOPIC", "syn_message");
        //异步发送
        rocketMQTemplate.asyncSend("SPRING_BOOT_TOPIC", payLoad, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                //成功的回调
            }

            @Override
            public void onException(Throwable e) {
                //异常的回调
            }
        });

        //只负责发送消息，不等待应答且没有回调函数触发，效率最高
        rocketMQTemplate.sendOneWay("SPRING_BOOT_TOPIC", payLoad);

        //同步顺序发送
        rocketMQTemplate.syncSendOrderly("SPRING_BOOT_TOPIC", payLoad, "hashkey");

        //销毁之后不能发送任何消息
        rocketMQTemplate.destroy();
    }
}
