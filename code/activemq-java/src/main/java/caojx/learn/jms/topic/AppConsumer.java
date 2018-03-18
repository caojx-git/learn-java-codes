package caojx.learn.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午12:56
 * jms主题模式-消息订阅者
 */
public class AppConsumer {

    public static final String url="tcp://127.0.0.1:61616"; //连接activemq的地址，61616是连接activemq默认的端口

    public static final String topicName="topic-test"; //主题名称

    public static void main(String[] args) throws JMSException {

        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话，第一个参数表示是否在事务中处理，由于是演示代码所以不使用事务false，第二个参数是连接应答模式，Session.AUTO_ACKNOWLEDGE表示自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标
        Destination destination = session.createTopic(topicName);

        //6.创建一个订阅者,并指定目标 主题
        MessageConsumer consumer = session.createConsumer(destination);

       //7.创建一个监听器,接收消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //注意，由于消息接收是异步的，所以不能关闭connection
        //connection.close();
    }
}
