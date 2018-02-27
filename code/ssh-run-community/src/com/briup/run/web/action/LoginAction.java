package com.briup.run.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Graderecord;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Pointaction;
import com.briup.run.common.bean.Pointrecord;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.common.util.MD5;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

/*
 * 登陆
 * */
public class LoginAction extends ActionSupport{
	private String username;
	private String password;
	private String authCode;
	private int autoLogin;
	private IMemberService iMemberService;

	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	public int getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(int autoLogin) {
		this.autoLogin = autoLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Override
	public String execute() throws Exception {
		//获取session里边的验证码和用户输入的验证码
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		String authString = (String) session.getAttribute("authCode");
		String passwd="";
		//获取request里边的用户名和密码
		String uname =	(String) request.getAttribute("uname");
		String upassword = (String) request.getAttribute("upassword");
		//如果session里边存在uname，upassword绕过验证码
		if(uname != null && upassword != null){
			authString = "";
			authCode=authString;
			passwd = upassword;
			username = uname;
		}else {
			passwd= MD5.getMD5Str(password);
		}
		if(!authString.equals(authCode)){
			request.setAttribute("message", "验证码错误");
			return ERROR;
		}else {
			try {
				Memberinfo memberinfo = iMemberService.login(username, passwd);
				request.setAttribute("message", "登陆成功");
				session.setAttribute("memberinfo", memberinfo);
				//判断是否有自动验证
				if(autoLogin==0){
					//创建cookie
					Cookie cookie1 = new Cookie("username", memberinfo.getNickname());
					Cookie cookie2 = new Cookie("password", passwd);
					//设置有效时间为1小时
					cookie1.setMaxAge(60*60);
					cookie2.setMaxAge(60*60);
					response.addCookie(cookie1);
					response.addCookie(cookie2);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		return SUCCESS;
	}
}
