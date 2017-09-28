package com.briup.spring.aop;

//委托类
public class UserDaoImpl implements UserDao{

	@Override
	public void saveUser(User user){
		System.out.println("保存用户");
	}
	
}
