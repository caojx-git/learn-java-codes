package com.briup.ch072;

public class TestGender {

	public static void main(String[] args) {
		Gender gender = Enum.valueOf(Gender.class, "FEMALE");
		System.out.println(gender.toString()+"--"+gender.name());
		
		System.out.println(gender.getName()+"---"+gender.toString());
		
	}
}
