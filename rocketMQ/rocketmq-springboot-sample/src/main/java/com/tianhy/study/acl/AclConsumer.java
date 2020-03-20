package com.tianhy.study.acl;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/7 11:23
 * @Desc:
 */
@Component
@RocketMQMessageListener(topic = "SPRING_BOOT_TOPIC", consumerGroup = "SPRING_BOOT_CONSUMER_GROUP", accessKey = "AK", secretKey = "SK")
public class AclConsumer implements RocketMQListener {
    @Override
    public void onMessage(Object message) {

    }
}
