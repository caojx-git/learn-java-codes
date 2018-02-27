package com.briup.run.common.util;

import com.briup.run.common.util.ip.IPSeeker;


public class Util {
		public static String PROVINCE[] = { "请选择", "北京", "上海", "天津", "重庆", "辽宁",
		"广东", "浙江", "江苏", "山东", "四川", "黑龙江", "湖南", "湖北", "河南", "安徽", "河北",
		"吉林", "江西", "广西", "山西", "内蒙古", "甘肃", "贵州", "新疆", "云南", "宁夏", "海南",
		"青海", "西藏", "港澳台", "海外", "其它" };

	public static String getProvinceNameById(String id) {
		return PROVINCE[Integer.parseInt(id)];
	}

	public static String getAddress(String ip) {
		String country = "";
		String area = "";
		IPSeeker seeker = IPSeeker.getInstance();
		country = seeker.getCountry(ip);
		area = seeker.getArea(ip);
		return country + area;
	}
}
