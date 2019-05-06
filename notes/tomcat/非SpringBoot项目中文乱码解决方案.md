# 非SpringBoot项目中文乱码解决方案

前台页面向后台提交参数的时候，中文经常出现乱码问题，可以检查一下下边的配置。
1.检查项目的编码是否为UTF-8
2.设置tomcat服务的编码，在server.xml文件中修改为如下
  <Connector URIEncoding="UTF-8" port="8888" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
3.在web.xml中添加编码过滤配置设置为UTF-8，这里使用springmvc的编码过滤

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <!--方式一，使用默认配置
    默认读取的springmvc配置文件为/WEB-INF/<servlet-name>-servlet.xml
    -->
  <!--  <servlet>
        <servlet-name>springmvc</servlet-name>
        &lt;!&ndash; springMVC的入口，分发器，管家 ,分发器默认读取/WEB-INF/<servlet-name>-servlet.xml文件&ndash;&gt;
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        &lt;!&ndash; 1表示tomcat启动的时候，springmvc也初始化 &ndash;&gt;
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>&lt;!&ndash; /表示拦截所有请求，也可以 *.do   *.html 等  &ndash;&gt;
    </servlet-mapping>-->

    <!--方式二，手动指定springmvc配置文件的路径
        推荐使用这种方式
    -->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 读取指定目录下的配置文件，名字可以改变
            *表示加载该目录以及子目录下的所有springmvc-servlet.xml
            不加*只会加载指定路径下的指定文件
        -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>
                classpath*:configs/springmvc-annotaion-servlet.xml
            </param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern><!-- /表示拦截所有请求，也可以 *.do   *.html 等  -->
    </servlet-mapping>

    <!--编码过滤,使用spring的编码过滤类-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name><!--设置为那种编码-->
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name><!--是否强制过滤-->
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern><!--那种请求需要编码过滤,这里对所有的请求进行编码过滤-->
    </filter-mapping>
</web-app>
```
