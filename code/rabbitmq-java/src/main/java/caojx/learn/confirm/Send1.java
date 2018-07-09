package caojx.learn.confirm;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm 普通模式 单条发送
 */
public class Send1 {

    //队列名称
    private static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {


        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2获取channel
        Channel channel = connection.createChannel();

        //3.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect将channel设置为confirm模式
        channel.confirmSelect();

        //4.发送数据 单条发送
        String msgString = "hello confirm message!";

        channel.basicPublish("", QUEUE_NAME, null, msgString.getBytes());

        //确认
        if(!channel.waitForConfirms()){
            System.out.println("message send failed!");
        }else{
            System.out.println("message send ok");
        }

        channel.close();
        connection.close();
    }
}
