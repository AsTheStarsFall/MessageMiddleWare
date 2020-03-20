package com.tianhy.study.springbootkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Description:
 * @Author: thy
 * @Date: 2020/1/28 10:55
 */
@Component
public class KafkaConsumer {

    //订阅主题
    @KafkaListener(topics = {"test-sb-topic"})
    public void listener(ConsumerRecord record) {

        Optional<Object> value = Optional.ofNullable(record.value());
        if (value.isPresent()) {
            System.out.println(value.get());
        }
    }
}
