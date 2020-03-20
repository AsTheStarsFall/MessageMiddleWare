package com.tianhy.study;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Description: 消费者
 * @Author: thy
 * @Date: 2020/1/27 20:33
 */
public class KafkaConsumerSample extends Thread {

    /**
     * Kafka 集群
     */
    private static final String KAFKA_CLUSTER = "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094";

    private KafkaConsumer<Integer, String> consumer;

    private String topic;

    public KafkaConsumerSample(String topic) {

        Properties properties = new Properties();
        //Kafka集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CLUSTER);
        //client.id
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "test-cid-1");
        //group.id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-groupid-1");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); //自动提交(批量确认)
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<Integer, String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {
        //订阅主题，如果消费指定分区的时候，就不需要订阅
        consumer.subscribe(Collections.singleton(topic));

/*        //指定消费的分区
        TopicPartition topicPartition = new TopicPartition(topic, 0);
        consumer.assign(Collections.singleton(topicPartition));*/

        while (true) {

            //消费消息
            ConsumerRecords<Integer, String> poll = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<Integer, String> record : poll) {
                System.out.println("key=" + record.key() + ",value=" + record.key() + "偏移量：" + record.offset());
            }
        }
    }

    public static void main(String[] args) {
        new KafkaConsumerSample("test_topic").start();
    }
}
