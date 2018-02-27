package com.briup.composite;

import java.io.Serializable;
//这个主键类: 
//必须实现序列化接口  否则操作的时候会报错
//最好重写hashCode(不重新使用Object类中的也可以)
//equals方法一定要重写  因为比较主键是否相等的时候需要用到这两个方法
public class PersonPK implements Serializable{
	private static final long serialVersionUID = 3223722820575289771L;
	private long id;
	private String name;
	
	public PersonPK() {
		
	}
	public PersonPK(long id, String name) {
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
	@Override
	public int hashCode() {
		return (int)id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(this==obj){
			return true;
		}
		if(!(obj instanceof PersonPK)){
			return false;
		}
		PersonPK pk = (PersonPK) obj;
		if(this.id == pk.id&&this.name.equals(pk.name)){
			return true;
		}
		return false;
	}
	
	
}
