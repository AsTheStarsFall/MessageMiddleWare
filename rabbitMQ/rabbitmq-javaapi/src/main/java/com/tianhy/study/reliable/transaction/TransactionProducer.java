package com.tianhy.study.reliable.transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;

/**
 * @Author: thy
 * @Date: 2020/2/4 11:33
 * @Desc: 消息可靠投递机制：事务模式
 */
public class TransactionProducer {
    private static final String EXCHANGE_NAME = "EXCHANGE_DEMO";
    private static final String ROUTING_KEY = "routing_key_demo";
    private static final String QUEUE_NAME = "QUEUE_DEMO";

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

        //todo 发送一条消息
        String msg = "hello rabbitmq msg in transaction mode";

        //enable tx mode 启用事务模式
        try {
            channel.txSelect();
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (IOException e) {
            channel.txRollback();
            System.out.println("消息已回滚");
        }

        channel.close();
        connection.close();


    }
}
