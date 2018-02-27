package com.briup.run.web.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;
/*
 * 注销-退出
 * */
public class LogOutAction extends ActionSupport{
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	@Override
	public String execute() throws Exception {
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			iMemberService.logout(memberinfo.getNickname());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
