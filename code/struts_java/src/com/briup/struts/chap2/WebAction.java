package com.briup.struts.chap2;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.briup.bean.Student;
import com.opensymphony.xwork2.ActionSupport;

public class WebAction extends ActionSupport implements RequestAware,SessionAware{
	
	//方法二实现RequestAware,SessionAware 这两个接口，我们只要提供set，get方法struts会自动帮我们将reques，session注入到值栈区
	private Map<String, Object> request;
	private Map<String, Object> session;
	
	public Map<String, Object> getRequest() {
		return request;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
		
	}
	@Override
	public String execute() throws Exception {
		System.out.println("this is webAction");
		System.out.println(session.get("student2"));
		return SUCCESS;
	}
	
	
/*
 * 方法一手动存入值栈去
	private Map<String, Object> request;
	private Map<String, Object> session;
	
	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public String execute() throws Exception {
		//放入值栈区
		request = new HashMap<String, Object>();
		session = new HashMap<String, Object>();
		Student student = new Student();
		student.setName("jack");
		request.put("name", "tom");
		session.put("name", "tom");
		request.put("student", student);
		session.put("student", student);
		return SUCCESS;
	}
	
*/
}
