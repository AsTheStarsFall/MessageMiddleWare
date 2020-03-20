package com.tianhy.study.trace;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/7 11:12
 * @Desc: 消息轨迹-消费者
 */
@Component
// customizedTraceTopic属性可以被配置rocketmq.consumer.customized-trace-topic替换
@RocketMQMessageListener(topic = "", consumerGroup = "", enableMsgTrace = true, customizedTraceTopic = "")
public class TraceConsumer implements RocketMQListener {
    @Override
    public void onMessage(Object message) {

    }
}
