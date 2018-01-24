package client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;
import server.ws02.cxf.interceptor1.HelloWS;
import server.ws02.cxf.interceptor1.HelloWSImplService;

import java.util.List;

/**
 * 调用WebService，使用自定义拦截器
 */
public class InterceptorClientTest2 {

    public static void main(String[] args) {
        //获取factory对应wsdl中<service name="HelloWSImplService">中的name值
        HelloWSImplService factory = new HelloWSImplService();
        //获取具体的服务对应<port name="HelloWSImplPort" binding="tns:HelloWSImplPortBinding">...</port>中的get+name值
        HelloWS helloWS = factory.getHelloWSImplPort();
        System.out.println(helloWS.getClass());

        //发送请求的客户端对象
        Client client = ClientProxy.getClient(helloWS);

        //客户端出拦截器
        //客户端的日志入拦截器
        List<Interceptor<? extends Message>> inInterceptors = client.getInInterceptors();
        inInterceptors.add(new LoggingInInterceptor());

        //客户端的日志出拦截器
        List<Interceptor<? extends Message>> outInterceptors = client.getOutInterceptors();
        outInterceptors.add(new LoggingOutInterceptor());
        //自定义拦截器
        outInterceptors.add(new AddUserInterceptor("tom", "123"));

        String result = helloWS.sayHello("jack");
        System.out.println("client:"+result);
    }
}
