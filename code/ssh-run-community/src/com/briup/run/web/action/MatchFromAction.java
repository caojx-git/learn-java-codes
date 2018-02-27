package com.briup.run.web.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Friendrecord;
import com.briup.run.common.bean.Match;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.service.IMemberService;
import com.briup.run.service.IMessengerService;
import com.opensymphony.xwork2.ActionSupport;

public class MatchFromAction extends ActionSupport{
	private String age;
	private String gender;
	private String provinceCity;
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvinceCity() {
		return provinceCity;
	}
	public void setProvinceCity(String provinceCity) {
		this.provinceCity = provinceCity;
	}
	
	@Override
	public String execute() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			age = Match.getAgeRangeById(age);
			List<Memberinfo> list = iMessengerService.findFriends(age, gender, provinceCity);

			//去除自己
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			Iterator<Memberinfo> iterator = list.listIterator();
			while(iterator.hasNext()){
				Memberinfo memberinfo2 = iterator.next();
				if(memberinfo2.getNickname().equals(memberinfo.getNickname())){
					iterator.remove();
				}
			}
			//去除自己的好友 
	
			Iterator<Memberinfo> iterator2 = list.listIterator();
			while(iterator2.hasNext()){
				Memberinfo memberinfo2 = iterator2.next();
				Friendrecord friendrecord = iMemberService.findFriend(memberinfo.getNickname(), memberinfo2.getNickname());
				if(friendrecord!= null&&memberinfo2.getNickname().equals(friendrecord.getFriendname())){
					iterator2.remove();
				}
			}
			//去除黑名单朋友
			request.setAttribute("members", list);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
