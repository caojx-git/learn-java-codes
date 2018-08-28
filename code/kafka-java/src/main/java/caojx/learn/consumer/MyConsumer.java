package caojx.learn.consumer;

import kafka.Kafka;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author caojx
 * @version 1.0
 * @description Kafka Consumer
 * @date 2018-07-09
 */
public class MyConsumer {

    private static KafkaConsumer<String, String> consumer;

    private static Properties kafkaPro;

    static {
        kafkaPro = new Properties();
        kafkaPro.put("bootstrap.servers", "127.0.0.1:9092");
        kafkaPro.put("group.id", "kafkaStudy");
        kafkaPro.put("key.deserializer", "org.apache.kafka.serialization.StringDeserializer");
        kafkaPro.put("value.deserializer", "org.apache.kafka.serialization.StringDeserializer");
    }

    /**
     * 1.consumer 自动提交消息位移
     */
    private static void generalConsumerAutoCommit() {
        kafkaPro.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<String, String>(kafkaPro);
        consumer.subscribe(Collections.singleton("kafka-study-m"));

        try {
            while (true) {
                boolean flag = true;
                //间隔100ms,拉取一次数据
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic = %s, partition = %s, key = %s", record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }
                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 2.consumer 手动同步提交消息位移
     */
    public static void generalConsumeMessageSyncCommit() {
        kafkaPro.put("auto.commit.offset", true);
        consumer = new KafkaConsumer<String, String>(kafkaPro);
        consumer.subscribe(Collections.singleton("kafka-study-m"));

        while (true) {
            boolean flag = true;
            //间隔100ms,拉取一次数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic = %s, partition = %s, key = %s", record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }
            try {
                consumer.commitSync();
            } catch (CommitFailedException e) {
                System.out.println("commit fail error: " + e.getMessage());
            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 3.consumer 手动异步提交消息位移
     */
    private static void generalConsumeMessageAsyncCommit() {
        kafkaPro.put("auto.commit.offset", true);
        consumer = new KafkaConsumer<String, String>(kafkaPro);
        consumer.subscribe(Collections.singleton("kafka-study-m"));

        while (true) {
            boolean flag = true;
            //间隔100ms,拉取一次数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic = %s, partition = %s, key = %s", record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }
            consumer.commitAsync();
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 4.consumer 手动异步提交消息位移带回调
     */
    public static void generalConsumeMessageAsyncCommitWithCallback() {
        kafkaPro.put("auto.commit.offset", true);
        consumer = new KafkaConsumer<String, String>(kafkaPro);
        consumer.subscribe(Collections.singleton("kafka-study-m"));

        while (true) {
            boolean flag = true;
            //间隔100ms,拉取一次数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic = %s, partition = %s, key = %s", record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                if (record.value().equals("done")) {
                    flag = false;
                }
            }
            consumer.commitAsync((offset, e) -> {
                if (null != e) {
                    System.out.println("commit async callback error: " + e.getMessage());
                }
            });
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 5.consumer 混合同步与异步提交消息位移
     */
    public static void mixSyncAndsyncCommit() {
        kafkaPro.put("auto.commit.offset", true);
        consumer = new KafkaConsumer<String, String>(kafkaPro);
        consumer.subscribe(Collections.singleton("kafka-study-m"));
        try {
            while (true) {
                //间隔100ms,拉取一次数据
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic = %s, partition = %s, key = %s", record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                }
                //异步提交
                consumer.commitAsync();
            }
        } catch (Exception e) {
            System.out.println("commit async error: " + e.getMessage());
        } finally {
            try {
                //同步提交
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }
}
