package com.briup.ch072;

import java.util.Collection;
import java.util.ArrayList;
public class GenericClass<Briup> {//<ռλ�����ȡ��>,�����ʱ���Է��ͽ��в���
	
	private Briup b;
	private String name;
	public GenericClass(){}
	public GenericClass(Briup b,String name){
		this.b = b;
		this.name = name;
	}
	public Briup method1(Briup b){
		return b;
	}
	public void print(Collection<?> c){
		for(Object o:c){
			System.out.println(o);
		}
	}
	public static void main(String[] args){
		GenericClass<String> g = new GenericClass();
		String s=g.method1("string");
		System.out.println(s); 
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		ArrayList<Double> a3 = new ArrayList<Double>();
		for(int i=0;i<3;i++){
			a1.add("str"+i);
			a2.add(i);
			a3.add(i+0.1);
		}
		g.print(a1);
		g.print(a2);
		g.print(a3);
	}

}
