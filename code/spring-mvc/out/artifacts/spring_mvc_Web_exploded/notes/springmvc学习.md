#SpringMVC

###一.Spring MVC 优点
	
   Spring MVC 和其他的Web框架一样，基于MVC设计理念
   采用松散耦合可拔插组件结构，比其他MVC框架更具有哦灵活性和扩展性。
   Spring MVC还支持REST风格的URL请求：注解驱动以及REST风格的SpringMVC
   是Spring3.0最出彩的功能之一，此外Spring MVC在数据绑定、视图解析、本地化
   以及静态资源处理都有许多不俗的表现。它在框架的设计上已经超越了Struts、webwork
   等框架。
###二.工作原理

a.DispathcerServlet是其核心，相当于Spring MVC中的大管家。在web.xml中配置后，拦截所有的HTTP请求

b.DispatcherServlet接受到请求后将其交给HandlerMapping处理，相当于路由，HandlerMapping将请求映射到相应的Handler可以看成是目标主机

c.DispatcherServlet收到HandlerMapping返回的Handler后，通过HandlerAdapter对Hanlder进行封装，然后返回ModeAndView包含了视图逻辑名和模型数据

d.DispatcherSerlvet收到ModeAndView后将其交给ViewResolver解析，将逻辑视图名得到正真的视图对象View

c.DispatcherServelt得到真正视图对象View后，就使用View和ModeAndView中的模型数据进行视图渲染。

d.最终展现给客户端--》图片、pdf文档、html页面、xml、json数据等


###三.快速入门(基于xml配置)
提示：springmvc实现主要有两种写法，xml配置和注解，注解是主流，这里先说xml配置方式的实现，后边
再使用注解的方式。
	
####3.1 新建在IDEA中创建JavaEE Web工程
	
>File->new Project->Java->JavaEE->Web Application-->工程名/工程保存位置

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-web.png)
	
####3.2 导入Springmvc中需要的jar包
Project Structure->Project Settings->Moules->Add(jar包的路径)
这里添加jar的方式跟Eclipse有点不同，但是思想都是一样的。
我这里将jar添加到WEB-INF的lib下边（lib目录不存在需要手动建立）

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-jar.png)

提示：项目相关的配置都在Project Settings里边设置，不仅是jar，这个新建的项目常常需要检查这里的配置是都正确
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-jar2.png)
	
注意Facets中的Source Roots：下边的内容要打勾，不然会找不到类
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-sourceroot.png)
	
####3.3web.xml配置
对于web工程来说，项目中用到的web框架基本都需要在这里配置，springmvc同样如此，web.xml是springmvc
的入口。

如果在web.xml中不手动制定配置文件的路径，将会读取默认的springmvc配置文件。

**a. web.xml springmvc默认读取/WEB-INF/<servlet-name>-servlet.xml**

默认方式所在的路径

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-webxml.png)

内容：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <!--方式一，不指定springmvc配置文件的路径-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <!-- springMVC的入口，分发器，管家 ,分发器默认读取/WEB-INF/<servlet-name>-servlet.xml文件-->
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 1表示tomcat启动的时候，springmvc也初始化 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern><!-- /表示拦截所有请求，也可以 *.do   *.html 等  -->
    </servlet-mapping>

</web-app>
```


**b.将springmvc配置文件配置到指定的路径下 如放置到src/configs下**
指定路径方式的截图

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-webxml2.png)

推荐使用指定路径的方式

内容：
```xml
<?xml version="1.0" encoding="UTF-8"?>
 <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
          version="3.1">
     <!--方式一，不指定springmvc配置文件的路径默认
     器默认读取/WEB-INF/<servlet-name>-servlet.xml文件
     -->
   <!--  <servlet>
         <servlet-name>springmvc</servlet-name>
         &lt;!&ndash; springMVC的入口，分发器，管家 ,分发器默认读取/WEB-INF/<servlet-name>-servlet.xml文件&ndash;&gt;
         <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
         &lt;!&ndash; tomcat启动的时候，springmvc也初始化 &ndash;&gt;
         <load-on-startup>1</load-on-startup>
     </servlet>
     <servlet-mapping>
         <servlet-name>springmvc</servlet-name>
         <url-pattern>/</url-pattern>&lt;!&ndash; /表示拦截所有请求，也可以 *.do   *.html 等  &ndash;&gt;
     </servlet-mapping>-->
 
     <!--方式二，手动指定springmvc配置文件的路径
         
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
             <param-value>classpath*:configs/springmvc-servlet.xml</param-value>
         </init-param>
         <load-on-startup>1</load-on-startup>
     </servlet>
     <servlet-mapping>
         <servlet-name>springmvc</servlet-name>
         <url-pattern>/</url-pattern><!-- /表示拦截所有请求，也可以 *.do   *.html 等  -->
     </servlet-mapping>
 </web-app>
```


####3.4 welcom.jsp

路径如图：

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-welcome.png)

内容：
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	hello springmvc
</body>
</html>
```

####3.4 HelloWorldController.java
```java
package com.learn.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by caojx on 16-12-28.
 * 基于xml文件配置的Controller需要实现org.springframework.web.servlet.mvc.Controller
 */
public class HelloWorldController implements Controller {

    /*
    * description:
    * handleRequest方法返回的是ModelAndView，这个类有很多对象，可以返回视图的路径，也可以返回视图路径和数据
    * 如下是返回试图welcome.jsp的路径
    * */
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("/jsp/welcome");
    }
}
```

####3.5 springmvc配置文件springmvc-servlet.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--配置bean,name的值将来会作为访问路径-->
        <bean name="/test/helloworld" class="com.learn.controller.HelloWorldController"></bean>
        <!--配置视图解析器，springMVC需要在配置文件这中配置试图解析器才正常解析视图-->
        <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/WEB-INF/views/"></property><!--指定jsp路径所在的前面一部分路径（前缀）-->
            <property name="suffix" value=".jsp"></property><!--试图视图资源的后缀，我这里使用jsp-->
        </bean>
</beans>
```  

####3.6 添加tomcat服务器
Edit Configurations

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-tomcat-add.png)

点击"+",找到tomcat

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-tomcat-add2.png)

Name：取个名字，随便，图中可以看出有错，这个因为没有Deployment(tomcat中还没有部署项目)
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-tomcat-add3.png)

点击fix或Deployment，部署项目，点击中间的"+"
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-tomcat-add4.png)

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-tomcat-add5.png)

点击Apply-->ok-->运行

####3.6 结果

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-result.png)



###四.数据传递

HelloWorldController返回数据后，在welcome.jsp中进行接收

####4.1HelloWorldController.java

```java
package com.learn.controller;

