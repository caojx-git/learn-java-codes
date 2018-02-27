package com.briup.ch072;

public class Car {
	//û��ö��֮ǰ��������Ҫ����������Ҫ��ʵ��
	public static Car AUDI = new Car("audi","German");
	public static Car DASAUTO = new Car("dasauto","German");
	public static Car BENZ = new Car("benz","German");
	private String name;
	private String country;
	private Car(){}	
	private Car(String name, String country){
		this.name = name;
		this.country = country;
	}

}
