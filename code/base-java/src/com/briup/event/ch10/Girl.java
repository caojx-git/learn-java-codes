package com.briup.event.ch10;

public class Girl {
	private String name;
	private EmotionListener boy;
	public Girl(){
	}
	public Girl(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setBoy(EmotionListener boy){
		this.boy=boy;
	}
	public EmotionListener getBoy(){
		return boy;
	}
	//ÊÂ¼þÔ´
	public void createSad(){
		EmotionEvent e = new EmotionEvent("sad",this);
		boy.sad(e);
	}
	public void createHappy(){
		EmotionEvent e = new EmotionEvent("happy",this);
		boy.happy(e);
	}
}
