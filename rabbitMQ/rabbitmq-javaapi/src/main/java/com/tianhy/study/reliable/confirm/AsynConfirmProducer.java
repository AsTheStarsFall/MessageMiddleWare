package com.tianhy.study.reliable.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @Author: thy
 * @Date: 2020/2/4 11:51
 * @Desc: 消息可靠性投递机制：确认模式-异步确认模式
 */
public class AsynConfirmProducer {

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

        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());
        //设置一个确认监听器
        channel.addConfirmListener(new ConfirmListener() {
            //回传Basic.Ack
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                //如果true，表示已经执行了deliveryTag之前的消息
                if (multiple) {
                    System.out.println("Basic.Ack, +deliveryTag :" + deliveryTag + "multipel :" + multiple);
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    confirmSet.remove(deliveryTag);
                }
            }
            //回传Basic.Nack
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("Basic.Nack, +deliveryTag :" + deliveryTag + "multipel :" + multiple);
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "normal confirm msg";
        //将信道设置为确认模式
        channel.confirmSelect();
        for (int i = 0; i < 10; i++) {
            //要发送消息的序列号
            long nextSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
            //每发送一条消息，将消息的序列号保存
            confirmSet.add(nextSeqNo);
        }

    }
}
