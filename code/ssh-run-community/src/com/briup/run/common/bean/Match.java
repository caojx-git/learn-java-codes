package com.briup.run.common.bean;

public class Match {

	private static String provincecity[] = {"unlimited", "浦东", "北京", "天津", "重庆", "辽宁",
		"广东", "浙江", "江苏", "山东", "四川", "黑龙江", "湖南", "湖北", "河南", "安徽", "河北",
		"吉林", "江西", "广西", "山西", "内蒙古", "甘肃", "贵州", "新疆", "云南", "宁夏", "海南",
		"青海", "西藏", "港澳台", "海外", "其它" };

	private static String ageRange[] = {"unlimited", "10-19", "20-29", "30-39"};
	public static String getProvinceNameById(String id) {
		return provincecity[Integer.parseInt(id)];
	}
	public static String getAgeRangeById(String id){
		return ageRange[Integer.parseInt(id)];
	}
	
	
}
