package com.briup.run.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Messagerecord;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMessengerService;
import com.opensymphony.xwork2.ActionSupport;

public class ListReciverMessageAction extends ActionSupport{
	private IMessengerService iMessengerService;
	

	public IMessengerService getiMessengerService() {
		return iMessengerService;
	}


	public void setiMessengerService(IMessengerService iMessengerService) {
		this.iMessengerService = iMessengerService;
	}


	@Override
	public String execute() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			//将发送后消息放到request范围
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			System.out.println("-----reciver"+memberinfo.getNickname());
			List<Messagerecord> list = iMessengerService.listRecieveMessage(memberinfo.getNickname());
			request.setAttribute("messagerecord", list);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
