package com.briup.run.web.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class AddBlackListAction extends ActionSupport{
	private String blackName;
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	public String getBlackName() {
		return blackName;
	}

	public void setBlackName(String blackName) {
		this.blackName = blackName;
	}

	@Override
	public String execute() throws Exception {
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			System.out.println(blackName);
			iMemberService.moveToBlack(memberinfo.getNickname(), blackName);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
