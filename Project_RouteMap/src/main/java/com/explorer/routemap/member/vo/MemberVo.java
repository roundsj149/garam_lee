package com.explorer.routemap.member.vo;

import java.sql.Timestamp;
import java.util.Date;

public class MemberVo {
	private int member_no;
	private int member_levelexp;
	private String member_id;
	private String member_pw;
	private String member_nickname;
	private String member_fullname;
	private String member_gender;
	private String member_birth;
	private String member_phone;
	private Date member_joindate;
	private String sessionKey;
	private Timestamp sessionLimit;
	private String member_status;
	private int member_login_trial;

	public MemberVo() {
		super();
	}

	public MemberVo(int member_no, int member_levelexp, String member_id, String member_pw, String member_nickname,
			String member_fullname, String member_gender, String member_birth, String member_phone,
			Date member_joindate, String sessionKey, Timestamp sessionLimit, String member_status,
			int member_login_trial) {
		super();
		this.member_no = member_no;
		this.member_levelexp = member_levelexp;
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_nickname = member_nickname;
		this.member_fullname = member_fullname;
		this.member_gender = member_gender;
		this.member_birth = member_birth;
		this.member_phone = member_phone;
		this.member_joindate = member_joindate;
		this.sessionKey = sessionKey;
		this.sessionLimit = sessionLimit;
		this.member_status = member_status;
		this.member_login_trial = member_login_trial;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getMember_levelexp() {
		return member_levelexp;
	}

	public void setMember_levelexp(int member_levelexp) {
		this.member_levelexp = member_levelexp;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_fullname() {
		return member_fullname;
	}

	public void setMember_fullname(String member_fullname) {
		this.member_fullname = member_fullname;
	}

	public String getMember_gender() {
		return member_gender;
	}

	public void setMember_gender(String member_gender) {
		this.member_gender = member_gender;
	}

	public String getMember_birth() {
		return member_birth;
	}

	public void setMember_birth(String member_birth) {
		this.member_birth = member_birth;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public Date getMember_joindate() {
		return member_joindate;
	}

	public void setMember_joindate(Date member_joindate) {
		this.member_joindate = member_joindate;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Timestamp getSessionLimit() {
		return sessionLimit;
	}

	public void setSessionLimit(Timestamp sessionLimit) {
		this.sessionLimit = sessionLimit;
	}

	public String getMember_status() {
		return member_status;
	}

	public void setMember_status(String member_status) {
		this.member_status = member_status;
	}

	public int getMember_login_trial() {
		return member_login_trial;
	}

	public void setMember_login_trial(int member_login_trial) {
		this.member_login_trial = member_login_trial;
	}
}