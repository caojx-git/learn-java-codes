package com.briup.spring.aop.bean;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class ThrowsAdiviceTest implements ThrowsAdvice{


	public void afterThrowing(Method method,Object[] args,Object target,Exception ex)throws Throwable{//Throwable subclass
		System.out.println("afterThrowing 2 ...."+method.getName()+"   "+ target.getClass().getName());
	}

}
