package com.briup.ch08;

public class CheckedTest {
	public static Object getInstance(String className) throws ClassNotFoundException,
	IllegalAccessException,InstantiationException{//throws 用于提示可能会抛出的异常类型
		Class c=null;
		Object o=null;
		try {
			c = Class.forName(className)  ;
			o = c.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;		//向上抛出异常
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
	public static void main(String[] args){
		try {
			Object o=getInstance("com.briup.ch08.CheckedTest");
			System.out.println(o);
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("main method catch");
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		}
		
	}


}
