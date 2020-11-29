package com.dayside.vacation.member.model;

import java.util.Date;

public class Member {
	
	private String employeeNo;
	private String emailId;
	private String fullname;
	private String pw;
	private String hashSalt;
	private String team;
	private char leaderYn;
	private char adminYn;
	private Date joinDt;
	private String joinStatus;
	
	public Member() {
		super();
	}

	public Member(String employeeNo, String emailId, String fullname, String pw, String hashSalt, String team,
			char leaderYn, char adminYn, Date joinDt, String joinStatus) {
		super();
		this.employeeNo = employeeNo;
		this.emailId = emailId;
		this.fullname = fullname;
		this.pw = pw;
		this.hashSalt = hashSalt;
		this.team = team;
		this.leaderYn = leaderYn;
		this.adminYn = adminYn;
		this.joinDt = joinDt;
		this.joinStatus = joinStatus;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getHashSalt() {
		return hashSalt;
	}

	public void setHashSalt(String hashSalt) {
		this.hashSalt = hashSalt;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public char getLeaderYn() {
		return leaderYn;
	}

	public void setLeaderYn(char leaderYn) {
		this.leaderYn = leaderYn;
	}

	public char getAdminYn() {
		return adminYn;
	}

	public void setAdminYn(char adminYn) {
		this.adminYn = adminYn;
	}

	public Date getJoinDt() {
		return joinDt;
	}

	public void setJoinDt(Date joinDt) {
		this.joinDt = joinDt;
	}

	public String getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}

}