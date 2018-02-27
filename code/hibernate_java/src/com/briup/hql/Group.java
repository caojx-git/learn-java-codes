package com.briup.hql;

import java.util.HashSet;
import java.util.Set;

public class Group {
	/*
	 * 少的一方持有多的set集合
	 * */
	private long id;
	private String name;
	private Set<User> users = new HashSet<User>();
	
	public Group() {
	}
	public Group(long id, String name) {
		this.id = id;
		this.name = name;
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
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
