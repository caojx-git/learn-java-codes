/*package com.briup.spring.ioc;

public class Test {
	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext("src/com/briup/spring/ioc/beans.xml");
		UserDao dao = (UserDao) factory.getBean("dao");
		UserService service = (UserService) factory.getBean("service");
		System.out.println(service.getUserDao());
	}
}
*/