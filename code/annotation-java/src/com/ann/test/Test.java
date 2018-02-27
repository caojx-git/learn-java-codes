package com.ann.test;

public class Test {

	@SuppressWarnings("deprecation")  //@SuppressWarnings 用于忽略或抑制警告，值为为发出告警的注解名
	public static void main(String[] args) {
		Person person = new Child();
		person.sing();
	}
}