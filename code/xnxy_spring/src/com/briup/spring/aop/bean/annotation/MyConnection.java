package com.briup.spring.aop.bean.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

@Configuration
@ImportResource("classpath:com/briup/spring/chap4/config.xml")  //指定配置文件的路径
public class MyConnection {
	
	
	@Value("${jdbc.url}")  //基本类型的变量使用@Value注解(括号里边是注入的值)  ，这是使用${是读取配db.properties中的值}
	private String url;
	
	@Value("${jdbc.username}")  //默认取的是当前操作系统用户的名称，可以在db.properties定义username的时候使用jdbc.username
	private String userName;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Bean(name="myDriverManager")
	@Scope("singleton")  //@Bean可以与@Scope组合使用
	public MyDriverManager MyDriverManager(){
		return new MyDriverManager(url,userName,password);
	}

}
