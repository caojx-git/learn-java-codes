package com.briup.struts.chap2;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.briup.bean.Student;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.Key;
import com.opensymphony.xwork2.util.ValueStack;
/*
 * 
 * 栈区操作
 * 
 * */
public class VsAndAc extends ActionSupport{
	private String  name;  //方法一提供get set方法，就可以把成员变量存放到值栈区
	private Student student;
	
	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("this is vsAndac");
		name="tom";
		student = new Student();
		student.setName("jack");
		String name = "rose";
		
		
		Student student2 = new Student();
		student2.setName(name);
		ActionContext ac = ActionContext.getContext();
		ValueStack vs = ac.getValueStack();
		vs.push(student2); //将student2放入值栈区
		//vs.pop();
		//------------------两个对象----------
		/*
		 * <c:property value="#name"/>下列方式通过#***取值
		 * */
		ac.put("name", name);  
		ac.put("student2", student2);
		System.out.println(ac.get("name"));
		/*
		 * 这种方式取出来的request对象原型是map，如果需要取出HttpServletRequest request则使用ServletActionContext.getRequest();
		 * */
		Map<String, Object> request = (Map<String, Object>) ac.get("request");
		System.out.println(request+"------------");
		HttpServletResponse reponse	= ServletActionContext.getResponse();
		Map<String, Object> session =  ac.getSession();
		session.put("student2", student2);
		
		Set<String> keys = request.keySet();
		for(String k: keys){
			Object value = request.get(k);
			System.out.println("key:"+k+"value:"+value);
		}
		request.put("name", name);
		
		return SUCCESS;
	}
}
