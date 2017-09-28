package com.briup.spring.ioc.bean;

import java.io.Serializable;

public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	private String country;
	private String province;
	private String city;
	public Address() {
	}
	
	public Address(String country, String province, String city) {
		super();
		this.country = country;
		this.province = province;
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Address [country=" + country + ", province=" + province
				+ ", city=" + city + "]";
	}
	
}
