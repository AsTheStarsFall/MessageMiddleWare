package com.tianhy.study.ttl;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tianhy.study.util.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 消息生产者
 * 设置消息、队列的TTL
 * @Author: thy
 * @Date: 2020/1/31 7:55
 */
public class TTLProducer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();

        String msg = "Dead message";

        //todo 通过队列属性设置消息的TTL
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-message-ttl", 6000);

        //非持久化、非排他、非自动删除的队列
        channel.queueDeclare("TEST_TTL_QUEUE", false, false, false, argss);

        //todo 通过对消息本身设置TTL
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) //持久化消息
                .expiration("10000") //6000ms
                .contentType("UTF-8")
                .build();

        //同时以两种方式设置了TTL，值小的生效

        //send
        channel.basicPublish("", "TEST_DLX_QUEUE", basicProperties, msg.getBytes());

        channel.close();
        connection.close();

    }

}
