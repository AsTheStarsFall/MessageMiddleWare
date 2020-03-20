package com.tianhy.study.dlx.delay;

import com.rabbitmq.client.*;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: thy
 * @Date: 2020/2/1 17:03
 * @Desc:
 */
public class DelayPluginConsumer {

    public static void main(String[] args) throws Exception {
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        //建立连接
        Connection connection = connectionFactory.newConnection();
        //信道
        Channel channel = connection.createChannel();

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println("接收时间：" + sdf.format(new Date()) + "\n 收到消息[" + msg + "]");
            }
        };
        channel.basicConsume("sb_DELAY_QUEUE", true, consumer);
        System.out.println("waiting for msg...");


    }

}