import org.apache.commons.logging.impl.WeakHashtable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caojx on 16-12-28.
 * 基于xml文件配置的Controller需要实现org.springframework.web.servlet.mvc.Controller
 */
public class HelloWorldController implements Controller {

    /*
    * description:
    * handleRequest方法返回的是ModelAndView，这个类有很多对象，可以返回视图的路径，也可以返回视图路径和数据
    * 如下是返回试图welcome.jsp的路径
    * */
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        /*
        * 1.返回试图路径
        * */
        //return new ModelAndView("/jsp/welcome");

        /*
        * 2.返回视图路径和model数据
        * String viewName 试图的路径
        * String modelName
        * Object modelObject
        * */
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("map1","小明1");
        map.put("map2","小明2");
        map.put("map3","小明3");
        map.put("map4","小明4");
        return new ModelAndView("/jsp/welcome","map",map);
    }
}

```

####4.2welcome.jsp
```jsp
<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 16-12-28
  Time: 下午11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>HelloWorldController</title>
</head>
<body>
    hello springmvc

    <h1>返回的数据</h1>
    <c:forEach items="${map}" var="m">
        ${m.key}----${m.value}<br/>
    </c:forEach>
</body>
</html>
```
####4.3结果
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-result1.png)



###五.spring MultiActionController 实现在一个Controller中写多个方法


	步骤1：写一个Controller类extends MultiActionController

	步骤2：自定义方法参数为 HttpServletRequest request,HttpServletResponse response

		public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
		System.out.println("add");
		return new ModelAndView("multi","method","add");
		}

	步骤3：在配置文件中配置参数方法解析器
		
		 <!-- 配置参数方法解析器     action为方法参数名
      			http://localhost:8888/springmvc2/test1/multi?action=update
       		-->
      		<bean id="paramMethodResolver" class="org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver">
      			<property name="paramName" value="action"></property>
      		</bean>
      
	步骤4：配置自定义Controller类
		<!-- 使用参数方法解析器， 引入方法参数解析器，将请求参数映射到具体的方法 
      		http://localhost:8888/springmvc2/test1/multi?action=add
     		 -->
      		<bean name="/test1/multi" class="com.mcao.web.controller.MultiController">
      			<property name="methodNameResolver">
      				<ref bean="paramMethodResolver"/>
      			</property>
      		</bean>

	步骤5：启动web服务器，访问http://localhost:8888/springmvc2/test1/multi?action=add
		action的值就是请求方法

####5.1 MultiController.java

```java
package com.learn.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by caojx on 16-12-29.
 * 实现在一个Controller中写多个方法,需要extends MultiActionController
 */
public class MultiController extends MultiActionController{

    /**
    * 基于配置文件xml实现的多方法Controller中方法的参数必须有两个参数,基于注解的时候不用
    * @param httpServletRequest
    * @param httpServletResponse
    * */
    public ModelAndView add(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("---------add---------");
        return new ModelAndView("/jsp/multi","method","add");
    }

    public ModelAndView update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("---------update---------");
        return new ModelAndView("/jsp/multi","method","update");
    }

}

```

####5.2multi.jsp
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>多方法Controller</title>
</head>
<body>
    <h1>多方法Controller</h1>
    本次请求的方法是${method}
</body>
</html>
```

####5.3 springmvc-serlvet.xml

这里主要添加方法参数解析器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--配置bean,name的值将来会作为访问路径-->
    <bean name="/test/helloworld" class="com.learn.controller.HelloWorldController"></bean>
    <!--2.一个controller中些多个方法-->
    <!--配置bean
        引入方法参数解析器
    -->
    <bean name="/test1/multi" class="com.learn.controller.MultiController">
        <property name="methodNameResolver">
            <ref bean="paramMethodResolver"></ref>
        </property>
    </bean>
    <!--
          配置参数方法解析器     action为方法参数名
         http://localhost:8888/springmvc2/test1/multi?action=update
      -->
    <bean id="paramMethodResolver" class="org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver">
        <property name="paramName" value="action"></property>
    </bean>

    <!--配置视图解析器，springMVC需要在配置文件这中配置试图解析器才正常解析视图-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"></property><!--指定jsp路径所在的前面一部分路径（前缀）-->
        <property name="suffix" value=".jsp"></property><!--试图视图资源的后缀，我这里使用jsp-->
    </bean>
</beans>
```

####5.4 结果

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-multi.png)

项目结构图：

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-project.png)

###六.spring mvc对静态资源的访问

	由于我们在web.xml中配置了如下，会拦截所有的请求，对于静态资源的话，浏览器会重新发送一次请求，也会被拦截
	我们又没有写Controller处理这些静态资源的请求，所以会造成静态资源访问不到。
	<servlet-mapping>
		<servlet-name>springmvc</servlet-name>
		<url-pattern>/</url-pattern><!-- /表示拦截所有请求，也可以 *.do   *.html 等 这样的话就只会拦截到.do 或*.html请求 -->
	</servlet-mapping>

	在springmvc-serlvet.xml中添加，我们就可以顺利访问到我们的静态资源
	<!-- 对静态资源的访问   声明在/imgs/** 的所有资源都不要拦截
	     **表示/imgs/下边以及子文件夹下的所有静态资源
	-->
	<mvc:resources location="/imgs/" mapping="/imgs/**"/>

	html部
		<body>
			<h2>访问图片</h2>
			<div>
		        <img alt="图片未找到" src="../imgs/05.jpg">
			</div>
		</body>


####6.1 StaticController.java

StaticController，是一个多方法Controller，img方法返回视图的路径
```java
package com.learn.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by caojx on 16-12-29.
 */
public class StaticController extends MultiActionController{

    /*
    * 返回static.jsp的视图路径
    * */
    public ModelAndView img(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return new ModelAndView("/jsp/static");
    }
}

```
####6.2static.jsp
```jsp
<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 16-12-29
  Time: 下午10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>静态资源-图片的访问</title>
</head>
<body>
    <h1>图片的访问</h1>
    <div>
        <img src="/images/05.jpg" alt="图片找不到">
    </div>
