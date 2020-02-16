package com.tianhy.study.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tianhy.study.util.ResourceUtil;

/**
 * @Author: thy
 * @Date: 2020/2/5 9:13
 * @Desc: 生产者-测试消费者应答
 */
public class AckProducer {
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
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String msg = "hello rabbitmq msg";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, (msg + "-" + i).getBytes());
        }

        channel.close();
        connection.close();
    }
}
