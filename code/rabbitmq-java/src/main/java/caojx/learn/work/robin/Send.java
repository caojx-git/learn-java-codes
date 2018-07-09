package caojx.learn.work.robin;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列，发送消息 --轮询分发round-robin
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
