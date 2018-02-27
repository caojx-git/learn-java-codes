package com.briup.run.common.bean;

/**
 * Memberspace entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Memberspace implements java.io.Serializable {

	// Fields

	private Long id;
	private Memberinfo memberinfo;
	private String opinion;
	private String runtime;
	private String runplace;
	private String runstar;
	private String runhabit;
	private String cellphone;
	private String icon;

	// Constructors

	/** default constructor */
	public Memberspace() {
	}

	/** full constructor */
	public Memberspace(Memberinfo memberinfo, String opinion, String runtime,
			String runplace, String runstar, String runhabit, String cellphone,
			String icon) {
		this.memberinfo = memberinfo;
		this.opinion = opinion;
		this.runtime = runtime;
		this.runplace = runplace;
		this.runstar = runstar;
		this.runhabit = runhabit;
		this.cellphone = cellphone;
		this.icon = icon;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Memberinfo getMemberinfo() {
		return this.memberinfo;
	}

	public void setMemberinfo(Memberinfo memberinfo) {
		this.memberinfo = memberinfo;
	}

	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getRuntime() {
		return this.runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getRunplace() {
		return this.runplace;
	}

	public void setRunplace(String runplace) {
		this.runplace = runplace;
	}

	public String getRunstar() {
		return this.runstar;
	}

	public void setRunstar(String runstar) {
		this.runstar = runstar;
	}

	public String getRunhabit() {
		return this.runhabit;
	}

	public void setRunhabit(String runhabit) {
		this.runhabit = runhabit;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}