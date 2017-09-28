package com.briup.spring.aop.bean.annotation.dao.impl;

import org.springframework.stereotype.Repository;

import com.briup.spring.aop.bean.annotation.dao.InjeactionDAO;


//dao持久层注解
@Repository("InjeactionDAO")
public class InjecationDAOImpl implements InjeactionDAO {

	@Override
	public void save(String arg) {
		//模拟数据库保存数据
		System.out.println("保存数据："+arg);
		
	}

}
