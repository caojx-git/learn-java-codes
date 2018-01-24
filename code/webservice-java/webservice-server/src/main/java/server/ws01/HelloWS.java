package server.ws01;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * SEI：定义WebService接口
 */

//该注解声明该类是webService类
@WebService
public interface HelloWS {

    //该注解声明该类是webService方法
    @WebMethod
    public String sayHello(String name);
}
