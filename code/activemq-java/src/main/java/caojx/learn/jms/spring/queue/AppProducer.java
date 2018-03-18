package caojx.learn.jms.spring.queue;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:43
 * 消息提供者
 */
public class AppProducer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jms-producer.xml");
        ProducerService service = context.getBean(ProducerService.class);
        for (int i = 0; i < 100; i++) {
            service.sendMessage("test"+i);
        }
        context.close();
    }
}
