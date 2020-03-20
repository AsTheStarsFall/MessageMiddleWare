package com.tianhy.study;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author: thy
 * @Date: 2020/2/6 13:26
 * @Desc: RocketMQ 消费者
 */
public class RocketmqConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("JAVA_API_CONSUMER_GROUP");
        consumer.setNamesrvAddr("119.23.249.97:9876");

        //指定从哪开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //订阅主题
        consumer.subscribe("JAVA_API_TOPIC", "*");
        //注册消息监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
                System.out.println("Receive new message :" + list.toString());
                //返回成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer started");


    }
}
