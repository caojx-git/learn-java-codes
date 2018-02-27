package com.briup.event.ch10;

public class EmotionEvent {
	private String info;
	private Girl girl;
	
	public EmotionEvent(){}
	public EmotionEvent(String info,Girl girl){
		this.info = info;
		this.girl=girl;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public String getInfo(){
		return info;
	}
	public void setGirl(Girl girl){
		this.girl=girl;
	}
	public Girl getGirl(){
		return girl;
	}
}
