package com.briup.orm.m2m.bi;

import java.util.HashSet;
import java.util.Set;
//多对多双向关联
public class Teacher {
	private long id;
	private String name;
	private Set<Student> students = 
					new HashSet<Student>();
	
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
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
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", students="
				+ students + "]";
	}
	
}
