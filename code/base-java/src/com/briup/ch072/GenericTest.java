package com.briup.ch072;

import java.util.ArrayList;
import java.util.List;


public class GenericTest {
	public static void main(String[] args){
		ArrayList<String> list = new ArrayList();
		list.add("string1");
		list.add("string2");
		list.add("string3");
		list.add("string4");
		ArrayList list2 = new ArrayList();
		//�Ϸ�������Ч
		ArrayList list3 = new ArrayList<String>();
		//�Ϸ�
		List<String> list4 = new ArrayList<String>();
		//���Ϸ�
		//ArrayList<Object> list5 = new ArrayList<String>();
	
		
	
		
	}

}
