package com.briup.jtest;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JTest {
	/*
	  	通过junit.Test的框架运行@Test标记的方法
	  	Test标记的方法可以直接执行
	  	如果同时出现多个Test方法，选中那个方法执行，则会执行全部Test标记方法
	  	注意：如果执行Test标记的方法，则Before或After标记的方法一定会执行
	  *
	  */
	@Test   
	public void testA() {
		System.out.println("testA");
	}
	@Before
	public void testB() {
		System.out.println("testB");
	}
	@After
	public void testC() {
		System.out.println("testC");
	}
	
	/*public static void main(String[] args) {
	
	}*/
}
