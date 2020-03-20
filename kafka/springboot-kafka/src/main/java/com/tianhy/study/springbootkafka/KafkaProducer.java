package com.tianhy.study.springbootkafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: thy
 * @Date: 2020/1/28 10:51
 */
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send() {
        String msg = "sb-data";
        kafkaTemplate.send("test-sb-topic", "sb-key", msg);
    }
}
