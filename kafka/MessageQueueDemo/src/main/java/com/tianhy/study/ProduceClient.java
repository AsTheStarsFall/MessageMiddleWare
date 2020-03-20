package com.tianhy.study;

import com.tianhy.study.broker.BrokerClient;

import java.io.IOException;

/**
 * @Description: 生产客户端
 * @Author: thy
 * @Date: 2020/1/26 5:42
 */
public class ProduceClient {

    public static void main(String[] args) throws IOException {
        BrokerClient.produce("Hello World");
    }
}
