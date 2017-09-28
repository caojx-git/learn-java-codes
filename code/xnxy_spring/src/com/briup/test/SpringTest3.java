package com.briup.test;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.briup.spring.aop.bean.AwareTest;
import com.briup.spring.aop.bean.ResourceTest;
import com.briup.spring.aop.bean.annotation.BeanAnnotation;
import com.briup.spring.aop.bean.annotation.BeanInvoker;
import com.briup.spring.aop.bean.annotation.aop.AspectBiz;
import com.briup.spring.aop.bean.annotation.aop.MyAspect;
import com.briup.spring.aop.bean.annotation.service.InjeactionService;
import com.briup.spring.ioc.UserService;
import com.briup.spring.ioc.bean.Car;
import com.briup.spring.ioc.bean.Coll;
import com.briup.spring.ioc.bean.Life;
import com.briup.spring.ioc.bean.Student;
import com.briup.spring.ioc.bean.Teacher;
import com.briup.spring.ioc.bean.User;


public class SpringTest3 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("BeforeClass 标注的方法 会最先先被执行");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AfterClass 标注的方法 会最后执行");
	}

	
	
	@Test
	public void aspectBefore(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/spring-aop-schema-advice.xml");
		AspectBiz aspectBiz = (AspectBiz) ac.getBean("aspectBiz");
		aspectBiz.biz();
	}
	
	@Test
	public void aspectAround(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/spring-aop-schema-advice.xml");
		AspectBiz aspectBiz = (AspectBiz) ac.getBean("aspectBiz");
		aspectBiz.init("init", 3);
	}
	
	
	

	
	
	
}
