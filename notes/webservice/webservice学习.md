
# Web Service学习

[toc]

## 一、Web Service简介
![](../images/Web Service/Web Service_0.png)  
我们手机或浏览器中一般都会有查询天气的服务，那么么个公司服务器的数据库中都保存了天气预报数据吗?如果没有, 那数据都存在哪了呢?这些网站是
如何得到这些数据的呢?其实大多这种公共服务都是调用WebService获得的。

### 1.1 Web Service是什么

1. 基于Web的服务：服务器端整出一些资源让客户端应用访问（获取数据）
2. 一个跨语言、跨平台的规范（抽象）
3. 多个跨平台、跨语言的应用间通信整合的方案（实际）

以各个网站显示天气预报功能为例:
气象中心的管理系统将收集的天气信息并将数据暴露出来(通过WebService Server), 而各大站点的应用就去调用它们得到天气信息并以不同的样式去展示(WebService Client).
网站提供了天气预报的服务，但其实它们什么也没有做，只是简单了调用了一下气象中心服务器上的一段代码而已。

### 1.2 为什么要用Web Service
web service能解决如下问题  
- 跨平台调用 
- 跨语言调用
- 远程调用

### 1.3 什么时候使用Web Service
1. 同一家公司的新旧应用之间
2. 不同公司的应用之间
分析业务需求：天猫网与中通物流系统如何交互？
3. 一些提供数据的内容聚合应用：天气预报、股票行情

### 1.4 免费的Web Service
百度或google搜索免费webService接口，如天气查询，号码归属地查询，航班查询等，都有免费的接口。
http://www.webxml.com.cn/zh_cn/web_services.aspx

### 1.5 Web Service中的几个重要术语
>WSDL：web service definition language
```text
直译 : WebService定义语言

1. 对应一种类型的文件.wsdl
2. 定义了webservice的服务器端与客户端应用交互传递请求和响应数据的格式和方式
3. 一个webservice对应一个唯一的wsdl文档
```
>SOAP：simple object  access protocal
```text
直译: 简单对象访问协议

1.	是一种简单的、基于HTTP和XML的协议, 用于在WEB上交换结构化的数据
2.	soap消息：请求消息和响应消息
3.	http+xml片断
```
>SEI：WebService EndPoint Interface(终端)
```text
直译: webservice的终端接口

1. 就是WebService服务器端用来处理请求的接口
```

>CXF：Celtix + XFire
```text
一个apache的用于开发webservice服务器端和客户端的框架
```

## 二、开发Web Service
•	开发手段：  
–	使用JDK开发(1.6及以上版本)  
–	使用CXF框架开发(工作中)  
•	组成：  
–	服务器端  
–	客户端  

### 2.1 使用JDK开发Web Service

1).开发服务器端  
•	webservice编码：  
–	@WebService( SEI和SEI的实现类)  
–	@WebMethod(SEI中的所有方法)  
•	发布webservice：  
–	Endpoint(终端, 发布WebService)  
2). 开发客户端  
•	使用eclipse提供的WebService浏览器访问  
–	查看对应的wsdl文档：…..?wsdl (一般浏览器)  
–	请求webservice并查看请求和响应消息(WebService浏览器)  

•	创建客户端应用编码方式访问  
–	借助jdk的wsimort.exe工具生成客户端代码：  
```text
wsimport -keep url   //url为wsdl文件的路径,-keep是保存生成的java代码的意思  
```
–	借助生成的代码编写请求代码  

- 开发服务端

1. 建立mave工程    
建立mave工程webservice-java项目，包含两个模块webservice-client是web项目，webservice-server是普通java项目分别作用客户端和服务端。
这里只是为了方便测试，将服务端和客户端建在同一个maven项目中，一般情况下服务端都调用其他公司的项目。
![](../images/webservice/webservice-java-project1.png)  

2. HelloWS.java  
定义WebService服务接口，需要在类中添加@WebService注解和方法中添加@WebMethod注解
```java
package server.ws01;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * SEI：定义WebService服务接口
 */

//该注解声明该类是webService类
@WebService
public interface HelloWS {

    //该注解声明该类是webService方法
    @WebMethod
    public String sayHello(String name);
}
```

3. HelloWSImpl.java   
实现HelloWS，只需要在实现类中添加@WebService注解
```java
package server.ws01;

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
```

4. ServerTest.java  
发布webservice
```java
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
```
5. 浏览器访问  
地址：http://127.0.0.1:8989/ws01/hellows?wsdl
![](../images/webservice/webservice_1.png)