</body>
</html>
```
####6.3springmvc-servlet.xml
添加对静态资源的处理
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--配置bean,name的值将来会作为访问路径-->
    <bean name="/test/helloworld" class="com.learn.controller.HelloWorldController"></bean>

    <!--2.一个controller中些多个方法-->

    <!--配置bean
        引入方法参数解析器
    -->
    <bean name="/test1/multi" class="com.learn.controller.MultiController">
        <property name="methodNameResolver">
            <ref bean="paramMethodResolver"></ref>
        </property>
    </bean>

    <!--
          配置参数方法解析器     action为方法参数名
         http://localhost:8888/springmvc2/test1/multi?action=update
      -->
    <bean id="paramMethodResolver" class="org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver">
        <property name="paramName" value="action"></property>
    </bean>

    <!--3.配置对静态资源的访问-->

    <!--配置bean，需要引入方法参数解析器-->
    <bean name="/test2/img" class="com.learn.controller.StaticController">
        <property name="methodNameResolver">
            <ref bean="paramMethodResolver"></ref>
        </property>
    </bean>

    <!--配置某些路径下的资源不进行拦截
        配置对静态资源的处理
    -->
    <mvc:resources mapping="/images/**" location="/images/"></mvc:resources>
    <mvc:resources mapping="/js/**" location="/js/"></mvc:resources>
    <mvc:resources mapping="/css/**" location="/css/"></mvc:resources>

    <!--配置视图解析器，springMVC需要在配置文件这中配置试图解析器才正常解析视图-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"></property><!--指定jsp路径所在的前面一部分路径（前缀）-->
        <property name="suffix" value=".jsp"></property><!--试图视图资源的后缀，我这里使用jsp-->
    </bean>
</beans>
```

####6.4 结果
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-img.png)


###七.springmvc注解配置
	
之前使用的都是xml配置方式，其实springmvc使用最多的是注解方式，使用起来非常简单方便
这里我们使用一个新的springmvc的配置文件springmvc-annotation-servlet.xml，之前的那个咱时不用了
    
####7.1 web.xml
在web.xml修改springmvc需要的配置文件所在的路径指向springmvc-annotaion-servlet.xml  
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
</web-app>
```
    
####7.2 springmvc-annotation-servlet.xml

对于springmvc-annotation-servlet.xml 添加注解的支持在spring3.0之前和3.0之后写法上有点不同
    
 **3.0之前**
    
	<!-- 注解扫描包 ,扫描该包下的所有Controller-->
	<context:component-scan base-package="com.learn.controller.annotation" />
	
	<!-- 开启springmvc注解，根据扫描包和url找类   -->
    <bean class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping"></bean>
	<!-- 开启springmvc注解，负责根据url找方法   -->
	<bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter"></bean>

	<!-- 配置视图解析器 -->
	<bean id="viewResolver"
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"></property><!-- 前缀/代码项目跟目录 -->
		<property name="suffix" value=".jsp"></property><!--后缀 -->
	</bean>

springmvc-annotation-servlet.xml 3.0之前内容
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--3.0之前使用springmvc注解-->
    <!--开启扫描包-->
    <context:component-scan base-package="com.learn.annotaion"></context:component-scan>
    <!--3.0之前开启springmvc注解支持-->
    <!-- 开启springmvc注解，根据扫描包和url找类   -->
    <bean class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping"></bean>
    <!-- 开启springmvc注解，负责根据url找方法   -->
    <bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter"></bean>
    

    <!--配置对静态资源进行放行，不拦截-->
    <mvc:resources mapping="/images/**" location="/images/"></mvc:resources>
    <mvc:resources mapping="/js/**" location="/js/"></mvc:resources>
    <mvc:resources mapping="/css/**" location="/css/"></mvc:resources>

    <!--配置视图解析器，springMVC需要在配置文件这中配置试图解析器才正常解析视图-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"></property><!--指定jsp路径所在的前面一部分路径（前缀）-->
        <property name="suffix" value=".jsp"></property><!--试图视图资源的后缀，我这里使用jsp-->
    </bean>
</beans>
```

	
**3.0之后**
开启注解变得非常简单，只需要配置一行就可以开启注解了

    <!-- 注解扫描包 ,扫描该包下的所有Controller-->
    <context:component-scan base-package="com.learn.controller.annotation" />
	<!--开启springmvc注解  3.0之后-->
	<mvc:annotation-driven/>

    <!-- 配置视图解析器 -->
    <bean id="viewResolver"
    	class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    	<property name="prefix" value="/WEB-INF/views/"></property><!-- 前缀/代码项目跟目录 -->
    	<property name="suffix" value=".jsp"></property><!--后缀 -->
    </bean>

springmvc-annotation-servlet.xml 3.0之后的内容
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--开启扫描包-->
    <context:component-scan base-package="com.learn.annotaion"></context:component-scan>
    <!--3.0之后使用springmvc注解-->
    <mvc:annotation-driven></mvc:annotation-driven>
    <!--配置对静态资源进行放行，不拦截-->
    <mvc:resources mapping="/images/**" location="/images/"></mvc:resources>
    <mvc:resources mapping="/js/**" location="/js/"></mvc:resources>
    <mvc:resources mapping="/css/**" location="/css/"></mvc:resources>

    <!--配置视图解析器，springMVC需要在配置文件这中配置试图解析器才正常解析视图-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"></property><!--指定jsp路径所在的前面一部分路径（前缀）-->
        <property name="suffix" value=".jsp"></property><!--试图视图资源的后缀，我这里使用jsp-->
    </bean>
</beans>
```

####7.3 springmvc注解类实现

步骤1：定义一个普通的类 配置注解@Controller //使用注解标示为一个Controller
步骤2：配置请求映射@RequestMapping("/user")  //配置该类的url的路经
步骤3：方法的返回值为逻辑视图名或者ModeAndView

UserController.java 内容

```java
package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description:
 * springmvc注解类的实现,可以通过如下简单步骤实现
 * 步骤1：定义一个普通的类 配置注解@Controller //使用注解标示为一个Controller
 * 步骤2：配置请求映射@RequestMapping("/user")  //配置该类的url的路经
 * 步骤3：方法的返回值为逻辑视图名或者ModeAndView
 * 提示：基于注解的Controller是可以些多个方法的
 * Created by caojx on 16-12-29.
 */
@Controller
public class UserController {

    /**
     * Description:添加用户方法，直接返回试图和数据，使用@RequestMapping设置访问路径
     * value设置访问路径的值，默认是这个
     * method设置请求方式，一般是不限至请求方式的，设置了请求方式，则只会接受设置的方式的请求，不设置的话没有限制
     * 提示：这里方法addUser中并没有参数HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse，
     * 这两个参数在使用注解的时候是非必须的。
     * @return ModelAndView
     */
    @RequestMapping(value="/user/addUser",method = RequestMethod.GET)
    public ModelAndView addUser(){
        System.out.println("-------addUser---------");
        return new ModelAndView("/jsp/annotation","result","addUser");
    }
    
