package com.briup.spring.aop.bean.annotation.aop;

//“µŒÒ¿‡
public class AspectBiz {
	
	public void biz(){
		System.out.println("Aspect biz");
		/*throw new RuntimeException();*/
	}
	
	public void init(String bizNae,int time){
		System.out.println("aspectBiz init00000:"+bizNae+"  "+time);
	}

}
