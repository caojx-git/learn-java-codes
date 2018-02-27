package com.briup.ch08;

public class MyException extends Exception{
	public String s;
	public MyException(){
		
	}
	public MyException(String message){
		super(message);
	}
	public MyException(String mesage,Throwable cause){
		super(mesage,cause);
	}
	public void test(){
		System.out.println("test");
	}
}
