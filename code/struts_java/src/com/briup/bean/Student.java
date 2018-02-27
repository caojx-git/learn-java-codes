package com.briup.bean;

import java.io.Serializable;

public class Student implements Serializable{
	private static final long serialVersionUID=4861554346284392457L;
	private String name;
	private String password;
	private int age;
	private String gender;
	private String province;
	private String hobby;
	public Student() {
	}
	public Student(String name) {
		this.name = name;
	}
	public Student(String name, String password, int age, String gender,
			String province, String hobby) {
		this.name = name;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.province = province;
		this.hobby = hobby;
	}
	
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", password=" + password + ", age="
				+ age + ", gender=" + gender + ", province=" + province
				+ ", hobby=" + hobby + "]";
	}
	
	
	

}
