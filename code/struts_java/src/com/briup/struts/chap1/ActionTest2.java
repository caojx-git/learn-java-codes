package com.briup.struts.chap1;

import com.opensymphony.xwork2.Action;

public class ActionTest2 implements Action{
	int count;  
	@Override
	public String execute() throws Exception {
		count++;
		System.out.println(count); //线程安全的
		return SUCCESS;
	}

}
