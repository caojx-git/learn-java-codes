package com.briup.test;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.briup.spring.aop.bean.UserDao;
import com.briup.spring.chap3.Customer;
import com.briup.spring.chap3.CustomerDao;


public class SpringTest2 {

	@Test
	public void before(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/before.xml");	
		UserDao userDao = factory.getBean("proxy", UserDao.class);
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}
	

	@Test
	public void after(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/after.xml");	
		UserDao userDao = factory.getBean("proxy", UserDao.class);
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}

	@Test
	public void throwTest(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/throw.xml");	
		UserDao userDao = factory.getBean("proxy", UserDao.class);
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}
	
	@Test
	public void pointCutTest(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/pintcut.xml");	
		UserDao userDao = factory.getBean("proxy", UserDao.class);
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}
	
	
	@Test
	public void advice(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/advice.xml");	
		UserDao userDao = factory.getBean("proxy", UserDao.class);
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}
	@Test
	public void advisor(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/advisor.xml");	
		UserDao userDao = factory.getBean("proxy", UserDao.class);
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}
	@Test
	public void autoAdvisor(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/autoAdvisor.xml");	
		UserDao userDao = factory.getBean("target", UserDao.class); //自动代理只能通过为拖累的id值来拿
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}
	@Test
	public void autoProxyByName(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/autoProxyByName.xml");	
		UserDao userDao = factory.getBean("target", UserDao.class);//自动代理只能通过为拖累的id值来拿
		userDao.saveUser();
		userDao.deleteUser();
		userDao.updateUser();
	}
	@Test
	public void jdbc_connection(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap3/jdbc.xml");	
		CustomerDao customerDao = (CustomerDao) factory.getBean("dao");
		Customer customer = new Customer();
		customer.setId(3);
		customer.setAge(20);
		customer.setName("tom");
		customerDao.saveCustomer(customer);
	}
	
	@Test
	public void hibernate_connection(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap3/hibernate.xml");	
		CustomerDao customerDao = (CustomerDao) factory.getBean("dao");
		Customer customer = new Customer();
		//customer.setId(3);
		customer.setAge(20);
		customer.setName("tom");
		customerDao.saveCustomer(customer);
	}
	@Test
	public void hibernate_connection_proxy(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap3/hibernate.xml");	
		CustomerDao customerDao = (CustomerDao) factory.getBean("proxy");
		Customer customer = new Customer();
		//customer.setId(3);
		customer.setAge(20);
		customer.setName("tom");
		customerDao.saveCustomer(customer);
	}
}
