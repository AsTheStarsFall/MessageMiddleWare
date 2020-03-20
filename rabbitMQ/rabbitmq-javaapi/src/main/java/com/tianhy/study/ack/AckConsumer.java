package com.tianhy.study.ack;

import com.rabbitmq.client.*;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;

/**
 * @Author: thy
 * @Date: 2020/2/4 22:26
 * @Desc: 消费者-确认和拒绝
 */
public class AckConsumer {
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

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message : '" + msg + "'");
                if (msg.contains("拒收")) {
                    // 拒绝消息
                    // requeue：是否重新入队列，true：是；false：直接丢弃，相当于告诉队列可以直接删除掉
                    // TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
                    channel.basicReject(envelope.getDeliveryTag(), false);
                } else if (msg.contains("异常")) {
                    // 批量拒绝
                    // requeue：是否重新入队列
                    // TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
                    channel.basicNack(envelope.getDeliveryTag(), true, false);
                } else {
                    // 手工应答
                    // 如果不应答，队列中的消息会一直存在，重新连接的时候会重复消费
                    channel.basicAck(envelope.getDeliveryTag(), true);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
