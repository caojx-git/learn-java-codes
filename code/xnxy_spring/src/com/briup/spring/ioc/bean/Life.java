package com.briup.spring.ioc.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

public class Life implements BeanNameAware,BeanFactoryAware{
	private String name;
	
	public Life(){//一加载就会调到用
		System.out.println("调用无参构造器");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("调用setName方法");
		this.name = name;
	}
	
	public void myInit() {
		System.out.println("调用myInit方法");
	}
	
	public void myDestory(){
		System.out.println("调用myDestory方法");
	}

	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		System.out.println("调用setBeanFactory方法");
		
	}

	@Override
	public void setBeanName(String arg0) {
		System.out.println("调用setBeanName方法");
	}
	
	
	/**
	 * 调用无参构造器
调用setName方法
调用setBeanName方法
调用setBeanFactory方法
调用myInit方法
com.briup.spring.ioc.bean.Life@4f0b5b
调用myDestory方法
AfterClass 标注的方法 会最后执行

	 * 
	 * */
}
