package com.briup.hql;

public class User {
	/*
	 * 多的一方持有少的一方的引用
	 * */
	private long id;
	private String name;
	private Group group;
	
	
	public User() {
		
	}
	public User(long id, String name, Group group) {
		this.id = id;
		this.name = name;
		this.group = group;
	}
	public User(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
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
}
