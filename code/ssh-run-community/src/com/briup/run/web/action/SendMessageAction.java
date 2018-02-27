package com.briup.run.web.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Messagerecord;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMessengerService;
import com.opensymphony.xwork2.ActionSupport;

public class SendMessageAction extends ActionSupport{
	private Messagerecord messagerecord;
	private IMessengerService iMessengerService;
	public IMessengerService getiMessengerService() {
		return iMessengerService;
	}

	public void setiMessengerService(IMessengerService iMessengerService) {
		this.iMessengerService = iMessengerService;
	}

	public Messagerecord getMessagerecord() {
		return messagerecord;
	}

	public void setMessagerecord(Messagerecord messagerecord) {
		this.messagerecord = messagerecord;
	}

	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			HttpSession session = request.getSession();
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			if(messagerecord.getReceiver().equals(memberinfo.getNickname())){
				throw new Exception("收件人不能是自己");
			}
			messagerecord.setSender(memberinfo.getNickname());
			Date date = new Date();
			messagerecord.setSenddate(date);
			iMessengerService.saveMessage(messagerecord);
			//将发送后消息放到request范围
			List<Messagerecord> list = iMessengerService.listSendMessage(memberinfo.getNickname());
			request.setAttribute("messagerecord", list);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
}
