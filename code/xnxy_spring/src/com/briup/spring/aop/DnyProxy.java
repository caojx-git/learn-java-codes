package com.briup.spring.aop;

import java.lang.reflect.Proxy;


public class DnyProxy {
	public static void main(String[] args) {
		User user = new User();
		user.setName("tom");
		//委托类
		UserDao userDao = new UserDaoImpl();
		//代理类
		MyProxy proxy = new MyProxy(userDao);
		//第一个参数 委托类的加载器classLoader镜像获取加载器
		//第二个参数 获取委托类和代理类实现的共同的接口
		//第三个参数 代理的实例对象
		userDao = (UserDao) Proxy.newProxyInstance(userDao.getClass().getClassLoader(), 
				userDao.getClass().getInterfaces(), proxy);
		userDao.saveUser(user); //userDao调用的是代理类中的invoke();
	}
}
