package com.briup.struts.chap2;

import com.opensymphony.xwork2.ActionSupport;

public class RedirectAction extends ActionSupport{
	@Override
	public String execute() throws Exception {
		System.out.println("this is redirect");
		return SUCCESS;
	}
}
