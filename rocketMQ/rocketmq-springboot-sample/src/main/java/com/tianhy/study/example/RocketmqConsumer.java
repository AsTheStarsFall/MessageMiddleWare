package com.tianhy.study.example;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/6 15:22
 * @Desc: 字符串消息
 */
@Component
@RocketMQMessageListener(topic = "SPRING_BOOT_TOPIC", consumerGroup = "SPRING_BOOT_CONSUMER_GROUP")
public class RocketmqConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        System.out.println("Consumer receive msg : " + msg);
    }
}
