package com.briup.ch07;

import java.util.HashSet;
import java.util.Set;

public class Ennancetor {
	public static void main(String[] args){
		Set set = new HashSet();
		set.add("str");
		set.add("str");
		set.add("str");
		set.add("str");
		for(Object s:set){
			System.out.println(s);
		}
	}

}
