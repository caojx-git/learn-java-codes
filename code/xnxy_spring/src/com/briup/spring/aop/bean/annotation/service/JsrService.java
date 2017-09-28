package com.briup.spring.aop.bean.annotation.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.briup.spring.aop.bean.annotation.dao.JsrDAO;

@Service
public class JsrService {
	
	@Resource
	private JsrDAO jsrDAO;
	
	@Resource  //作用与上边一样
	public void setJsrDAO(JsrDAO jsrDAO){
		this.jsrDAO = jsrDAO;
	}
	
	public void save(){
		jsrDAO.save();
	}
	
	@PostConstruct
	public void init(){
		System.out.println("jsr Service init");
	}
	
	@PreDestroy
	public void destory(){
		System.out.println("jsr Service destory");
	}
	
	/**
	 * 
	 * jsr Service init
		com.briup.spring.aop.bean.annotation.service.JsrService@7dc4cb
		jsr Service destory
	 * */
	
}
