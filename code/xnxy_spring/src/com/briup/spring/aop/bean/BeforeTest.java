package com.briup.spring.aop.bean;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class BeforeTest implements MethodBeforeAdvice{

	@Override
	public void before(Method method, Object[] obj, Object object)
			throws Throwable {
		System.out.println("version 1.0 author tom "+method.getName()+" is execute");
	}
	
}
