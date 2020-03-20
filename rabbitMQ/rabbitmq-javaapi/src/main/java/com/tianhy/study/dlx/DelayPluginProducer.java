package com.tianhy.study.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tianhy.study.util.ResourceUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: thy
 * @Date: 2020/2/1 14:22
 * @Desc: 延迟队列-生产者
 */
public class DelayPluginProducer {

    public static void main(String[] args) throws Exception {
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        //建立连接
        Connection connection = connectionFactory.newConnection();
        //信道
        Channel channel = connection.createChannel();

        //延迟投递
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        //延迟1分钟
        calendar.add(Calendar.MINUTE, +1);
        Date delayTime = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "发送时间：" + sdf.format(now) + ", 投递时间：" + sdf.format(calendar.getTime());

        //设置延迟时间
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-delay", delayTime.getTime() - now.getTime());

        AMQP.BasicProperties.Builder pros = new AMQP.BasicProperties.Builder()
                .headers(argss);

        channel.basicPublish("DELAY_EXCHANGE", "DELAY_KEY", pros.build(), msg.getBytes());
        channel.close();
        connection.close();

    }
}
