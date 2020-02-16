package com.tianhy.study;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @Author: thy
 * @Date: 2020/2/6 13:17
 * @Desc: RocketMq 生产者
 */
public class RocketmqProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("JAVA_API_PRODUCER_GROUP");
        producer.setNamesrvAddr("119.23.249.97:9876");

        producer.start();

        String msg = "Nice RocketMQ msg";
        for (int i = 0; i < 5; i++) {

            //封装消息 message
            Message message = new Message("JAVA_API_TOPIC", "java_api_tag", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息
            SendResult sendResult = producer.send(message);
/*            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {

                }

                @Override
                public void onException(Throwable e) {

                }
            });*/
//            System.out.println(sendResult);
            producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    return null;
                }
            }, "");


            System.out.printf("%s%n", sendResult);
        }


    }
}
