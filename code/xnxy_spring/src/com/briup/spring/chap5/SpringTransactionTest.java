package com.briup.spring.chap5;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.briup.spring.chap5.service.AccountService;

/**
 * 转账测试类
 * */
public class SpringTransactionTest {
	
	
	/**
	 * 1编程式事务管理
	 * */
	@Test
	public void test1() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap5/applicationContext.xml");
		AccountService accountService = ac.getBean("accountService",AccountService.class);
		accountService.transfer("张三", "李四", 200.0);
	}
	/**
	 * 2声明式事务管理-基于事务代理实现
	 * */
	@Test
	public void test2() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap5/applicationContext2.xml");
		AccountService accountService = ac.getBean("accountServiceProxy",AccountService.class);
		accountService.transfer("张三", "李四", 200.0);
	}
	/**
	 * 3声明式事务管理-基于AspectJ实现
	 * */
	@Test
	public void test3() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap5/applicationContext3.xml");
		AccountService accountService = ac.getBean("accountService2",AccountService.class);
		accountService.transfer("张三", "李四", 200.0);
	}
	/**
	 * 4声明式事务管理-基于注解的实现
	 * */
	@Test
	public void test4() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap5/applicationContext4.xml");
		AccountService accountService = ac.getBean("accountService2",AccountService.class);
		accountService.transfer("张三", "李四", 200.0);
	}
}
