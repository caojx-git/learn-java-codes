package com.briup.struts.chap3;

import com.opensymphony.xwork2.ActionSupport;

public class FormAction extends ActionSupport{
	private String name;
	private String password;
	private String gender;
	private String hobby;
	private String city;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("name="+name+"password="+password+"gender="+gender+"city="+city+"hobby="+hobby);
		return SUCCESS;
	}
}
