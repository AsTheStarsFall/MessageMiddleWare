package com.tianhy.study.broker;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Description: 消息中心客户端
 * @Author: thy
 * @Date: 2020/1/26 5:30
 */
public class BrokerClient {

    //生产消息
    public static void produce(String msg) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        //写出去
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(msg);
            out.flush();
        }
    }

    //消费消息
    public static String consume() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        //读
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             //向消息中心发送“Consume” 表示消费消息
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            //发送 consume
            out.println("CONSUME");
            out.flush();

            //获取要消费的消息
            String s = in.readLine();

            return s;
        }
    }
}
