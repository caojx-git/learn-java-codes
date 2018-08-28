package caojx.learn.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * Kafka Producer
 * 1.启动zookeeper: zkServer start (配置文件 kafka/zookeeper.properties)
 * 2.启动kafka: ./kafka-server-start.sh ../config/server.properties (配置文件 server.properties)
 * 3.创建topic: kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic kafka-study-m
 * 4.kafka Producer: kafka-console-producer --broker-list localhost:9092 --topic kafka-study-m
 * 5.kafka Comsumer: kafka-console-consumer --bootstrap-server localhost:9092 --topic kafka-study-m --from-beginning
 */
public class MyProducer {

    private static KafkaProducer<String, String> producer;

    //静态代码块设置属性
    static {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "127.0.0.1:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //自定义分区分配器，可选择配置
        kafkaProps.put("partitioner.class", "caojx.learn.partitioner.Custompartitioner");

        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    /**
     * producer 发送消息不考虑返回信息
     */
    private static void sendMessageForgetResult() {
        ProducerRecord<String, String> record = new ProducerRecord("kafka-study-m", "name", "ForgetResult");
        producer.send(record);
        producer.close();
    }

    /**
     * producer 发送消息同步等待返回信息
     * @throws Exception
     */
    private static void sedMessageSync() throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord("kafka-study-m", "name", "sync");
        RecordMetadata result = producer.send(record).get();

        System.out.println(result.topic());
        System.out.println(result.partition());
        System.out.println(result.offset());

        producer.close();
    }

    /**
     * producer 发送消息异步回掉返回信息
     */
    public static void sendMessageCallback() {
        ProducerRecord<String, String> record = new ProducerRecord("kafka-study-m", "name", "callback");
        producer.send(record, new MyProducerCallback());
        producer.close();

    }

    private static class MyProducerCallback implements Callback{

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if(e != null){
                e.printStackTrace();
                return;
            }
            System.out.println(recordMetadata.topic());
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.offset());
        }
    }

    public static void main(String[] args) throws Exception{
        sendMessageForgetResult();
        sedMessageSync();
        sendMessageCallback();
    }
}
