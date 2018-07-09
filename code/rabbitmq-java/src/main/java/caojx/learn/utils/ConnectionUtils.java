package caojx.learn.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    /**
     * 获取RabbitMQ连接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {

        //1.定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //2.设置服务地址
        factory.setHost("127.0.0.1");

        //AMQP端口
        factory.setPort(5672);

        //vhost
        factory.setVirtualHost("/vhost_caojx");

        //用户名 密码
        factory.setUsername("caojx");
        factory.setPassword("caojx");

        return factory.newConnection();
    }
}