- 开发客户端  
1. 根据wsdl文档地址生成可客户端代码  
```text
$cd /webservice-client/src/main/java
$wsimport -keep http://127.0.0.1:8989/ws01/hellows?wsdl
``` 
代码生成结果  
![](../images/webservice/webservice_3.png)

2. 根据生成的客户端代码调用服务  
```java
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
```
结果  
![](../images/webservice/webservice_4.png)    
![](../images/webservice/webservice_5.png)    

## 三、编写天气预报的Web Service
网络上有很多免费的WebService接口如下，提供了一些免费的WebService接口，比如天气查询服务、手机号码归属查询、航班查询等。      
http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl  
下边我们来编写天气预报查询的Web Service

1. 根据wsdl生成客户端代码
```text
wsimport -keep http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl #这样直接生成客户代码出现问题，因为该服务式net写的，所以
java方式在生成客户端代码的时候都需要进行少许的修改
```
解决方案
- 将wsdl文档先保存到本地 [weather.wsd]()
- 将 <s:element ref="s:schema" /><s:any /> 替换成 <s:any minOccurs="2" maxOccurs="2"/> 这个是Java调用net的webservice都有的问题

生成客户端代码  
```text
$cd /webservice-client/src/main/java
$wsimport -keep ~/code/learn/code/webservice-java/webservice-client/src/main/resources/weather.wsdl
```
![](../images/webservice/webservice_6.png)
 
2. WeatherClientTest.java  
调用天气服务
```java
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
       System.out.println(weatherWSSoap.getWeather("上海","").getString());
    }
}
```
结果  
```text
/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=62219:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/lib/tools.jar:/Users/caojx/code/learn/code/webservice-java/webservice-client/target/classes client.WeatherClientTest
[直辖市 上海, 上海, 2013, 2018/01/17 21:31:43, 今日天气实况：气温：8℃；风向/风力：东北风 1级；湿度：81%, 紫外线强度：最弱。空气质量：中。, 紫外线指数：最弱，辐射弱，涂擦SPF8-12防晒护肤品。
健臻·血糖指数：不易波动，天气条件好，血糖不易波动，可适时进行户外锻炼。
感冒指数：较易发，温差较大，较易感冒，注意防护。
穿衣指数：较冷，建议着厚外套加毛衣等服装。
洗车指数：较适宜，无雨且风力较小，易保持清洁度。
空气污染指数：中，易感人群应适当减少室外活动。
, 1月17日 多云, 5℃/14℃, 西北风小于3级, 1.gif, 1.gif, 1月18日 多云转阴, 6℃/11℃, 东北风小于3级, 1.gif, 2.gif, 1月19日 多云, 8℃/12℃, 东北风小于3级, 1.gif, 1.gif, 1月20日 阴转小雨, 8℃/11℃, 东风小于3级, 2.gif, 7.gif, 1月21日 中雨, 6℃/11℃, 东风3-4级转西北风4-5级, 8.gif, 8.gif]
```
## 四、WSDL文档分析

