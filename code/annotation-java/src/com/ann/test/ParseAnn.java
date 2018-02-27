package com.ann.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/*
 * 通过反射解析注解
 * */
public class ParseAnn {

	public static void main(String[] args) {
		//1.使用类加载器加载类
		try{
			Class c = Class.forName("com.ann.test.Child");
			//2.找到类上边的注解
			boolean isExist = c.isAnnotationPresent(Description.class);
			if(isExist){
				//3.拿到注解实例
				Description description = (Description) c.getAnnotation(Description.class);
				System.out.println(description.value()+"---1");
			}
			
			//4.找到方法上的注解
			Method[] methods =c.getMethods();
			for(Method method:methods){
				boolean isMExist = method.isAnnotationPresent(Description.class);
				if(isMExist){
					//5.拿到方法上的注解实例
					Description dmethod = method.getAnnotation(Description.class);
					System.out.println(dmethod.value()+"---2");
				}
			}
			
			//获取所有的方法上的注解
			for(Method m:methods){
				Annotation[] annotations = m.getAnnotations();
				for(Annotation annotation:annotations){
					if(annotation instanceof Description){
						Description d = (Description)annotation;
						System.out.println(d.value()+"---3");
					}
				}
			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
}
