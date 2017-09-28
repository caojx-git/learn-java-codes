package com.briup.spring.aop.bean.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreConfig {

	
	
	//initMethod，和desotryMethod是BeanImplOne里边的方法
	@Bean(name="beanImplOne",initMethod="init",destroyMethod="destory")  //如果没有指定Bean的name属性，则bean的name默认为方法的名称
	public BeanInterface BeanImplOne(){
		return new BeanImplOne();
	}
}
