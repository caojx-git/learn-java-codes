package server.datetype;


import javax.xml.ws.Endpoint;

public class ServerTest {

    public static void main(String[] args) {
        System.out.println("发布开始");
        //发布地址
        String address = "http://127.0.0.1:8989/dateType/dateTypews";
        //指定发布的地址和SEI实现类对象
        Endpoint.publish(address, new DateTypeWSImpl());
        System.out.println("发布完成");
    }
}