    /**
     * Description:删除用户，直接返回视图路径和数据
     * @return ModelAndView
     * */
    @RequestMapping(value="/user/deleteUser",method = RequestMethod.GET)
    public ModelAndView deleteUser(){
        System.out.println("-------deleteUser---------");
        return new ModelAndView("/jsp/annotation","result","deleteUser");
    }
}
```

####7.4 运行

提示：使用jdk1.8的使用出现如下错误，换成jdk1.7运行就可以正常
 严重: Context initialization failed
    org.springframework.beans.factory.BeanDefinitionStoreException: Failed to read candidate component class: file [/mnt/sda3/learn/code/spring-mvc/out/artifacts/spring_mvc_Web_exploded/WEB-INF/classes/com/learn/annotaion/UserController.class]; nested exception is java.lang.IllegalArgumentException
    	at org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider.findCandidateComponents(ClassPathScanningCandidateComponentProvider.java:281)
        ....
 
 运行结果：
 
 ![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-annotation1.png)


####7.5对springmvc注解进行优化
上边的注解案例中UserController.java类中注解还有很多地方使用起来还是有点别扭，这里对其进行优化一下

```java
package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * 优化springmvc注解类UserController2.java
 * Created by caojx on 16-12-29.
 */
@Controller
@RequestMapping("/user2")
public class UserController2 {

    /**
     * Description:添加用户方法，直接返回试图和数据，使用@RequestMapping设置访问路径
     * value设置访问路径的值，默认是这个,所以可以不用显示写value=""
     * method设置请求方式，一般是不限至请求方式的，不限至
     * @return ModelAndView
     */
    @RequestMapping("/addUser") //由于每个方法RequestMapping("/user2/addUser"),都会出现user2，我们将user2移动到类体注解中
    public ModelAndView addUser(){
        System.out.println("-------addUser 优化版---------");
        return new ModelAndView("/jsp/annotation","result","addUser");
    }

    /**
     * Description:删除用户，直接返回视图路径和数据
     * @return ModelAndView
     * */
    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(){
        System.out.println("-------deleteUser 优化版---------");
        return new ModelAndView("/jsp/annotation","result","deleteUser");
    }

    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @param httpServletRequest
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(HttpServletRequest httpServletRequest){
        String result = "this is toUser----优化版";
        httpServletRequest.setAttribute("result", result);
        return "/jsp/annotation";
    }

}

```

####7.6 优化后的访问结果
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-annotation2.png)




##八.springmvc参数传递

	
###8.1修改编码，加强对中文的支持
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

###8.2参数的几种传递方式（前台到后台，后台到前台）

>前台到后台的参数传递

**方式一:通过前台页面的属性名与后台方法中的方法参数保持一样就可以将参数值注入进来
这种方式比较简单，当参数前台界面传递的参数比较少的时候，使用这种方式**

**方式二：当前台页面的参数名与后台的方法参数不一致时，使用@RequestParam注解，绑定参数**

**方式三：通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中**

####8.2.1前台页面参数名与方法参数名称一致（之前台到后台）
**方式一:通过前台页面的属性名与后台方法中的方法参数保持一样就可以将参数值注入进来
这种方式比较简单，当参数前台界面传递的参数比较少的时候，使用这种方式**

**方式二：当前台页面的参数名与后台的方法参数不一致时，使用@RequestParam注解，绑定参数**

#####8.2.1.1 addUser.jsp（之前台到后台）
添加用户页面，参数名与后台页面保持一致

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>springmvc参数传递添加用户</title>
    <script type="text/javascript">
        function addUser() {
            var form = document.forms[0];
            form.action = "/data/addUser";
            form.method = "get";
            form.submit();
        }
    </script>
</head>
<body>
<h1>添加用户</h1>
<form action="">
    姓名：<input type="text" name="userName"/>
    年龄：<input type="text" name="age"/>
    <input type="submit" value="添加" onclick="addUser()">
</form>
</body>
</html>
```
#####8.2.1.2 userManager.jsp（之前台到后台）
后台方法接收到addUser.jsp页面的参数后，将参数返回到该页面中展示
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
</head>
<body>
    <h1>用户管理</h1>
    姓名：===>${userName}
    年龄：===>${age}
</body>
</html>
```

#####8.2.1.3DataController.java（之前台到后台）
```java
/**
 * Description:springmvc中参数的传递,接收页面传递到Controller中的参数
 * 1.前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
 * 2.当前台页面的参数名与后台的方法参数不一致时，使用@RequestParam注解，绑定参数，这种方式使用的比较多
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data")
public class DataController {

    /**
     * 方式一
     * Description:添加用户方法，直接返回试图
     * 前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
     * @return String
     */
    @RequestMapping("/addUser")
    public String addUser(String userName,String age,HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",userName);
        httpServletRequest.setAttribute("age",age);
        return "/jsp/userManager";
    }

     /**
      * 方式二
      * Description:添加用户的方法，
      * 前台页面和后台方法参数名不一致，使用@RequestParam注解绑定参数，这种方式相比第一种方式更为常用
      * required的默认值为true，表示必须的参数
      * @param name 用户名
      * @param age 年龄
      * @param httpServletRequest
      * @return
      */
      @RequestMapping("/addUser2")
      public String addUser2(@RequestParam(value = "userName",required = true) String name,
                                @RequestParam(value = "age",required = true) int age,
                                HttpServletRequest httpServletRequest){
          System.out.println("-------addUser 接收的参数--name:"+name+"--age:"+name);
          //将接受的参数返回到用户管理页面
          httpServletRequest.setAttribute("userName",name);
          httpServletRequest.setAttribute("age",age);
          return "/jsp/userManager";
      }

    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(){
        return "/jsp/addUser";
    }

}
```
#####8.2.1.4 结果（之前台到后台）

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-param1.png)

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-param2.png)

![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-param3.png)



####8.2.2通过实体类注入，实体类中提供getter.. setter（之前台到后台）

#####8.2.2.1User.java（之前台到后台）
```java
package com.learn.entity;

/**
 * Description: 用户实体类
 * Created by caojx on 16-12-30.
 */
public class User {

    private String userName;
    private String age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

```

#####8.2.2.2 DataController 添加实体类方式的参数传入（之前台到后台）

**方式三：通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中**

```java
/**
 * Description:springmvc中参数的传递,接收页面传递到Controller中的参数
 * 方式一：前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
 * 方式二：当前台页面的参数名与后台的方法参数不一致时，使用@RequestParam注解，绑定参数
 * 方式三：通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data")
public class DataController {

    /**
     * 方式一
     * Description:添加用户方法，直接返回试图
     * 前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
     * @return String
     */
    @RequestMapping("/addUser")
    public String addUser(String userName,String age,HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",userName);
        httpServletRequest.setAttribute("age",age);
        return "/jsp/userManager";
    }

