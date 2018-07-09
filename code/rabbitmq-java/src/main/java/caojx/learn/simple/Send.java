package caojx.learn.simple;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列发送消息
 */
public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        //1.获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //2.从连接中获取一个通道
        Channel channel = connection.createChannel();

        //3.创建队列生名
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //4.发送消息
        String msg = "hello simple";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("--send msg:"+msg);

        channel.close();
        connection.close();
    }
}
