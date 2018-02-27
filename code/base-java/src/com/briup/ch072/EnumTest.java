package com.briup.ch072;

import java.util.EnumMap;
import java.util.EnumSet;

public class EnumTest {
	public static void main(String[] args){
		Clothes chinel =Clothes.CHINEL;
		System.out.println(chinel.getCountry());
		System.out.println(chinel.name());
		System.out.println(chinel.ordinal());	//
		System.out.println(chinel.GUCCI.ordinal());	//
		System.out.println(chinel.ONLY.ordinal());	//
		System.out.println(Clothes.values());	
		Clothes[] c = Clothes.values();
		for(Clothes cl:c){
			System.out.println(cl);
		}
		
		Clothes c1 =Clothes.valueOf("CHINEL");	//通过枚举常量获取枚举实例
		System.out.println(chinel==c1);	//true
		Enum.valueOf(Clothes.class, "CHINEL");	//通过类镜像，跟枚举实例获取枚举实例
		
		
		EnumSet<Clothes> set = EnumSet.of(Clothes.CHINEL);
		set.add(Clothes.GUCCI);
		for(Clothes clothe:set){
			System.out.println(clothe);
		}
		EnumMap<Clothes,String> map = new EnumMap(Clothes.class);
		EnumMap<Clothes,String> map1 = new EnumMap<Clothes,String>(new EnumMap<Clothes,String>(Clothes.class));
	}

}