     /**
      * 方式二
      * Description:添加用户的方法，
      * 前台页面和后台方法参数名不一致，使用@RequestParam注解绑定参数，这种方式相比第一种方式更为常用
      * required的默认值为true，表示必须的参数
      * @param name 用户名
      * @param age 年龄
      * @param httpServletRequest
      * @return
      */
      @RequestMapping("/addUser2")
      public String addUser2(@RequestParam(value = "userName",required = true) String name,
                                    @RequestParam(value = "age",required = true) int age,
                                    HttpServletRequest httpServletRequest){
          System.out.println("-------addUser 接收的参数--name:"+name+"--age:"+name);
          //将接受的参数返回到用户管理页面
          httpServletRequest.setAttribute("userName",name);
          httpServletRequest.setAttribute("age",age);
          return "/jsp/userManager";
      }
          
    /**
     * 方式三
     * Description:添加用户方法，直接返回试图
     * 通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
     * @return String
     */
    @RequestMapping("/addUser3")
    public String addUser3(User user, HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+user.getUserName()+"--age:"+user.getAge());
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",user.getUserName());
        httpServletRequest.setAttribute("age",user.getAge());
        return "/jsp/userManager";
    }

    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(){
        return "/jsp/addUser";
    }

}
```

#####8.2.2.3 addUser.jsp（之前台到后台）
修改action的路径为/data/addUser2
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>springmvc参数传递添加用户</title>
    <script type="text/javascript">
        function addUser() {
            var form = document.forms[0];
            form.action = "/data/addUser3";
            form.method = "get";
            form.submit();
        }
    </script>
</head>
<body>
<h1>添加用户</h1>
<form action="">
    姓名：<input type="text" name="userName"/>
    年龄：<input type="text" name="age"/>

    <input type="submit" value="添加" onclick="addUser()">
</form>
</body>
</html>
```

结果与之前的一致，这里就不再提供截图了。

###8.3传递json数据（之前台到后台）
json数据格式是web开发中一种常用数据传递格式，这里我们使用一个简单的案例来演示对json数据的传递


####8.3.1json.jsp页面（之前台到后台）
将参数提交到后台页面，然后接受后台返回的参数

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="/js/common/jquery-1.7.1.min.js"></script>
    <title>json的数据传递</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#add").click(function () {
                var userName = $("#userName").attr("value")
                var age = $("#age").attr("value");
                var user = {
                    "userName":userName,
                    "age":age
                };

                $.ajax({
                    url:"/data/addUserJson",
                    type:"post",
                    data:user,
                    success:function (data) {
                        alert("userName"+data.userName+"-----age:"+data.age);
                    }
                });
            });
        });
    </script>
</head>
<body>
    <h1>添加用户</h1>
    姓名：<input type="text" id="userName"/>
    年龄：<input type="text" id="age"/>
    <input type="button" id="add" value="添加">
</body>
</html>

```
####8.3.2DataController.java（之前台到后台）

addJson方法用户接受页面参数，和给页面返回数据

```java
package com.learn.annotaion;

import com.learn.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * Description:springmvc中参数的传递,接收页面传递到Controller中的参数
 * 方式一：前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
 * 方式二：当前台页面的参数名与后台的方法参数不一致时，使用@RequestParam注解，绑定参数
 * 方式三：通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data")
public class DataController {

    /**
     * 方式一
     * Description:添加用户方法，直接返回试图
     * 前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
     * @return String
     */
    @RequestMapping("/addUser")
    public String addUser(String userName,String age,HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",userName);
        httpServletRequest.setAttribute("age",age);
        return "/jsp/userManager";
    }

    /**
     * 方式二
     * Description:添加用户的方法，
     * 前台页面和后台方法参数名不一致，使用@RequestParam注解绑定参数，这种方式相比第一种方式更为常用
     * required的默认值为true，表示必须的参数
     * @param name 用户名
     * @param age 年龄
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/addUser2")
    public String addUser2(@RequestParam(value = "userName",required = true) String name,
                           @RequestParam(value = "age",required = true) int age,
                           HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--name:"+name+"--age:"+name);
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",name);
        httpServletRequest.setAttribute("age",age);
        return "/jsp/userManager";
    }

    /**
     * 方式三
     * Description:添加用户方法，直接返回试图
     * 通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
     * @return String
     */
    @RequestMapping("/addUser3")
    public String addUser3(User user, HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+user.getUserName()+"--age:"+user.getAge());
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",user.getUserName());
        httpServletRequest.setAttribute("age",user.getAge());
        return "/jsp/userManager";
    }


    /**
     * Description:添加用户方法，使用json方式
     * 通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
     * 前台页面使用json方式传递数据
     * @return String
     */
    @RequestMapping("/addUserJson")
    public String addJson(User user, HttpServletResponse httpServletResponse){
        System.out.println("-------addUserJson 接收的参数--userName:"+user.getUserName()+"--age:"+user.getAge());
        //将接受到参数返回给json页面,其实返回json数据常用的写法使用JSONObject和JSONArray或者是将阿从看json
        String result = "{\"userName\":\" "+ user.getUserName() +" \",\"age\":\" "+ user.getAge()+" \"}";
        httpServletResponse.setContentType("application/json");
        PrintWriter out = null;
        try{
            out = httpServletResponse.getWriter();
            out.print(result);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/jsp/json";
    }


    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(){
        //return "/jsp/addUser";
        return "/jsp/json";
    }

}

```

####8.3.4结果（之前台到后台）
![](/home/caojx/learn/notes/images/spring/springmvc/springmvc-json.png);

###8.4参数的几种传递方式(后台到前台的参数传递)
>后台到前台的参数传递

springmvc支持的返回方式有，ModelAndView,Model,ModelMap,Map,List,View,String,void


#####8.4.1DataController2.java(后台到前台的参数传递)

其中ModelAndView，Model,ModelMap，View这些基本的方式就不再介绍了，这里主要介绍一下返回Map，list，String（jsonString方式）。

```java
package com.learn.annotaion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:springmvc将Controller中返回的数据，交给页面
 * springmvc支持的返回方式有，ModelAndView,Model,ModelMap,Map,List,View,String,void
 * 其中ModelAndView，Model,ModelMap，View就不再介绍了，这里主要介绍一下返回Map，list，String
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data2")
public class DataController2 {

    /**
     * 方式一，返回Map
     * Description:将接受到的参数通过Map方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * @return Map
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String,Object> addUser(@RequestParam(value = "userName",required = true) String userName,
                                      @RequestParam(value = "age",required = true)int age){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
       Map<String,Object> map = new HashMap<String,Object>();
       map.put("userName",userName);
       map.put("age",age);
       return map;
    }

    /**
     * 方式二，返回List
     * Description:将接受到的参数通过list方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * @return List
     */
    @RequestMapping("/addUser2")
    @ResponseBody
    public List<User> addUser2(@RequestParam(value = "userName",required = true) String userName,
                         @RequestParam(value = "age",required = true)int age){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        users.add(user);
        return users;
    }

