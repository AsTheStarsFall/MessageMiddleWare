package com.tianhy.study.reliable.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tianhy.study.util.ResourceUtil;

/**
 * @Author: thy
 * @Date: 2020/2/4 11:42
 * @Desc: 消息可靠性投递机制：确认模式-普通确认模式
 */
public class NormalConfirmProducer {
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

        //将信道设置为确认模式
        channel.confirmSelect();
        String msg = "normal confirm msg";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());

        //逐条确认
        if (channel.waitForConfirms()) {
            System.out.println("消息发送成功");
        }
        channel.close();
        connection.close();
    }
}
