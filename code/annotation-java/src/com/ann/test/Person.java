
package com.ann.test;

@Description("I am a class annotation")
public class Person {
	
	@Description("I am a method annotation")
	public String name() {
		return null;
	}
	
	@Description("I am a method annotation")
	public int age(){
		return 0;
	}
	
	@Deprecated  //注解说明该方法过时了
	public void sing(){
		
	}
}
