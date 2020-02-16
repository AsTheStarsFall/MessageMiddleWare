package com.tianhy.study.dlx;

import com.rabbitmq.client.*;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: thy
 * @Date: 2020/2/1 14:23
 * @Desc: 延迟队列-消费者
 * 前提是Linux服务器安装rabbitmq_delayed_message_exchange插件
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

        //声明x-delayed-message类型的 交换器
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-delayed-type", "direct");
        channel.exchangeDeclare("DELAY_EXCHANGE", "x-delayed-message", false, false, argss);

        channel.queueDeclare("DELAY_QUEUE", false, false, false, null);

        channel.queueBind("DELAY_QUEUE", "DELAY_EXCHANGE", "DELAY_KEY");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println("接收时间：" + sdf.format(new Date()) + "\n 收到消息[" + msg + "]");
            }
        };
        channel.basicConsume("DELAY_QUEUE", true, consumer);
        System.out.println("waiting for msg...");
    }

}
