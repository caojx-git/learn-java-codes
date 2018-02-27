package com.briup.ch07;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
public class SortList {

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("a");
		list.add("z");
		list.add("x");
		list.add("y");
		list.add("f");
		list.add("f");
		/*Collections.sort(list);//ArrayList中的辅助类，专门用于对List进行排序。
		for(Object o:list){
			System.out.println(o);
		}
		*/
		//倒叙排序
		Collections.sort(list,new Comparator(){
			public int compare(Object o1,Object o2){
			String s1 = (String)o1;
			String s2 = (String)o2;
			return s2.compareTo(s1);
			}
		});
		for(Object o:list){
			System.out.println(o);
		}
	}

}
