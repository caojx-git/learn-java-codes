package caojx.learn.routing;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 */
public class Reciver2 {

    private static final String EXCHANGE_NAME = "test_exchange_direct";
    private static final String QUEUE_NAME = "test_queue_direct_2";


    public static void main(String[] args) throws IOException, TimeoutException {

        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2.获取channel
        final Channel channel = connection.createChannel();

        //3.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);

        //4.绑定交换机和routingKey,绑定多个routingKey
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,  "error");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,  "info");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,  "warning");



        //5.接收消息
        DefaultConsumer consumer =  new DefaultConsumer(channel) {
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body, "utf-8");
                System.out.println("reciver:"+msgString);
                ////发送手动的回执
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        //自动应答改成false
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }
}
