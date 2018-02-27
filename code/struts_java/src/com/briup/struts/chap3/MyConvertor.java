package com.briup.struts.chap3;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.briup.bean.Student;

public class MyConvertor extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map map, String[] str, Class cls) {
		String s = str[0];
		System.out.println(s);
		String[] s2 = s.split(":");
		Student student = new Student();
		student.setName(s2[0]);
		student.setAge(Integer.parseInt(s2[1]));
		student.setGender(s2[2]);
		return student;
	}

	@Override
	public String convertToString(Map map, Object obj) {
		return obj.toString();
	}
	
}
