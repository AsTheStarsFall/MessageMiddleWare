package com.tianhy.study.transaction;

import com.tianhy.study.domain.OrderPaidEvent;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;

/**
 * @Author: thy
 * @Date: 2020/2/7 10:37
 * @Desc: 事务消息-生产者
 */
public class TransactionProducer {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public void send() {
        OrderPaidEvent orderPaidEvent = new OrderPaidEvent("NO_0001", new BigDecimal("93.26"));

        //构建spring message
        Message message = MessageBuilder.withPayload(orderPaidEvent).build();
        rocketMQTemplate.sendMessageInTransaction("TRANS_PRODUCER_GROUP", "", message, null);

    }
}
