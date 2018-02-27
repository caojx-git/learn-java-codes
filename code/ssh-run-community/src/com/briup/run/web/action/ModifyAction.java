package com.briup.run.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class ModifyAction extends ActionSupport{
	private String newPasswd;
	private String oldPasswd;
	private Memberinfo memberinfo;
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}
	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}
	public String getNewPasswd() {
		return newPasswd;
	}
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	public String getOldPasswd() {
		return oldPasswd;
	}
	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}
	
	public Memberinfo getMemberinfo() {
		return memberinfo;
	}
	public void setMemberinfo(Memberinfo memberinfo) {
		this.memberinfo = memberinfo;
	}
	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		try {
			//取出session中的memberinfo
			Memberinfo memberinfo2 = (Memberinfo) session.getAttribute("memberinfo");
			memberinfo.setNickname(memberinfo2.getNickname());
			memberinfo.setPassword(newPasswd);
			iMemberService.saveOrUpDate(memberinfo, oldPasswd);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
