package com.briup.run.web.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class AddFriendAction extends ActionSupport{
	private String friendname;
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}
	
	@Override
	public String execute() throws Exception {
		try {
			System.out.println("hello -----------------"+friendname);
			HttpSession session = ServletActionContext.getRequest().getSession();
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			System.out.println(memberinfo.getNickname()+"------"+friendname);
			iMemberService.moveToFriend(memberinfo.getNickname(), friendname);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
