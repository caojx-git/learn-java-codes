package com.briup.run.web.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private IMemberService iMemberService;
	private Memberinfo memberinfo;
	private String authCode;

	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Memberinfo getMemberinfo() {
		return memberinfo;
	}

	public void setMemberinfo(Memberinfo memberinfo) {
		this.memberinfo = memberinfo;
	}

	@Override
	public String execute() throws Exception {
		HttpServletRequest request = null;
		try {
			// 判断验证码是否正确
			request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String authString = (String) session.getAttribute("authCode");
			if (authCode.equals(authString)) {
				iMemberService.registerMemberinfo(memberinfo);
				request.setAttribute("message", "注册成功");
			} else { // 验证码错误
				request.setAttribute("message", "验证码错误");
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
}
