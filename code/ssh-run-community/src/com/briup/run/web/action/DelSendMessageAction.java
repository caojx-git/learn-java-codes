package com.briup.run.web.action;

import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMessengerService;
import com.opensymphony.xwork2.ActionSupport;

public class DelSendMessageAction extends ActionSupport{
	private Long[] ID;
	private IMessengerService iMessengerService;
	

	public IMessengerService getiMessengerService() {
		return iMessengerService;
	}

	public void setiMessengerService(IMessengerService iMessengerService) {
		this.iMessengerService = iMessengerService;
	}

	
	public Long[] getID() {
		return ID;
	}

	public void setID(Long[] iD) {
		ID = iD;
	}

	@Override
	public String execute() throws Exception {
		try {
			for(Long id:ID){
				iMessengerService.delSendMessage(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
