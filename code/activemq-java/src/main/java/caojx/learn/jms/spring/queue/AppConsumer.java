package caojx.learn.jms.spring.queue;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:57
 * 消息消费者
 */
public class AppConsumer {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jms-consumer.xml");
    }
}
