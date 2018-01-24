package client;

import cn.com.webxml.WeatherWS;
import cn.com.webxml.WeatherWSSoap;

public class WeatherClientTest {

    public static void main(String[] args) {
        //创建服务工厂，对应wsdl文档<wsdl:service name="WeatherWS">...</wsdl:service>中的name
        WeatherWS weatherWS = new WeatherWS();
        //创建具体的服务对象，对应wsdl文档中<wsdl:port name="WeatherWSSoap" binding="tns:WeatherWSSoap">...</wsdl:port>中的name
        WeatherWSSoap weatherWSSoap = weatherWS.getWeatherWSSoap();
        //调用天气服务
        System.out.println(weatherWSSoap.getWeather("儋州","").getString());
    }
}
