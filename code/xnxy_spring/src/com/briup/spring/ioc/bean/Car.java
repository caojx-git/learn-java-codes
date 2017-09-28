package com.briup.spring.ioc.bean;

import java.io.Serializable;

public class Car implements Serializable {

	private static final long serialVersionUID = 1L;
	private String owner;
	private int price;
	private String name;
	
	public Car() {
		super();
	}

	public Car(String owner, int price, String name) {
		super();
		this.owner = owner;
		this.price = price;
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Car [owner=" + owner + ", price=" + price + ", name=" + name
				+ "]";
	}
	
}
