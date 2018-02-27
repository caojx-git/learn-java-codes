package com.briup.ch07;

import java.util.*;
public class TreeSetTest {
	public static void main(String[] args){
		Set set = new TreeSet();
		/*for(int i=0;i<5;i++){
			set.add("string"+i);
		}*/
	/*	set.add("abc");
		set.add("Abc");
		set.add("Xbc");
		set.add("abd");*/
		set.add(new Teacher(1));
		set.add(new Teacher(2));
		set.add(new Teacher(3));
		set.add(new Teacher(4));
		Iterator i = set.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
			//System.out.println(i.hashCode());
		}
	}
}

 class Teacher implements Comparable{
	public int id;
	public Teacher(){}
	public Teacher(int id){
		this.id = id;
	}
	@Override
	public int compareTo(Object o) {
		Teacher t = (Teacher)o;
		System.out.println(id+"compareto"+t.id);
		return t.id-id;
	}
	//如果是1（正数）的话，正序排列把新添加的元素放在旧的元素后面 升序
	//0的话认为前后元素相同，不添加
	//如果是以个负数的话，按照倒叙排列 降序
	public String toString(){
		return id+"";
	}
	
	
}