package com.briup.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//代理类 ---动态代理实现InvocationHandler 不知道代理谁--即不知道委托类是谁
public class MyProxy implements InvocationHandler{

	private Object target;//由于不知道委托类是谁，所以声明Object类型
	
	public MyProxy() {}

	public MyProxy(Object target) {
		this.target = target;
	}
	public void before(){
		System.out.println("你好");
	}
	//代理类调用的方法，该方法添加了自己的逻辑
	@Override
	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {
		before();
		//使用反射获取真正 要添加逻辑的方法
		//invoke中第一个参数谁执行 委托类 target
		//第二个参数表示方法的参数
		method.invoke(target, args);
		return null;
	}

}
