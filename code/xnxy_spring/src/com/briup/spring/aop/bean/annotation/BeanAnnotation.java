package com.briup.spring.aop.bean.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


//由于不知道其作用于DAO或Service所以使用通用注解
//@Component -->默认使用类名小写作为bean的name
@Scope("prototype") //括号中为Scope的范围,这里设置为原型模式
@Component("beanAnnotation")
public class BeanAnnotation {
	
	public void say(String arg){
		System.out.println("BeanAnnotation: "+arg);
	}

}
