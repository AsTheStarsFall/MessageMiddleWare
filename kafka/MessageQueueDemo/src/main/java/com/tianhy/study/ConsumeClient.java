package com.tianhy.study;

import com.tianhy.study.broker.BrokerClient;

import java.io.IOException;

/**
 * @Description: 消费客户端
 * @Author: thy
 * @Date: 2020/1/26 5:44
 */
public class ConsumeClient {
    public static void main(String[] args) throws IOException {
        String consume = BrokerClient.consume();
        System.out.println("获取消息：" + consume);
    }
}
