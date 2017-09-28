package com.briup.spring.aop.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AwareTest implements ApplicationContextAware,BeanNameAware{

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println(applicationContext.getBean(AwareTest.class));
	}

	@Override
	public void setBeanName(String beanName) {
		System.out.println(beanName);
	}

}
