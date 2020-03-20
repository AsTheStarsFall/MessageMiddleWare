package com.tianhy.study.trace;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: thy
 * @Date: 2020/2/7 11:22
 * @Desc: 消息追踪-生产者，此功能默认是开启的，且trace-topic 为 RMQ_SYS_TRACE_TOPIC
 */
public class TraceProducer {
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public SendResult send(String msg) {
        SendResult sendResult = rocketMQTemplate.syncSend("SPRING_BOOT_TOPIC", msg);
        return sendResult;
    }
}
