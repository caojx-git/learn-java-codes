package com.briup.run.web.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class IsCreateSpaceAction extends ActionSupport {
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
		Boolean isMemberspace = iMemberService.isMemberspace(memberinfo.getId());
		if (!isMemberspace) {
			return ERROR;
		}
		return SUCCESS;
	}
}
