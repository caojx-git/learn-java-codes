package server.ws02.cxf.spring;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface OrderWS {

    @WebMethod
    public Order getOrderById(int id);
}
