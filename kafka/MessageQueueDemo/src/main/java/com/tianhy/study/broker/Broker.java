package com.tianhy.study.broker;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Description: 消息处理中心
 * @Author: thy
 * @Date: 2020/1/26 5:04
 */
public class Broker {

    //队列存储消息的最大数量
    private final static int MAX_SIZE = 3;

    //存储消息的容器
    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(MAX_SIZE);


    //生产消息
    public static void produce(String msg) {
        if (messageQueue.offer(msg)) {
            System.out.println("成功向消息中心发送消息：" + msg + ",当前消息中心消息数量是：" + messageQueue.size());
        } else {
            System.out.println("消息中心已满");
        }
    }

    //消费消息
    public static String consume() {
        String msg = messageQueue.poll();
        if (msg != null) {
            System.out.println("消费消息：" + msg + "，当前消息中心数量是：" + messageQueue.size());
        } else {
            System.out.println("当前消息中心没有消息可消费");
        }
        return msg;
    }
}
