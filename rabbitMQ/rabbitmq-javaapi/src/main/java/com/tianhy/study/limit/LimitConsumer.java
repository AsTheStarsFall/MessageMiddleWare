package com.tianhy.study.limit;

import com.rabbitmq.client.*;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;

/**
 * @Author: thy
 * @Date: 2020/2/5 11:50
 * @Desc: 限流-消费者
 */
public class LimitConsumer {
    private static final String EXCHANGE_NAME = "ACK_EXCHANGE";
    private static final String ROUTING_KEY = "ack_key";
    private static final String QUEUE_NAME = "ACK_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        //创建连接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message : '" + msg + "'");

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //手动ack模式下，Qos数目的消息未被消费的话，不消费新的消息
        channel.basicQos(2);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        channel.close();
        connection.close();
    }
}