    /**
     * 方式三，使用jackson，返回jsonString
     * Description:将接受到的参数通过jsonString方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * 需要新增3个jar包支持
     * jackson-annotationsxxx.jar
     * jackson-corexxx.jar
     * jackson-databindxxx.jar
     * 
     * json字符串中有中文的时候，容易出现乱码问题，可以在@RequestMapping中添加produces = "application/json;charset=UTF-8"对编码进行处理，
     * 也可时在springmvc配置文件中使用拦截器的方式实现，我个人比较喜欢拦截器的方式，使用拦截器的方式，就不用每次都在@RequestMapping中写produces。
     * @return List
     */
    @RequestMapping(value = "/addUser3",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addUser3(@RequestParam(value = "userName",required = true) String userName,
                               @RequestParam(value = "age",required = true)int age) throws JsonProcessingException {
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        return jsonString;
    }

    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(){
        return "/jsp/addUser";
    }

}
```

在返回jsonString的时候出现了中文乱码，解决方法

1.在@RequestMapping中添加produces = "application/json;charset=UTF-8"，如下：
```java
@RequestMapping(value = "/addUser3",produces = "application/json;charset=UTF-8")
     @ResponseBody
     public String addUser3(@RequestParam(value = "userName",required = true) String userName,
                                @RequestParam(value = "age",required = true)int age) throws JsonProcessingException {
         System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
         //将接受的参数返回到用户管理页面
         User user = new User();
         user.setUserName(userName);
         user.setAge(age);
         ObjectMapper objectMapper = new ObjectMapper();
         String jsonString = objectMapper.writeValueAsString(user);
         return jsonString;
     }
```

这种方式需要在每个出现乱码的请求方法中添加produces = "application/json;charset=UTF-8

2.使用拦截器的方式实现,在开启注解的时候添加配置编码过滤，一般是设置StringHttpMessageConverter，推荐使用这种方式，
这样就不用每次在@RequestMapping中添加produces = "application/json;charset=UTF-8"了
```xml
 <mvc:annotation-driven>
        <!--下边是对返回的字符编码做过滤，通过拦截器的方式
        由于我在使用jackjson返回中文的时候，出现了乱码，为了防止返回中文字符的时候乱码，一般使用拦截器的方式去实现。
        -->
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <property name="supportedMediaTypes">
                    <list>
                        <value>text/plain;charset=UTF-8</value>
                        <value>text/html;charset=UTF-8</value>
                    </list>
                </property>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json;charset=UTF-8</value>
                        <value>application/x-www-form-urlencoded;charset=UTF-8</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>

    </mvc:annotation-driven>
```


##九.SpringMVC文件上传

###9.1springmvc-annotaion-servlet.xml

添加文件上传解析器

```xml
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="defaultEncoding" value="utf-8"></property><!--文件编码-->
        <property name="maxUploadSize" value="1048576000"></property><!--文件最大值-->
        <property name="maxInMemorySize" value="40960"></property><!--文件缓存大小-->
    </bean>
```

###9.2DataController2.java

这里使用两种方式上传文件

```java
package com.learn.annotaion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:springmvc将Controller中返回的数据，交给页面
 * springmvc支持的返回方式有，ModelAndView,Model,ModelMap,Map,List,View,String,void
 * 其中ModelAndView，Model,ModelMap，View就不再介绍了，这里主要介绍一下返回Map，list，String
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data2")
public class DataController2 {

    /**
     * 方式一，返回Map
     * Description:将接受到的参数通过Map方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * @return Map
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String,Object> addUser(@RequestParam(value = "userName",required = true) String userName,
                                      @RequestParam(value = "age",required = true)int age){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
       Map<String,Object> map = new HashMap<String,Object>();
       map.put("userName",userName);
       map.put("age",age);
       return map;
    }

    /**
     * 方式二，返回List
     * Description:将接受到的参数通过list方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * @return List
     */
    @RequestMapping("/addUser2")
    @ResponseBody
    public List<User> addUser2(@RequestParam(value = "userName",required = true) String userName,
                         @RequestParam(value = "age",required = true)int age){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        users.add(user);
        return users;
    }

    /**
     * 方式三，使用jackson，返回jsonString
     * Description:将接受到的参数通过jsonString方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * 需要新增3个jar包支持
     * jackson-annotationsxxx.jar
     * jackson-corexxx.jar
     * jackson-databindxxx.jar
     *
     * json字符串中有中文的时候，容易出现乱码问题，可以在@RequestMapping中添加produces = "application/json;charset=UTF-8"对编码进行处理，
     * 也可时在springmvc配置文件中使用拦截器的方式实现，我个人比较喜欢拦截器的方式，使用拦截器的方式，就不用每次都在@RequestMapping中写produces。
     * @return List
     */
    @RequestMapping(value = "/addUser3",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addUser3(@RequestParam(value = "userName",required = true) String userName,
                               @RequestParam(value = "age",required = true)int age) throws JsonProcessingException {
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        return jsonString;
    }

    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(){
        return "/jsp/addUser";
    }

}

```

###9.3upload.jsp

上传文件的时候，需要设置一下form表单的enctype：有下边3个值

multipart/form-data 在发送前编码所有字符（默认）

application/x-www-form-urlencoded 不对字符编码，在使用包含文件上传空间的表单时，必须使用该值

text/plain 空格转换为“+”加号，但不对特殊字符编码

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="/js/common/jquery-1.7.1.min.js"></script>
    <title>springmvc文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <h1>文件上传</h1>
    <%--
    上传文件的时候，需要设置一下form表单的enctype：有下边3个值
    multipart/form-data 在发送前编码所有字符（默认）
    application/x-www-form-urlencoded 不对字符编码，在使用包含文件上传空间的表单时，必须使用该值
    text/plain 空格转换为“+”加号，但不对特殊字符编码
    --%>
    <form  name="userForm" enctype="multipart/form-data" action="/file/upload2" method="post">
        选择文件：<input type="file" name="file"/>
        <input type="submit" value="上传">
    </form>
</body>
</html>
```

##十.springmvc和spring的集成

springmvc集成spring的时候，主要需要在web.xml中配置ContextLoaderListener
Listener:org.springframework.web.context.ContextLoaderListener 主要是用来加载spring所需药的配置文件

###10.1web.xml文件的修改
添加ContextLoaderListener Listener配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <!--
    web.xml的加载顺序
    1.context-param
    2.listener
    3.filter
    4.servlet
    -->


