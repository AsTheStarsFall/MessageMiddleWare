package com.tianhy.study.sample;

import com.rabbitmq.client.*;
import com.tianhy.study.util.ResourceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 消费者
 * @Author: thy
 * @Date: 2020/1/30 14:40
 */
public class RabbitConsumer {

    private static final String QUEUE_NAME = "TEST_TTL_QUEUE";
    private static final String IP_ADDRESS = "119.23.249.97";
    private static final int PORT = 5672;


    public static void main(String[] args) throws Exception {
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS, PORT)
        };
        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setUsername("admin");
//        connectionFactory.setPassword("123456");
        connectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        //创建连接
//        Connection connection = connectionFactory.newConnection(addresses);
        Connection connection = connectionFactory.newConnection();

        //通道
        Channel channel = connection.createChannel();
        //设置客户端最多接收未被ack的消息的个数
        channel.basicQos(64);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv msg :" + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //显式的ack
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
//        channel.basicCancel("myConsumerTag");
        /**
         * 推模式的消费方式（autoAck=false）
         * 将信道设置为投递模式，直到取消订阅 channel.basicCancel("myConsumerTag") 为止
         * 在投递期间，不断地推送消息给消费者，但消息的个数受到basicQos(64)的限制
         * tag 标签是用来区分多个消费者
         */
        channel.basicConsume(QUEUE_NAME, false, "myConsumerTag", consumer);

        //todo 拉模式的消费方式
        GetResponse response = channel.basicGet(QUEUE_NAME, false);
        channel.basicAck(response.getEnvelope().getDeliveryTag(), false);

        //等待回调函数执行完毕，关闭资源
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        channel.close();
        connection.close();
    }
}
