package com.briup.ch07;
import java.util.*;

public class HashSetTest{
	public static void main(String[] args){
		Set set  = new HashSet();
		for(int i=0;i<5;i++){
			//set.add("string"+i);
			set.add(new Student(i));
		}
		
		set.add(new Student(1));
		set.add(new Student(1));
		set.add(new Student(1));
		System.out.println("size="+set.size());
		Student s1 = new Student(1);
		System.out.println(set.contains(s1));
		Iterator<Student> i =set.iterator();
		while(i.hasNext()){
			Student s = i.next();
			
			System.out.println(s.hashCode());
			//System.out.println(s.id);
			System.out.println(s);
		}
	}
}

class Student{
	public int id;
	public Student(){
	}
	/*public int hashCode(){
		return 1;
	}*/
	public boolean equals(Object o){
		Student s = (Student)o;
		return id == s.id;
	}
	public Student(int id){
		this.id = id;
	}
	
}