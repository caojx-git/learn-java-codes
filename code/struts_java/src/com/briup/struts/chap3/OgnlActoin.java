package com.briup.struts.chap3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.briup.bean.Student;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class OgnlActoin extends ActionSupport{
	private Student student;
	private List<Student> list;
	private Map<Integer, Student> map;
	
	public List<Student> getList() {
		return list;
	}

	public void setList(List<Student> list) {
		this.list = list;
	}

	public Map<Integer, Student> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Student> map) {
		this.map = map;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String execute() throws Exception {
		/*
		 * 提供get set 方法的变量会放到Value Stack Contents值栈区 ,变量名作为Property Name	值作为Property Value
		 * */
		student = new Student("tom", "123", 20, "male", "sh", "football");
		Student student2 = new Student("jack", "123", 21, "male", "sh", "football");
		/*
		 * ActionContext ac使用put方法会放入Stack Context
		 * */
		ActionContext ac = ActionContext.getContext();
		ac.put("student", student2);
		Map<String, Object> session = (Map<String, Object>)ac.get("session");
		session.put("name", "helloword");
		
		Student student3 = new Student("rose","123",20,"female","js","football");
		list = new ArrayList<Student>();
		list.add(student2);
		list.add(student3);
		map = new HashMap<Integer, Student>();
		map.put(1, student2);
		map.put(2, student2);
		return SUCCESS;
	}
}
