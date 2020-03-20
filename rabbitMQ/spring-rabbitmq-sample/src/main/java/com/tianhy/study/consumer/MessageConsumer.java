package com.tianhy.study.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Author: thy
 * @Date: 2020/2/3 11:32
 * @Desc:
 */
public class MessageConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("MessageConsumer recv message :" + message);

    }
}
