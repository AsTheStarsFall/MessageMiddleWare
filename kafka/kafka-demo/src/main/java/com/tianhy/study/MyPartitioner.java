package com.tianhy.study;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Description: 自定义分区策略
 * @Author: thy
 * @Date: 2020/1/28 11:51
 */
public class MyPartitioner implements Partitioner {

    //根据recoder计算分区
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
                         Cluster cluster) {
        System.out.println("Compute partition for the given record");
        //首先根据topic拿到对应的分区信息
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        //获取分区列表的长度
        int size = partitionInfos.size();
        //如果key为Null，则随机
        if (key == null) {
            Random random = new Random();
            //返回一个范围在0~size之间的正整数
            return random.nextInt(size);
        }
        //否则根据key计算分区
        return Math.abs(key.hashCode()) % size;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
