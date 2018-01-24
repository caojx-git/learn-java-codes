package server.ws02.cxf.spring;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class OrderWSImpl implements OrderWS{

    @Override
    public Order getOrderById(int id){
        System.out.println("getOrderById() "+id);
        return new Order(id, "飞机", 100000000);
    }
}
