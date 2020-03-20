package com.tianhy.study;

import com.tianhy.study.producer.MessageProducer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.retry.backoff.Sleeper;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: thy
 * @Date: 2020/2/3 12:02
 * @Desc:
 */
public class RabbitTest {

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ApplicationContext context;

    @Test
    public void messageTest() throws InterruptedException {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");

        MessageProducer producer = context.getBean(MessageProducer.class);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            producer.sendMessage("Nice Spring - " + i);
        }

    }
}
