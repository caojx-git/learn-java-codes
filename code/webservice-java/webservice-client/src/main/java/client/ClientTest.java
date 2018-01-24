package client;

import server.ws01.HelloWSImpl;
import server.ws01.HelloWSImplService;

/**
 * 调用WebService
 */
public class ClientTest {

    public static void main(String[] args) {
        //获取factory对应wsdl中<service name="HelloWSImplService">中的name值
        HelloWSImplService factory = new HelloWSImplService();
        //获取具体的服务对应<port name="HelloWSImplPort" binding="tns:HelloWSImplPortBinding">...</port>中的get+name值
        HelloWSImpl helloWS = factory.getHelloWSImplPort();
        System.out.println(helloWS.getClass());
        String result = helloWS.sayHello("jack");
        System.out.println("client:"+result);
    }
}
