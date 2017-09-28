package com.briup.spring.ioc.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Coll implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<String> list;
	//private Set<Integer> set;
	private Set<User> set;
	private Map<String,String> map;
	private Properties properties;
	
	public Coll() {
	}
	
	public Coll(List<String> list, Set<User> set, Map<String, String> map,
			Properties properties) {
		super();
		this.list = list;
		this.set = set;
		this.map = map;
		this.properties = properties;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	

/*	public Set<Integer> getSet() {
		return set;
	}

	public void setSet(Set<Integer> set) {
		this.set = set;
	}*/

	public Set<User> getSet() {
		return set;
	}

	public void setSet(Set<User> set) {
		this.set = set;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void printList() {
		System.out.println("list:"+list.getClass());
		for(String str:list){
			System.out.println(str);
		}
	}
/*	public void printSet(){
		System.out.println("set:"+set.getClass());
		for(Integer i:set){
			System.out.println(i);
		}
	}*/
	public void printSet(){
		System.out.println("set:"+set.getClass());
		for(User u:set){
			System.out.println(u);
		}
	}
	public void  printMap() {
		System.out.println("map"+map.getClass());
		Set<String> set = map.keySet();
		for(String s : set){
			System.out.println("key:"+s+"value:"+map.get(s));
		}
	}
	public void printProperties(){
		System.out.println("properties:"+properties.getClass());
		Set set = properties.entrySet();
		for(Object o:set){
			System.out.println(o);
		}
	}
}
