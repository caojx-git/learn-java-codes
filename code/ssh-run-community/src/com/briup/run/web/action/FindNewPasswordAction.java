package com.briup.run.web.action;

import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;

public class FindNewPasswordAction extends ActionSupport{
	private String userName;
	private String passwdQuestion;
	private String passwdAnswer;
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswdQuestion() {
		return passwdQuestion;
	}

	public void setPasswdQuestion(String passwdQuestion) {
		this.passwdQuestion = passwdQuestion;
	}

	public String getPasswdAnswer() {
		return passwdAnswer;
	}

	public void setPasswdAnswer(String passwdAnswer) {
		this.passwdAnswer = passwdAnswer;
	}

	@Override
	public String execute() throws Exception {
		String newPassword = null;
		try {
			newPassword = iMemberService.getBackPasswd(userName, passwdQuestion, passwdAnswer);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
