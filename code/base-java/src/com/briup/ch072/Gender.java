package com.briup.ch072;


/*
 * 声明一个Enum类， 枚举类默认继承与java.lang.Enum
 * 
 * 在类中声明该类可创建的所有枚举实例
 * MAIL("男") --> public static final MAIL = new Gender(男);
 * 将所有的属性，构造器声明为私有的为了实现良好的封装
 * */
public enum Gender {
	MAIL("男"),FEMALE("女");
	private String name;
	
	private Gender(){
		
	}
	private Gender(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}


