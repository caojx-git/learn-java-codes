package caojx.learn.confirm;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm 普通模式 批量发送
 */
public class Send2 {

    //队列名称
    private static final String QUEUE_NAME = "test_queue_confirm2";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {


        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2获取channel
        Channel channel = connection.createChannel();

        //3.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect将channel设置为confirm模式
        channel.confirmSelect();

        //4.发送批量数据
        for (int i = 0; i < 10; i++) {
            String msgString = "hello confirm batch message"+i;
            channel.basicPublish("", QUEUE_NAME, null, msgString.getBytes());
        }

        //确认
        if(!channel.waitForConfirms()){
            System.out.println("message batch send failed!");
        }else{
            System.out.println("message batch send ok");
        }

        channel.close();
        connection.close();
    }
}
