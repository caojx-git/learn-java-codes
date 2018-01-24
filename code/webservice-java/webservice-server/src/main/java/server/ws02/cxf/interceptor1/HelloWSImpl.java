package server.ws02.cxf.interceptor1;

import javax.jws.WebService;

/**
 * SEI的实现类
 */
@WebService
public class HelloWSImpl implements HelloWS {

    @Override
    public String sayHello(String name) {
        System.out.println("server sayHello()"+name);
        return "hello "+name;
    }
}
