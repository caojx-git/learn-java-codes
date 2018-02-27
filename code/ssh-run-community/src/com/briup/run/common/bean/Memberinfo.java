package com.briup.run.common.bean;

import java.util.Date;


public class Memberinfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Graderecord graderecord;
	private String nickname;
	private String password;
	private String gender;
	private Long age;
	private String email;
	private String provincecity;
	private String address;
	private String phone;
	private String passwordquestion;
	private String passwordanswer;
	private String recommender;
	private Long point;
	private Date registerdate;
	private Date latestdate;
	private Long status;
	private Long isonline;
	private Memberspace memberSpace;

	public Memberinfo() {
	}

	public Memberinfo(String nickname, String password, String gender,
			Long age, String email) {
		this.nickname = nickname;
		this.password = password;
		this.gender = gender;
		this.age = age;
		this.email = email;
	}

	public Memberinfo(Graderecord graderecord, String nickname,
			String password, String gender, Long age, String email,
			String provincecity, String address, String phone,
			String passwordquestion, String passwordanswer, String recommender,
			Long point, Date registerdate, Date latestdate, Long status,
			Long isonline, Memberspace memberSpace) {
		this.graderecord = graderecord;
		this.nickname = nickname;
		this.password = password;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.provincecity = provincecity;
		this.address = address;
		this.phone = phone;
		this.passwordquestion = passwordquestion;
		this.passwordanswer = passwordanswer;
		this.recommender = recommender;
		this.point = point;
		this.registerdate = registerdate;
		this.latestdate = latestdate;
		this.status = status;
		this.isonline = isonline;
		this.memberSpace = memberSpace;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Graderecord getGraderecord() {
		return this.graderecord;
	}

	public void setGraderecord(Graderecord graderecord) {
		this.graderecord = graderecord;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getAge() {
		return this.age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvincecity() {
		return this.provincecity;
	}

	public void setProvincecity(String provincecity) {
		this.provincecity = provincecity;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasswordquestion() {
		return this.passwordquestion;
	}

	public void setPasswordquestion(String passwordquestion) {
		this.passwordquestion = passwordquestion;
	}

	public String getPasswordanswer() {
		return this.passwordanswer;
	}

	public void setPasswordanswer(String passwordanswer) {
		this.passwordanswer = passwordanswer;
	}

	public String getRecommender() {
		return this.recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	public Long getPoint() {
		return this.point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Date getRegisterdate() {
		return this.registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	public Date getLatestdate() {
		return this.latestdate;
	}

	public void setLatestdate(Date latestdate) {
		this.latestdate = latestdate;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getIsonline() {
		return this.isonline;
	}

	public void setIsonline(Long isonline) {
		this.isonline = isonline;
	}

	public Memberspace getMemberSpace() {
		return memberSpace;
	}

	public void setMemberSpace(Memberspace memberSpace) {
		this.memberSpace = memberSpace;
	}

	@Override
	public String toString() {
		return "Memberinfo [id=" + id + ", graderecord=" + graderecord
				+ ", nickname=" + nickname + ", password=" + password
				+ ", gender=" + gender + ", age=" + age + ", email=" + email
				+ ", provincecity=" + provincecity + ", address=" + address
				+ ", phone=" + phone + ", passwordquestion=" + passwordquestion
				+ ", passwordanswer=" + passwordanswer + ", recommender="
				+ recommender + ", point=" + point + ", registerdate="
				+ registerdate + ", latestdate=" + latestdate + ", status="
				+ status + ", isonline=" + isonline + ", memberSpace="
				+ memberSpace + "]";
	}
	
	

}