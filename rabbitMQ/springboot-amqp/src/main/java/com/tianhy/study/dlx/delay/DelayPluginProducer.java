package com.tianhy.study.dlx.delay;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: thy
 * @Date: 2020/2/1 16:51
 * @Desc:
 */
@ComponentScan(basePackages = "com.tianhy.study.dlx.delay")
public class DelayPluginProducer {

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DelayPluginProducer.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, +10);
        Date delayTime = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "延迟插件测试消息，发送时间：" + sdf.format(now) + ",路由时间：" + sdf.format(delayTime);

        MessageProperties properties = new MessageProperties();
        properties.setHeader("x-delay", delayTime.getTime() - now.getTime());
//        properties.setDelay((int) (delayTime.getTime() - now.getTime()));
        Message message = new Message(msg.getBytes(), properties);

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(5000);
                rabbitTemplate.convertAndSend("sb_DELAY_EXCHANGE", "#", message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.in.read();
    }


}
