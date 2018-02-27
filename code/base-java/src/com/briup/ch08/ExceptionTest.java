package com.briup.ch08;

public class ExceptionTest {
	public static void division(){
		int i = 10;
		int j = 0;
		try{//try用来包裹可能会出项的异常代码
			System.out.println("j="+j);
			int x = i/j;	//异常 ArithmeticException
			ExceptionTest ex = null;
			ex.test();		//NullPointerException
			System.out.println("Occur exception");
		}catch(ArithmeticException e){//抓取Exception或其子类的异常类型
			//异常处理
			e.printStackTrace();//打印异常栈
			System.out.println("catch statement");
			//System.out.println("除数的分母不能为0");
		}catch(NullPointerException e){
			e.printStackTrace();//打印异常栈
			System.out.println("catch2 statement");
		}finally{
			System.out.println("method end");
		}
		
	}
	public static void main(String[] args){
		System.out.println("before invoke");
		division();
		System.out.println("later invoke");
	}
	public void test(){
		
	}
}
