package com.briup.event.ch10;

public class EmotionTest {
	public static void main(String[] args){
		Girl g = new Girl("losy");
		g.setBoy(new EmotionListener(){	//匿名内部类
			public void sad(EmotionEvent e){
				Girl girl = e.getGirl();
				System.out.println(girl.getName()+e.getInfo());
			}

			@Override
			public void happy(EmotionEvent e) {
				Girl girl = e.getGirl();
				System.out.println(girl.getName()+e.getInfo());
			}
		});
		g.createSad();
	}
}
