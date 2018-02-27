package com.briup.run.common.bean;

/**
 * Friendrecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Friendrecord implements java.io.Serializable {

	// Fields

	private Long id;
	private String selfname;
	private String friendname;

	// Constructors

	/** default constructor */
	public Friendrecord() {
	}

	/** full constructor */
	public Friendrecord(String selfname, String friendname) {
		this.selfname = selfname;
		this.friendname = friendname;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSelfname() {
		return this.selfname;
	}

	public void setSelfname(String selfname) {
		this.selfname = selfname;
	}

	public String getFriendname() {
		return this.friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

}