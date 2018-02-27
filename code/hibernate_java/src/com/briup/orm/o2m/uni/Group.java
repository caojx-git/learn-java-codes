package com.briup.orm.o2m.uni;

import java.util.HashSet;
import java.util.Set;

//一对多单向关联
public class Group {
	private long id;
	private String name;
	private Set<User> users = new HashSet<User>();
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
