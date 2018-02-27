package com.briup.ch08;

public class MyExceptionTest {
	public static void main(String[] args){
		int x;
		x=10;
		try{
			throw new MyException("x¸³Öµ´óÓÚ0");
		}catch(MyException e){
			try {
				throw new Exception(e.getMessage(),e);
			} catch (Exception e1) {
				System.out.println(e1.getCause());
				e1.printStackTrace();
			}
		}
		System.out.println("main end");
	}
	
}
