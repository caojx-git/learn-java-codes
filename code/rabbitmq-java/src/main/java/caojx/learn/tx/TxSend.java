package caojx.learn.tx;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 事务模式
 */
public class TxSend {

    //队列名称
    private static final String QUEUE_NAME = "test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException {

        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2获取channel
        Channel channel = connection.createChannel();

        //3.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msgString = "hello tx message!";
        try{
            //4.开启事务模式
            channel.txSelect();

            channel.basicPublish("", QUEUE_NAME, null, msgString.getBytes());

            //异常回滚
            //int xx = 1/0;

            System.out.println("send message:"+msgString);
            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();
            System.out.println("send message rollback!");
        }

        channel.close();
        connection.close();

    }
}
