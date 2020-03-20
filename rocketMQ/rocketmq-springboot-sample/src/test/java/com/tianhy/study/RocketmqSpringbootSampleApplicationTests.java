package com.tianhy.study;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketmqSpringbootSampleApplicationTests {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Test
    public void contextLoads() {
        String msg = "springboot test msg";
        rocketMQTemplate.syncSend("SPRING_BOOT_TOPIC", msg);
    }

}
