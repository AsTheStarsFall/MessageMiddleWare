package com.tianhy.study.example;

import com.tianhy.study.domain.OrderPaidEvent;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/6 15:22
 * @Desc: 实体消息
 */
@Component
@RocketMQMessageListener(topic = "SPRING_BOOT_TOPIC", consumerGroup = "SPRING_BOOT_CONSUMER_GROUP")
public class RocketmqConsumer1 implements RocketMQListener<OrderPaidEvent> {
    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        System.out.println("Consumer receive msg : " + orderPaidEvent.toString());
    }
}
