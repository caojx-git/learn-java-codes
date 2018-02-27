package com.briup.run.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/*
 * 验证自动登陆
 * */
public class CheckAutoLoginAction extends ActionSupport{
	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookies= request.getCookies();
		String username = null;
		String password = null;
		for(Cookie cookie : cookies){
			String name = cookie.getName();
			if(name.equals("username")){
				username = cookie.getValue();
			}
			if(name.equals("password")){
				password = cookie.getValue();
			}
		}
		//把用户名和密码放置到session方位中
		if(username!= null && password != null){
			request.setAttribute("uname", username);
			request.setAttribute("upassword", password);
		}else {
			return ERROR;
		}
		return SUCCESS;
	}
}
