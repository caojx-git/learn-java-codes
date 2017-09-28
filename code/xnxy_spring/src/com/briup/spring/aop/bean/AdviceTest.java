package com.briup.spring.aop.bean;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AdviceTest implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		System.out.println("方法开始执行"+new Date());
		method.proceed();
		System.out.println("方法执行完毕"+new Date());
		return null;
	}
	

}