    <!--spring集成，设置spring配置文件所在的路径
        注意：这里不用添加上springmvc的配置文件，因为springmvc会处理自己的配置文件
    -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>
            classpath*:configs/applicationContext.xml
        </param-value>
    </context-param>
    
    <!--spring集成配置listener，负责加载spring的配置文件-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    
    <!--springmvc集成-->
    
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
                classpath*:configs/springmvc-servlet.xml,
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

###10.2 applicationContext.xml
新建applicationContext.xml对对spring进行配置，这里简单的配置一个bean
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置bean-->
    <bean id="springManager" class="com.learn.annotaion.SpringManager"></bean>
</beans>
```
###10.3 ISpring.java
定义springManager的服务接口

```java
public interface ISpring {
    public String get();
}
```
###10.4 SpringManager.java

用户实现ISpring接口，实现一些简单的操作

```java
public class SpringManager implements ISpring {

    @Override
    public String get() {
        System.out.println("---------------I am SpringManager-----");
        return "I am getMethod";
    }
}
```
###10.5 SpringController.java

```java
package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Description:对spring的集成,SpringController用户测试SpringMVC与spring的集成
 * Created by caojx on 17-1-8.
 */
@Controller
@RequestMapping("/spring")
public class SpringController {

    //使用注解获取SpringManager的实例,@Resource注解里边使用的值是application.xml配置的对应bean的id或name值
    @Resource(name="springManager")
    private ISpring springManager;

    /**
     * Description:用户返回配置成功的页面
     * @return
     */
    @RequestMapping("/get")
    public String get(){
        System.out.println(springManager.get()+"0000000");
        return "/jsp/success2";
    }
}
```

###10.6success2.jsp
spring配置成功后，跳转到该页面
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>spring集成success</title>
</head>
<body>
spring集成成功！
</body>
</html>
```
###10.7结果

![](/home/caojx/learn/notes/images/spring/springmvc/spring/springmvc-spring1.png)

###10.8spring与springmvc的上下文关系

spring可以理解成为一个对象的容器，对象都装载到spring容器中，而spring的上下文Context（也可以理解为容器）
获取到该上下文对象后就可以获取到spring中装载的bean了。

代码片段
```java
    @RequestMapping("/get")
    public String get(HttpServletRequest request){
        //获取spring的上线文对象
        WebApplicationContext webApplicationContext1 = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        //获取springmvc的上下文对象
        WebApplicationContext webApplicationContext2 = RequestContextUtils.getWebApplicationContext(request);

        //获取到上下文对象后，就可以获取上下问文中的bean
        ISpring springManager2 = (ISpring) webApplicationContext2.getBean("springManager");

        System.out.println(springManager.get());
        System.out.println(springManager2.get());
        return "/jsp/success2";
    }
```


##十一、spring与Hibernate4集成

这部分主要是扩展功能，不是springmvc中的部分，这里主要实现一个小案例，集成springmvc+spring+hibernate4进行
集成，实现一个小的增删改查功能。

###11.1 添加spring集成hibernate4所需要的jar
![](/home/caojx/learn/notes/images/spring/springmvc/hibernate/spring-hibernate-jar.png)

###11.2 Hibernate五大对象
在hibernate中，可以说是围绕着这一下五大对象展开的，集成在spring中后，就交给spring对这五大对象进行管理。

Configuration,相当于hibernate的上下文

SessionFactory，hibernate中的重量级对象，一般一个数据库只对应一个SessionFactory,主要用于管理Session

Session，轻量级对象，用于对数据库做增删改查。

Transaction，事物对象，用于管理事物。

Query和Criteria

集成hibernate的时候，Configuration,SessionFactory,Transaction都是交给spring进行管理的。
####11.3 spring集成hibernate4的配置文件spring-datasource.xml

新建的spring-datasource.xml文件中主要是对数据源，sessionFactory，事务进行配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--数据源配置-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <!--配置连接数据库中的参数-->
        <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"></property>
        <property name="url" value="jdbc:oracle:thin:@localhost:1521:XE"></property>
        <property name="username" value="myoracle"></property>
        <property name="password" value="myoracle"></property>
    </bean>

    <!--配置SessionFactory,用于管理创建Session的，这个也是Spring为我们提供好了-->
    <bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
        <property name="hibernateProperties"><!--配置管理hibernate的一些属性-->
            <props>
                <!--配置数据库方言，便于让hibernate将转成正确的sql，因为不同的数据库sql语句的设计上是存在差异的，
                就好比不同地方的人说不同的方言。而Hibernate呢，它会所有的“方言”，我们要做的，就是告诉它，我们需要它说哪种“方言”-->
                <prop key="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</prop>
                <!--
                create：
每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
create-drop ：
每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
update：
最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），以后加载hibernate时根据 model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
validate ：
每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。
                -->
                <prop key="hibernate.hbm2ddl.auto">create</prop>
                <prop key="hibernate.show_sql">true</prop><!--是否打印sql语句-->
                <prop key="hibernate.format_sql">true</prop><!--是否格式化sql语句-->
              <!--  <prop key="hibernate.current_session_context_class">thread</prop>-->
            </props>
        </property>
        <property name="configLocations"><!--加载hibernate的配置文件-->
            <list>
                <value>
                    classpath*:configs/hibernate.cfg.xml
                </value>
            </list>
        </property>
    </bean>

    <!--配置事物管理器-->
    <bean id="transactionManager" class="org.springframework.orm.hibernate4.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"></property><!--一般一个事物管理一个sessionFactory-->
    </bean>

    <!--配置代理事物-->
    <bean id="transactionBase" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean" lazy-init="true" abstract="true">
        <property name="transactionManager" ref="transactionManager"></property><!--事物管理器-->
        <property name="transactionAttributes"><!--配置事物的描述，在那些地方需要加事物-->
            <props>
                <prop key="add*">PROPAGATION_REQUIRED,-Exception</prop><!--支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。(就比如上边的场景，methoda假如有事务
                                                                           	则使用methoda的使用，假如methoda没有则新建一个事务),如果出现异常，事物就回滚-->
                <prop key="update*">PROPAGATION_REQUIRED,-Exception</prop>
                <prop key="insert*">PROPAGATION_REQUIRED,-Exception</prop>
                <prop key="modify*">PROPAGATION_REQUIRED,-Exception</prop>
                <prop key="get*">PROPAGATION_NEVER,-Exception</prop><!--查询以非事物的方式进行-->
            </props>
        </property>
    </bean>
</beans>
```

####11.4 hibernate.cfg.xml 文件

这个文件主要是配置试题类的映射文件所在位置。

```xml
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!--实体类映射,使用注解的方式的时候使用class-->
        <mapping class="com.learn.entity.User"></mapping>
    </session-factory>
</hibernate-configuration>
```
####11.5 User.java

```java
package com.learn.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Description: 用户实体类
 * Created by caojx on 16-12-30.
 */
@Entity//使用javax中的注解，扩展性更强
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid") //注解生成策略
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;
    @Column(length = 25)
    private String userName;
    @Column(length = 24)
    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

```

#### 11.6 DAO层IUserDao.java接口和UserDAO实现类
 
 IUserDao.java接口
 
 ```java
package com.learn.dao;

import com.learn.entity.User;

import java.util.List;

/**
 * Description:springmvc+spring+hibernate
 * Created by caojx on 17-1-17.
 */
public interface IUserDAO {

