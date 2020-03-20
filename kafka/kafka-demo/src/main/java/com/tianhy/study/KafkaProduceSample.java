package com.tianhy.study;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

/**
 * @Description: 消息生产
 * @Author: thy
 * @Date: 2020/1/27 20:05
 */
public class KafkaProduceSample extends Thread {

    public static final String KAFKA_CLUSTER = "119.23.249.97:9092,119.23.249.97:9093,119.23.249.97:9094";

    private KafkaProducer<Integer, String> producer;

    private String topic;

    public KafkaProduceSample(String topic) {
        //Kafka produce配置
        Properties properties = new Properties();
        //Kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CLUSTER);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "test-cid-1");
        //分区策略
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.tianhy.study.MyPartitioner");
        //TODO kafka的消息以键值对的形式发送到kafka服务器，在消息发送到服务器之前，需要把不同类型的消息序列化为二进制类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                //生产的消息
                String msg = "Produce msg - " + i;
/*                //同步(阻塞)
                producer.send(new ProducerRecord<>(topic, 1, msg), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {

                    }
                });*/
                //异步
                producer.send(new ProducerRecord<>(topic, 1, msg), (metadata, exception) -> {
                    System.out.println("偏移量：" + metadata.offset() + ",分区：" + metadata.partition() + ",主题：" + metadata.topic());
                });
                Thread.sleep(3);
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        new KafkaProduceSample("topic-with-replicas").start();
    }
}
