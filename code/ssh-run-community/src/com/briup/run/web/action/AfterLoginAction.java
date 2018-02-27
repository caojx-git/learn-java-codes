package com.briup.run.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class AfterLoginAction extends ActionSupport{
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	@Override
	public String execute() throws Exception {
		//获取在线用户数量
		int num = iMemberService.findMemberinfoOnline();
		//获取前10名的用户
		List<Memberinfo> list = iMemberService.findMemberinfoByNum(10);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		System.out.println(num+"----"+list.size());
		session.setAttribute("onlineNum", num);
		session.setAttribute("members", list);
		return SUCCESS;
	}
}
