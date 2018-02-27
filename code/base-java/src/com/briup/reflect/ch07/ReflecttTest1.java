package com.briup.reflect.ch07;

import java.lang.reflect.*;
public class ReflecttTest1 {
	public static void main(String[] args) throws Exception {
		String s = args[0];
		Object o=Class.forName(s).newInstance();
		System.out.println(o);
		Integer[] obj = (Integer[])Array.newInstance(Integer.class,10);
		System.out.println(obj);
	}

}
