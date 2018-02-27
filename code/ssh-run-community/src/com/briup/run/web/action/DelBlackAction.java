package com.briup.run.web.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class DelBlackAction extends ActionSupport{
	private String[] nickName;
	private IMemberService iMemberService;
	public String[] getNickName() {
		return nickName;
	}
	public void setNickName(String[] nickName) {
		this.nickName = nickName;
	}
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
			for(String name:nickName){
				iMemberService.deleleBlack(memberinfo.getNickname(), name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
}
