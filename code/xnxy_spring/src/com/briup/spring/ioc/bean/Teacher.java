package com.briup.spring.ioc.bean;

import java.io.Serializable;

public class Teacher implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String gender;
	
/*	public Teacher() {
	}*/

	public Teacher(int id, String name, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", gender=" + gender
				+ "]";
	}
	
	
}
