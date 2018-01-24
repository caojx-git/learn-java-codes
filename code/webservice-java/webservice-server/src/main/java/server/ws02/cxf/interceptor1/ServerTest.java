package server.ws02.cxf.interceptor1;

import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.message.Message;
import server.ws02.cxf.interceptor2.CheckUserInterceptor;

import javax.xml.ws.Endpoint;
import java.util.List;

/**
 * 发布WebService,并添加入拦截器和出拦截器，添加后我们可以在debug窗口看到请求响应日志
 */
public class ServerTest {

    public static void main(String[] args) {
        System.out.println("发布开始");
        //发布地址
        String address = "http://127.0.0.1:8989/ws02/interceptor1";
        //指定发布的地址和SEI实现类对象
        Endpoint endpoint = Endpoint.publish(address, new HelloWSImpl());

        EndpointImpl endpointImpl = (EndpointImpl) endpoint;
        //服务端的日志入拦截器
        List<Interceptor<? extends Message>> inInterceptors = endpointImpl.getInInterceptors();
        inInterceptors.add(new LoggingInInterceptor());
        inInterceptors.add(new CheckUserInterceptor()); //拦截器校验用户名或密码
        //服务端的日志出拦截器
        List<Interceptor<? extends Message>> outInterceptors = endpointImpl.getOutInterceptors();
        outInterceptors.add(new LoggingOutInterceptor());

        System.out.println("发布完成");
    }
}
