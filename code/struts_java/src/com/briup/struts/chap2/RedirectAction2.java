package com.briup.struts.chap2;

import com.opensymphony.xwork2.ActionSupport;

public class RedirectAction2  extends ActionSupport{
	@Override
	public String execute() throws Exception {
		System.out.println("this is redirectAction2");
		return SUCCESS;
	}
}
