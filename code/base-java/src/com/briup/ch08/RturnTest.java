package com.briup.ch08;

import java.io.IOException;

public class RturnTest {
	public static int test(){
		String s = "6888";
		double d = Double.parseDouble(s);	//只能接受数值型的字符串
		System.out.println(d);
		int i=0;
		try{
			System.out.println("try statement");
			return i++;	//与finally处的叠加执行
		}catch(Exception e){
			System.out.println("catch statement");
			return i++;
		}finally{
			System.out.println("finaly statement");	
			return i;
		}
	}
	
	public static void main(String[] args){
		System.out.println(test());
	}
}
