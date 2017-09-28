package com.briup.spring.aop.bean.annotation.dao;

import org.springframework.stereotype.Repository;

@Repository
public class JsrDAO {
	
	public void save(){
		System.out.println("JsrDao invoker");
	}

}
