package com.tianhy.study.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tianhy.study.util.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: thy
 * @Date: 2020/2/1 9:52
 * @Desc: 消息消费者，测试DLX
 * 设置消息TTL时间为10s，如果依旧未消费，变消息为死信，发送给死信交换器，然后路由到死信队列
 */
public class DLXConsumer {

    private static final String EXCHANGE_NORMAL = "EXCHANGE_NORMAL";
    private static final String EXCHANGE_DLX = "EXCHANGE_DLX";
    private static final String QUEUE_NORMAL = "QUEUE_NORMAL";
    private static final String QUEUE_DLX = "QUEUE_DLX";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //指定DLX交换器
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-dead-letter-exchange", EXCHANGE_DLX);
        argss.put("x-dead-letter-routing-key", "routingkey");

        //正常的和DLX交换器
        channel.exchangeDeclare(EXCHANGE_NORMAL, "fanout", true);
        channel.exchangeDeclare(EXCHANGE_DLX, "direct", true);

        //正常队列，传入DLX交换器的信息(当队列中的消息死信，传递给DLX交换器)
        channel.queueDeclare(QUEUE_NORMAL, true, false, false, argss);
        //绑定,正常的交换器是 fanout 模式，不需要指定路由键
        channel.queueBind(QUEUE_NORMAL, EXCHANGE_NORMAL, "");

        //DLX队列
        channel.queueDeclare(QUEUE_DLX, true, false, false, null);
        //绑定,direct模式，需要指定路由键
        channel.queueBind(QUEUE_DLX, EXCHANGE_DLX, "routingkey");
        System.out.println("waiting for message...");


//        channel.basicPublish(EXCHANGE_NORMAL, "rk", null, "".getBytes());
    }

}
