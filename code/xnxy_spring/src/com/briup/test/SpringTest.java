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
import com.briup.spring.aop.bean.annotation.service.InjeactionService;
import com.briup.spring.ioc.UserService;
import com.briup.spring.ioc.bean.Car;
import com.briup.spring.ioc.bean.Coll;
import com.briup.spring.ioc.bean.Life;
import com.briup.spring.ioc.bean.Student;
import com.briup.spring.ioc.bean.Teacher;
import com.briup.spring.ioc.bean.User;


public class SpringTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("BeforeClass 标注的方法 会最先先被执行");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AfterClass 标注的方法 会最后执行");
	}

	@Test
	public void test() {
		System.out.println("test");
		//路经比较特殊
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap1/ioc.xml");
		UserService service = (UserService) factory.getBean("service");
		service.getUserDao().save();
	}
	@Test
	public void test2() {
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap1/set.xml");
		//User user = (User) factory.getBean("user");
		//User user = (User) factory.getBean("user",User.class);
		//User user = (User) factory.getBean(User.class); //只有唯一的bean的时候才使用这种方式
		//System.out.println(user);
		System.out.println(factory.getType("user")); //获取user实例的类型
		User user = (User) factory.getBean("user"); 
		User user2 = (User) factory.getBean("user");
		System.out.println(user == user2);//true -- 单例  --这是可以控制的在配置文件中 bean scope="prototype"-->会变成原型模式 这时结果会是false
		System.out.println(factory.isPrototype("user"));//是否为原型     false
		System.out.println(factory.isSingleton("user"));//是否为单例     true
		
		System.out.println(factory.isTypeMatch("user", User.class));//判断 user实例是否为这种类型 true
		
		String[] str = factory.getAliases("user"); //获取别名  
		for(int i=0;i<str.length;i++){
			System.out.println(str[i]);
		}
	}
	
	/*
	 * 配置构造器参数 constructor-arg
	 * */
	@Test
	public void test3() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap1/constructor.xml");
		Teacher teacher = (Teacher) ac.getBean("teacher");
		System.out.println(teacher);//Teacher [id=1, name=tom, gender=male]
	}
	
	/*
	 * 遍历集合
	 * */
	@Test
	public void test4() {
	
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap1/coll.xml");
		ClassPathResource resource = new ClassPathResource("classpath:coll.xml");
		XmlBeanFactory factory = new XmlBeanFactory(resource);
		Coll coll = factory.getBean("coll",Coll.class);
		//coll.printList();
		coll.printSet();
		//coll.printMap();
		//coll.printProperties();
	}
	@Test
	public void test5(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap1/autowire.xml");
		Student student = ac.getBean("student",Student.class);
		System.out.println(student);
	}
	@Test
	public void test6(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap1/abstract.xml");
		//Car car = (Car) factory.getBean("car"); abstract=true不能直接获取实例
		Car car = factory.getBean("car1",Car.class);
		System.out.println(car);
	}
	@Test
	public void test7(){
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap1/connection.xml");
		Connection connection = (Connection) factory.getBean("conn");
		System.out.println(connection);
	}
	
	@Test
	public void life(){//springBean的生命周期	
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap2/life.xml");
		Life life = ac.getBean("life",Life.class);
		System.out.println(life);
		ac.destroy();
	}
	
	@Test
	public void AwareTest(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap1/aware.xml");
		AwareTest awareTest = ac.getBean("applicationAawareTest",AwareTest.class);
		System.out.println(awareTest);
	}
	
	@Test
	public void ResourceTest(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap1/resources.xml");
		ResourceTest resourceTest = ac.getBean("resourcetest",ResourceTest.class);
		try {
			resourceTest.resource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void convertor(){//自定义转换器
		BeanFactory factory = new ClassPathXmlApplicationContext("com/briup/spring/chap2/edit.xml");
		Student student = factory.getBean("student",Student.class);
		System.out.println(student);
	}
	
	
	
	@Test
	public void testAnnotation(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/annotation.xml");
		//@Component没有value值，默认使用类名首字母小写作为bean的id
		BeanAnnotation beanAnnotation1  = ac.getBean("beanAnnotation",BeanAnnotation.class);
		BeanAnnotation beanAnnotation2  = ac.getBean("beanAnnotation",BeanAnnotation.class);
		System.out.println(beanAnnotation1);
		System.out.println(beanAnnotation2);
		
		//com.briup.spring.aop.bean.annotation.BeanAnnotation@1598d5f
		//com.briup.spring.aop.bean.annotation.BeanAnnotation@505fd8
	}
	
	@Test
	public void testAutowired(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/annotation.xml");
		InjeactionService injeactionService = (InjeactionService) ac.getBean("injeactionServiceImpl");
		injeactionService.save("this is autowired");
	}
	
	@Test
	public void testAutowired2(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/annotation.xml");
		BeanInvoker beanInvoker = (BeanInvoker) ac.getBean("beanInvoker");
		beanInvoker.print();
	}
	
	@Test
	public void testBean(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/annotation.xml");
		System.out.println(ac.getBean("beanImplOne"));
	}
	
	@Test
	public void testBean2(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/annotation.xml");
		System.out.println(ac.getBean("myDriverManager"));
		System.out.println(ac.getBean("myDriverManager"));
	}
	
	@Test
	public void testJsr(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/briup/spring/chap4/annotation.xml");
		System.out.println(ac.getBean("jsrService"));
		ac.destroy();
	}
	
	
	
}
