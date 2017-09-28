package com.briup.spring.aop.bean;



import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;


public class AfterTest  implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		System.out.println(arg1.getName()+" is over!");
	}
}
