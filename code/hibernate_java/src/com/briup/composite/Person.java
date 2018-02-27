package com.briup.composite;

//联合主键映射
public class Person {
	private PersonPK pk;
	private int age;
	private Address address;
	
	public PersonPK getPk() {
		return pk;
	}
	public void setPk(PersonPK pk) {
		this.pk = pk;
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
}
