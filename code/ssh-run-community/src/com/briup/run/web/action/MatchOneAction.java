package com.briup.run.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Bean;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.briup.run.service.IMessengerService;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.swing.internal.plaf.metal.resources.metal;

public class MatchOneAction extends ActionSupport{
	private IMessengerService iMessengerService;
	private IMemberService iMemberService;
	
	public IMessengerService getiMessengerService() {
		return iMessengerService;
	}

	public void setiMessengerService(IMessengerService iMessengerService) {
		this.iMessengerService = iMessengerService;
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
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			List<Memberinfo> list = iMessengerService.findOneMemberinfo();
			Memberinfo memberinfo2 = (Memberinfo) session.getAttribute("memberinfo");
			while(list.get(0).getNickname().equals(memberinfo2.getNickname())){//如果是自己重新查询
				list = iMessengerService.findOneMemberinfo();
			}
			//如果是自己的好友重新查询
			List<Memberinfo> list2 = iMemberService.listFriend(memberinfo2.getNickname());
			int num = iMessengerService.findMemberinfoNum();
			//判断是否所有的用户已经是你的好友
			if (list2.size()==num-1) {
				System.out.println("没有更多的好友可查询");
				return ERROR;
			}
			for(int i = 0 ; i < list2.size(); i++){
				if(list.get(0).equals(list2.get(i))){
					list = iMessengerService.findOneMemberinfo();
					i = -1;
				}
			}
			request.setAttribute("members", list);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
