package caojx.learn.routing;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {

        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2.获取channel
        Channel channel = connection.createChannel();

        //3.声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //4.发送消息
        String msg ="hello direct";
        String routingKey = "error";

        //设置routingKey，只有绑定了对应routingKey的队列才可以接收到消息
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

        System.out.println("消息发送完毕："+msg);

        //5.关闭资源
        channel.close();
        connection.close();
    }
}
