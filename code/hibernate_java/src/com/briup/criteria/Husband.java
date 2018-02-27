package com.briup.criteria;

public class Husband {
	private long id;
	private String name;
	private int age;
	private double salary;
	
	private Wife wife;
	
	public Husband() {
	}
	public Husband(String name, int age, double salary) {
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Wife getWife() {
		return wife;
	}
	public void setWife(Wife wife) {
		this.wife = wife;
	}
	
}
