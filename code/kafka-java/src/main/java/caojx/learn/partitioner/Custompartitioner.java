package caojx.learn.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * @author caojx
 * @version 1.0
 * @description 自定义分区分配器
 * @Copyright (c) 2018, xxx All Rights Reserved.
 * @date 2018-07-09
 */
public class Custompartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfoList = cluster.partitionsForTopic(topic);
        int numpatitions = partitionInfoList.size();
        System.out.println("current topic is: " + topic);
        System.out.println("current topic has: " + numpatitions + " patitions!");

        if (null == keyBytes || !(key instanceof String)) {
            throw new InvalidRecordException("kafka message must have key!");
        }

        if (numpatitions == 1) {
            return 0;
        }

        if (key.equals("name")) {
            return numpatitions - 1;
        }

        return Math.abs(Utils.murmur2(keyBytes)) % (numpatitions - 1);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
