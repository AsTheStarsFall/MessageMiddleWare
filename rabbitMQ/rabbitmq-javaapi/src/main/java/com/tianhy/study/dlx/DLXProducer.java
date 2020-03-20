package com.tianhy.study.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tianhy.study.util.ResourceUtil;


/**
 * @Author: thy
 * @Date: 2020/2/1 9:27
 * @Desc: 消息生产者，通过设置TTL测试DLX
 */
public class DLXProducer {

    public static void main(String[] args) throws Exception {

        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        //建立连接
        Connection connection = connectionFactory.newConnection();
        //信道
        Channel channel = connection.createChannel();

        String msg = "This is a DLX message";

        //设置属性
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000") // 消息本身TTL 10s
                .build();
        //发送一条路由键为 rk 的消息到正常的队列
        channel.basicPublish("EXCHANGE_NORMAL", "rk", properties, msg.getBytes());

        channel.close();
        connection.close();
    }


}
