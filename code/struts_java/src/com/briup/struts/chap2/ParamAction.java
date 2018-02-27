package com.briup.struts.chap2;

import com.briup.bean.Student;
import com.opensymphony.xwork2.ActionSupport;

public class ParamAction extends ActionSupport {
/*
 * 方法1
 * 	//变量名需要跟前台传过来的参数一致 否则拿不到值
	private String name;
	private String pwd;
	private int age;
	private String gender;
	private String province;
	private String hobby;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	@Override
	public String execute() throws Exception {
		Student student = new Student(name, pwd, age, gender, province, hobby);
		System.out.println(student);
		return SUCCESS;
	}
*/
	/*
	 * 方法2
	 * 
	 * 前台页面传送参数
	 * 
	 * <input type="text" name="student.name">
	 * <input type="password" name="student.password">
	 * 
	 * student.中的student名字需要同本类中的成员变量student名字一样
	 * student.name 中的name需要跟Student.java中的成员变量名字一样
	 * student.passowrd 中的password需要跟Student.java中的成员变量名字一样
	 * ....
	 * */
	private Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	

}
