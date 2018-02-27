package com.briup.ch072;

import java.util.Collection;

public class GenericBound {
	public static void print(Collection<? extends Number> c){
		for(Number n:c){//������Number ��������ֻ�ܷ������Ϳ���ΪNumber ������
			System.out.println(n);
		}
	}
	public static void print1(Collection<? super Number> s){
		for(Object s1:s){////������Number ��������ֻ�ܷ������Ϳ���ΪNumber �ĸ���
			System.out.println(s1);
		}
	}
	

	public static void main(String[] args){
		Collection<Object> c1 = null;
		Collection<Number> c2 = null;
		Collection<Integer> c3 = null;
		
		//print(c1);
		print(c2);
		print(c3);
		print1(c1);
		print1(c2);
		//print1(c3);
		
		//������
		Collection c4 = null;
	}


}
