package client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import server.ws02.cxf.spring.Order;
import server.ws02.cxf.spring.OrderWS;

public class SpringClientTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-beans.xml");
        OrderWS orderWS = (OrderWS) context.getBean("orderClient");
        Order order = orderWS.getOrderById(24);
        System.out.println(order.getId()+"-"+order.getName()+"-"+order.getPrice());
    }
}
