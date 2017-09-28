package com.briup.spring.aop.bean.annotation.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/*
 * 声明一个切面类
 * */
public class MyAspect {
	
	public void before(){
		System.out.println("aspect before");
	}

	public void afterReturning(){
		System.out.println("aspect afterReturning");
	}
	
	public void afteThrowing(){
		System.out.println("aspect afteThrowing");
	}
	
	public void after(){
		System.out.println("aspect after(finally)");
	}
	
	public void around(ProceedingJoinPoint joinPoint){
		Object object = null;
		try{
			System.out.println("aspect around 1"); //方法执行前
			object = joinPoint.proceed();  //代表业务方法的执行
			System.out.println("aspect around 2"); //方法执行后
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
	
	//AOP中参数的传递
	public void aroundInit(ProceedingJoinPoint joinPoint,String bizName,int times){
		System.out.println(bizName+"--"+times);
		Object object = null;
		try{
			System.out.println("aspect around 1"); //方法执行前
			object = joinPoint.proceed();  //代表业务方法的执行
			System.out.println("aspect around 1"); //方法执行后
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
}
