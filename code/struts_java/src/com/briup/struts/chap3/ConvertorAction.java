package com.briup.struts.chap3;

import com.briup.bean.Student;
import com.opensymphony.xwork2.ActionSupport;

public class ConvertorAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Student student;
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
