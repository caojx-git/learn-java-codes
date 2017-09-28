package com.briup.spring.aop.bean.annotation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class BeanImplOne implements BeanInterface {
	
	public void init(){
		System.out.println("This is init");
	}

	public void destory(){
		System.out.println("This is destory");
	}
}
