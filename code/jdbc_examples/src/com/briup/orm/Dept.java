package com.briup.orm;

public class Dept {
	private int id;
	private String name;
	private int region_id;
	public Dept(){
		
	}
	public Dept(int id,String name,int region_id){
		this.id = id;
		this.name = name;
		this.region_id = region_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return id+","+name;
	}
}
