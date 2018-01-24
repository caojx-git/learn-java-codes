package server.ws01;

import javax.xml.ws.Endpoint;

/**
 * 发布WebService
 */
public class ServerTest {

    public static void main(String[] args) {
        System.out.println("发布开始");
        //发布地址
        String address = "http://127.0.0.1:8989/ws01/hellows";
        //指定发布的地址和SEI实现类对象
        Endpoint.publish(address, new HelloWSImpl());
        System.out.println("发布完成");
    }
}
