package caojx.learn.simple;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者获取消息 旧的接收消息方式
 */
public class Reciver {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2.创建频道
        Channel channel = connection.createChannel();

        //3.定义队列的消费者  旧的接收消息方式
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //4.监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msgString = new String(delivery.getBody());
            System.out.println("[recv] msg："+msgString);
        }
    }
}
