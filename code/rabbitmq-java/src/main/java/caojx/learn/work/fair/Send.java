package caojx.learn.work.fair;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列，发送消息 --公平分发
 */
public class Send {


    /**
     *                          |---c1
     * p --------Queue----------|
     *                          |---c2
     */
    private static final String QUEUE_NAME = "test_work_queue";


    public static void main(String[] args) throws IOException, TimeoutException {

        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2获取channel
        Channel channel = connection.createChannel();

        //3.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /**
         * 每个消费者发送确认消息之前，纤细队列不会发送下一个消息到消费者，一次只处理一个消息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        //4.发送消息
        for (int i = 0; i < 50; i++) {
            String msg = "hello"+i;
            channel.basicPublish("",QUEUE_NAME, null, msg.getBytes());
        }
        System.out.println("消息发送完毕");
        channel.close();
        connection.close();
    }
}
