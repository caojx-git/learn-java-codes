package com.briup.spring.aop.bean.annotation;

public class MyDriverManager {
	
	public MyDriverManager(String url, String userName, String password){
		System.out.println("url :"+url);
		System.out.println("userName :"+userName);
		System.out.println("password :"+password);
	}

}
