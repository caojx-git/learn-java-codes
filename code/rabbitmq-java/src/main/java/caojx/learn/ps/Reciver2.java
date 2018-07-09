package caojx.learn.ps;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式-消息订阅者
 */
public class Reciver2 {

    //队列名称
    private static final String QUEUE_NAME = "test_queue_fanout_sms";
    //交换机名称
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2获取channel
        Channel channel = connection.createChannel();

        //3.队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //4.绑定到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");


        //5.接收消息
        DefaultConsumer consumer =  new DefaultConsumer(channel) {
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body, "utf-8");
                System.out.println("reciver:"+msgString);
            }
        };

        //6.监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
