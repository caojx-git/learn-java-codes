package com.briup.ch07;
import java.util.Iterator;
public class ArrayListTest{
	public static void main(String[] args){
		List list = new ArrayList();

		for(int i=0;i<4;i++){
			list.add("string"+i);
		}
		list.add(5,"String4");
	/*	for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		*/
		Iterator i = list.iterator();
		while(i.hasNext()){
			String s = (String)i.next();
			System.out.println(s);
		}
	}
}
