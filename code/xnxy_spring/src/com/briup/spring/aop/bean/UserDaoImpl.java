package com.briup.spring.aop.bean;

//委托类
public class UserDaoImpl implements UserDao {
/*
	@Override
	public void saveUser() throws RuntimeException{
		System.out.println("保存用户");
		throw new RuntimeException();
	}*/

	

	@Override
	public void saveUser(){
		System.out.println("保存用户");
	}
	
	@Override
	public void deleteUser() {
		System.out.println("删除用户");
	}

	@Override
	public void updateUser() {
		System.out.println("更新用户");
	}

}
