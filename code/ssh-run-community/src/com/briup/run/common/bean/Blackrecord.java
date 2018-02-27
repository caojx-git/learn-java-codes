package com.briup.run.common.bean;

/**
 * Blackrecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Blackrecord implements java.io.Serializable {

	// Fields

	private Long id;
	private String selfname;
	private String blackname;

	// Constructors

	/** default constructor */
	public Blackrecord() {
	}

	/** full constructor */
	public Blackrecord(String selfname, String blackname) {
		this.selfname = selfname;
		this.blackname = blackname;
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

	public String getBlackname() {
		return this.blackname;
	}

	public void setBlackname(String blackname) {
		this.blackname = blackname;
	}

}