    /**
     *添加用户
     */
    public void addUser(User user);

    /**
     *查询用户
     */
    public List<User> getAllUser();

    /**
     *删除用户
     */
    public boolean delUser(String id);

    /**
     * 查询单个用户
     * */
    public User getUser(String id);

    /**
     * 更新用户信息
     * */
    public boolean updateUser(User user);
}

```

UserDao实现类

```java
package com.learn.dao.impl;

import com.learn.dao.IUserDAO;
import com.learn.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;


/**
 * Description:springmvc+spring+hibernate
 * Created by caojx on 17-1-17.
 */
public class UserDAO implements IUserDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 添加用户
     */
    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }


    /**
     * 查询用户
     */
    @Override
    public List<User> getAllUser() {
        String hql = "from User";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    /**
     * 删除用户
     */
    @Override
    public boolean delUser(String id) {
        String hql = "delete User u where u.id=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, id);
        return (query.executeUpdate() > 0);
    }

    /**
     * 查询单个用户信息
     */
    @Override
    public User getUser(String id) {
        String hql = "from User u where u.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, id);
        return (User) query.uniqueResult();
    }

    /**
     * 更新用户信息
     */
    @Override
    public boolean updateUser(User user) {
        String hql = "update User u set u.userName = ? , u.age = ? where u.id =?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, user.getUserName());
        query.setInteger(1, user.getAge());
        query.setString(2, user.getId());
        return (query.executeUpdate() > 0);
    }
}
```

####11.7 service层业务逻辑层IUserManager.java和实现类UserManger.java

IUserManager.java接口

```java
package com.learn.service;

import com.learn.entity.User;

import java.util.List;

/**
 * Description:
 * Created by caojx on 17-1-17.
 */
public interface IUserManager {

    /**
     * 添加用户
     * */
    public void addUser(User user);

    /**
     * 查询所有用户
     * */
    public List<User> getAllUser();

    /**
     * 删除用户
     * */
    public boolean delUser(String id);

    /**
     * 查询单个用户
     * */
    public User getUser(String id);

    /**
     * 更新用户信息
     * */
    public boolean updateUser(User user);
}
```

UserManager.java实现类

```java
package com.learn.service.impl;

import com.learn.dao.IUserDAO;
import com.learn.entity.User;
import com.learn.service.IUserManager;

import java.util.List;


/**
 * Description:
 * Created by caojx on 17-1-17.
 */
public class UserManager implements IUserManager {


    private IUserDAO userDAO;

    public IUserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * 添加用户
     * */
    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    /**
     * 获取用户
     * */
    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }

    /**
     * 删除用户
     * */
    @Override
    public boolean delUser(String id) {
        return userDAO.delUser(id);
    }

    /**
     *查询单个用户
     */
    @Override
    public User getUser(String id) {
        return userDAO.getUser(id);
    }

    /**
     * 更新用户信息
     * */
    @Override
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

}
```

####11.8 applicationContext.xml 对Dao和Service进行配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       
    <!--配置userDAO-->
    <bean id="userDAO" class="com.learn.dao.impl.UserDAO">
        <property name="sessionFactory" ref="sessionFactory"></property>
    </bean>

    <!--配置userManger-->
    <bean id="userManagerBase" class="com.learn.service.impl.UserManager">
        <property name="userDAO" ref="userDAO"></property>
    </bean>

    <!--配置代理userManager-->
    <bean id="userManager" parent="transactionBase">
        <property name="target" ref="userManagerBase"></property>
    </bean>
</beans>
```

####11.9 实现UserController3.java

```java
package com.learn.controller;

import com.learn.entity.User;
import com.learn.service.IUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Description:springmvc+spring+hibernate
 * Created by caojx on 17-1-17.
 */
@Controller
@RequestMapping("/user3")
public class UserController3 {

    @Resource(name = "userManager")
    private IUserManager userManager;

    /**
     * Description:调转到添加用户界面
     *
     * @return
     */
    @RequestMapping("/toAddUser3")
    public String toAddUser() {
        return "jsp/addUser3";
    }

    /**
     * 添加用户
     */
    @RequestMapping("/addUser3")
    public String addUser(User user) {
        userManager.addUser(user);
        //return "/jsp/success3";
        return "redirect:/user3/getAllUser";//springmvc url重定向，将url重定向到用户查询方法中，这种方式不太提倡，这里只是说明一下有这种用法。
    }

    /**
     * 查询用户
     */
    @RequestMapping("/getAllUser")
    public String getAllUser(HttpServletRequest httpServletRequest) {
        List<User> users = userManager.getAllUser();
        httpServletRequest.setAttribute("users", users);
        return "/jsp/userManager3";
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delUser3")
    public void delUser(String id, HttpServletResponse httpServletResponse) {
        String result = "{\"result\":\"error\"}";
        if (userManager.delUser(id)) {
            result = "{\"result\":\"success\"}";
        }
        PrintWriter out = null;
        httpServletResponse.setContentType("application/json"); //这里说明了返回数据的类型是json类型，返回其他类型前台会接受不到
        try {
            out = httpServletResponse.getWriter();
            out.write(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * 查询单个实体,并将数据返回到编辑页面
     */
    @RequestMapping("/getUser3")
    public String getUser(String id, HttpServletRequest httpServletRequest) {
        User user = userManager.getUser(id);
        httpServletRequest.setAttribute("user", user);
        return "/jsp/editUser";
    }

    /**
     * 更新用户信息
     */
    @RequestMapping("/updateUser3")
    public String updateUser(User user, HttpServletRequest httpServletRequest) {
        if (userManager.updateUser(user)) {//更新后将更新的数据返回给页面
            user = userManager.getUser(user.getId());
            httpServletRequest.setAttribute("user", user);
            return "/jsp/editUser";
        } else {
            return "/jsp/error";
        }
    }
}
```