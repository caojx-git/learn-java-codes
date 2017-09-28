package com.briup.spring.aop.bean.annotation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.spring.aop.bean.annotation.dao.InjeactionDAO;
import com.briup.spring.aop.bean.annotation.service.InjeactionService;

//业务逻辑层的注解
@Service("injeactionServiceImpl")
public class InjeactionServiceImpl implements InjeactionService {
	
	//使用autowired 注入成员属性的值，可以省略getxx setxx方法
	@Autowired
	private InjeactionDAO injeactionDAO;
	
	
	//setter注入同上边作用一样
	@Autowired
	public void setInjeactionDAO(InjeactionDAO injeactionDAO) {
		this.injeactionDAO = injeactionDAO;
	}
	
	//构造注入同属性注入一样
	@Autowired
	public InjeactionServiceImpl(InjeactionDAO injeactionDAO) {
		this.injeactionDAO = injeactionDAO;
	}

	@Override
	public void save(String arg) {
		//模拟业务操作
		System.out.println("Service接收参数"+arg);
		injeactionDAO.save(arg);

	}

}
