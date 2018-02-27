package com.briup.struts.chap1;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.briup.bean.Student;
import com.opensymphony.xwork2.ActionSupport;

public class ActionTest3 extends ActionSupport {

	@Override
	public String execute() throws Exception {
		//ServletActionContext方法可以用来获取servlet相关对象
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String password = request.getParameter("pwd");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String province = request.getParameter("province");
		String[] str = request.getParameterValues("hobby");
		System.err.println(str);
		String hobbys = str[0];
		for(int i = 1; i <str.length; i++) {
			hobbys += ","+str[i];
		}
		System.out.println(hobbys);
		Student student = new Student(name, password, age, gender, province, hobbys);
		request.setAttribute("student", student);
		return SUCCESS;
	}

}
