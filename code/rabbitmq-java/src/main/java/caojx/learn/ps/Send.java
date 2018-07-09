package caojx.learn.ps;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅-消息发布者
 */
public class Send {

    //交换机名称
    private static final String EXCHANGE_NAME = "test_exchange_fanout";


    public static void main(String[] args) throws IOException, TimeoutException {

        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2获取channel
        Channel channel = connection.createChannel();

        //3.声明交换机
        /**
         * fanout表示不处理路由键，只需要将队列绑定到对应的交换机，发送消息到交换机，消息就会转发到与交换机绑定的所有队列
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //分发

        //4.发送消息
        String msg = "hello ps";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

        //5.关闭资源
        channel.close();
        connection.close();

        System.out.println("发送完毕");


    }

}
