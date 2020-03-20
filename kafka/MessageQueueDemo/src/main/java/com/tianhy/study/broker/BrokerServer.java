package com.tianhy.study.broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: 提供消息中心服务，向外提供
 * @Author: thy
 * @Date: 2020/1/26 5:15
 */
public class BrokerServer implements Runnable {

    public static int SERVICE_PORT = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //获取输入流/输入流 进行处理
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream out = new PrintStream(socket.getOutputStream())) {
            while (true) {
                String s = in.readLine();
                if (s == null) {
                    continue;
                }

                System.out.println("接收到原始数据：" + s);

                //表示要消费一条消息
                if (s.equals("CONSUME")) {
                    //从消息队列获取消息
                    String consume = Broker.consume();
                    //写入sock
                    out.print(consume);
                    out.flush();
                } else {
                    //否则都是生产的消息
                    Broker.produce(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVICE_PORT);
        while (true) {
            BrokerServer brokerServer = new BrokerServer(serverSocket.accept());
            new Thread(brokerServer).start();
        }
    }
}
