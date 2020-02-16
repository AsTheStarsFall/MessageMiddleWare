package com.tianhy.study.returnlistener;

import com.rabbitmq.client.*;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;

/**
 * @Author: thy
 * @Date: 2020/2/4 21:58
 * @Desc: 消息回发
 */
public class ReturnListenerProducer {
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

        //消息回调监听
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("=========监听器收到了无法路由，被返回的消息============");
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
                System.out.println("message:" + new String(body));
            }
        });

        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
        channel.close();
        connection.close();


    }

}
