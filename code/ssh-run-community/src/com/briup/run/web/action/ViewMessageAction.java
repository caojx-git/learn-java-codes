package com.briup.run.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Messagerecord;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMessengerService;
import com.opensymphony.xwork2.ActionSupport;

public class ViewMessageAction extends ActionSupport{
	private String ID;
	private IMessengerService iMessengerService;
	
	public IMessengerService getiMessengerService() {
		return iMessengerService;
	}

	public void setiMessengerService(IMessengerService iMessengerService) {
		this.iMessengerService = iMessengerService;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public String execute() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			Messagerecord findMessage = iMessengerService.findMessage(Long.parseLong(ID));
			request.setAttribute("message", findMessage);
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			if(!(findMessage.getSender().equals(memberinfo.getNickname()))){//自己查看发件箱中的邮件不用设置为已读
				findMessage.setStatus(1L);
			}
			iMessengerService.saveMessage(findMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