### 4.1 wsdl文档结构
![](../images/webservice/webservice_7.png)  
从上图可以看出wsdl文档结构是如下的，这也是JDK开发WebService时生成的wsdl文档的默认结构，如果使用cxf框架或axis框架开发的cxf与该文档结构可能会略有区别。
```xml
<definitions>
    <types>
        <schema></schema>
    </types>
    <message>
        <part></part>
    </message>
    <portType>
        <operation>
            <input></input>
            <output></output>
        </operation>
    </portType>
    <binding>
        <operation>
            <input></input>
            <output></output>
        </operation>
    </binding>
    <service>
        <port></port>
        <address></address>
    </service>
</definitions>
```
### 4.2 标签作用详情
```xml
<definitions xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:wsp="http://www.w3.org/ns/ws-policy" xmlns:wsp1_2="http://schemas.xmlsoap.org/ws/2004/09/policy" xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://ws01.server/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://schemas.xmlsoap.org/wsdl/" targetNamespace="http://ws01.server/" name="HelloWSImplService">
    <types>
       <!-- <xsd:schema>
            <xsd:import namespace="http://ws01.server/" schemaLocation="http://127.0.0.1:8989/ws01/hellows?xsd=1"/>
        </xsd:schema>-->

        <xs:schema xmlns:tns="http://ws01.server/" xmlns:xs="http://www.w3.org/2001/XMLSchema" version="1.0" targetNamespace="http://ws01.server/">

            <!--定义消息片段-->
            <!--

                请求入参标签片段定义：
                <sayHello>
                    <arg0>string<arg0>
                 </sayHello>

                响应出参标签片段定义：
                 <sayHelloResponse>
                    <return>string</return>
                 <sayHelloResponse>
            -->

            <xs:element name="sayHello" type="tns:sayHello"/>
            <xs:element name="sayHelloResponse" type="tns:sayHelloResponse"/>
            <xs:complexType name="sayHello">
                <xs:sequence>
                    <xs:element name="arg0" type="xs:string" minOccurs="0"/>
                </xs:sequence>
            </xs:complexType>
            <xs:complexType name="sayHelloResponse">
                <xs:sequence>
                    <xs:element name="return" type="xs:string" minOccurs="0"/>
                </xs:sequence>
            </xs:complexType>
        </xs:schema>
    </types>



    <!--
        message: 用于定义消息的结构，声明消息中包含的片段
            part：用于指定引用的types中定义的标签片段
    -->
    <message name="sayHello">
        <part name="parameters" element="tns:sayHello"/>
    </message>
    <message name="sayHelloResponse">
        <part name="parameters" element="tns:sayHelloResponse"/>
    </message>

    <!--
        portType:用来定义服务器端的SEI
               operation：用来指定SEI中的请求方法
                    input：指定客户端应用传过来的数据，会引用上边定义的<message>
                    output:指定服务端应用返回给客户端的数据，会引用上边定义的<message>
    -->
    <portType name="HelloWSImpl">
        <operation name="sayHello">
            <input wsam:Action="http://ws01.server/HelloWSImpl/sayHelloRequest" message="tns:sayHello"/>
            <output wsam:Action="http://ws01.server/HelloWSImpl/sayHelloResponse" message="tns:sayHelloResponse"/>
        </operation>
    </portType>

<!--
    binding:用于定义SEI的实现类
        type：引用上边的<portType>
        <soap:binding style="document"/> ：绑定的数据是一个document(xml)
        operation:用来定义实现的方法
            <soap:operation soapAction="" style="document"/>: 传输的是document(xml)
            input:指定客户端应用传过来的数据
                <soap:body use="literal"/>：文本数据
            output:指定服务端返回给客户端的数据
                <soap:body use="literal"/>：文本数据

-->
    <binding name="HelloWSImplPortBinding" type="tns:HelloWSImpl">
        <soap:binding transport="http://schemas.xmlsoap.org/soap/http" style="document"/>
        <operation name="sayHello">
            <soap:operation soapAction="" style="document"/>
            <input>
                <soap:body use="literal"/>
            </input>
            <output>
                <soap:body use="literal"/>
            </output>
        </operation>
    </binding>

    <!--
        service：相当于一个webservice容器
            name属性：用来指定一个客户端容器类
            port属性：用来指定一个服务器端处理的请求入口（就是SEI的实现）
                binding属性：引用上边定义的<binding>
                address:当前webservice请求地址
    -->
    <service name="HelloWSImplService">
        <port name="HelloWSImplPort" binding="tns:HelloWSImplPortBinding">
            <soap:address location="http://127.0.0.1:8989/ws01/hellows"/>
        </port>
    </service>
</definitions>
```
### 4.3 重要标签的说明
- types - 数据类型(标签)定义的容器，里面使用schema定义了一些标签结构供message引用 
- message - 通信消息的数据结构的抽象类型化定义。引用types中定义的标签
- operation - 对服务中所支持的操作的抽象描述，一个operation描述了一个访问入口的请求消息与响应消息对。
- portType - 对于某个访问入口点类型所支持的操作的抽象集合，这些操作可以由一个或多个服务访问点来支持。
- binding - 特定端口类型的具体协议和数据格式规范的绑定。
- service- 相关服务访问点的集合
- port - 定义为协议/数据格式绑定与具体Web访问地址组合的单个服务访问点。


## 五、使用CXF开发WebService

### 5.1 CXF简介


### 5.2 CXF支持的数据类型

1. 基本类型    
- short，char，float,double，int,long，boolean等  
2. 引用类型  
- String  
- 集合：数组，List, Set, Map（JDK开发WebService不支持Map类型） 
- 自定义类型   Student  


参考：  
https://www.cnblogs.com/holbrook/archive/2012/12/12/2814821.html
http://blog.csdn.net/kongxx/article/details/7544640  