package caojx.learn.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

public class SpringMain {

    public static void main(String[] args) throws InterruptedException {
      AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:content.xml");

      //RabbitMQ模板
      RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
      //发送消息
      template.convertAndSend("hello world");
      Thread.sleep(1000);//休眠1s
      ctx.destroy();

    }
}
