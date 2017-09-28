package com.briup.spring.ioc.bean;

import java.io.Serializable;

public class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Address address;
	
	public Student() {
	}
	
	/*public Student(Address address) {
		super();
		this.address = address;
	}*/

	public Student(String name, int age, Address address) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", address="
				+ address + "]";
	}
	
}
