package com.briup.run.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class ListFriendAction extends ActionSupport{
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
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			List<Memberinfo> list = iMemberService.listFriend(memberinfo.getNickname());
			System.out.println(list.size());
			request.setAttribute("myFriends", list);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}	
