    package com.explorer.routemap.admin.vo;

import java.util.Date;

public class AdminVo {

	private int admin_no;
	private String admin_id;
	private String admin_pw;
	private String admin_nickname;
	private String admin_fullname;
	private String admin_gender;
	private String admin_birth;
	private String admin_phone;
	private Date admin_joindate;
	private String admin_status;
	private int admin_login_trial;
	
	public AdminVo() {
		super();
	}

	public AdminVo(int admin_no, String admin_id, String admin_pw, String admin_nickname, String admin_fullname,
			String admin_gender, String admin_birth, String admin_phone, Date admin_joindate, String admin_status,
			int admin_login_trial) {
		super();
		this.admin_no = admin_no;
		this.admin_id = admin_id;
		this.admin_pw = admin_pw;
		this.admin_nickname = admin_nickname;
		this.admin_fullname = admin_fullname;
		this.admin_gender = admin_gender;
		this.admin_birth = admin_birth;
		this.admin_phone = admin_phone;
		this.admin_joindate = admin_joindate;
		this.admin_status = admin_status;
		this.admin_login_trial = admin_login_trial;
	}

	public int getAdmin_no() {
		return admin_no;
	}

	public void setAdmin_no(int admin_no) {
		this.admin_no = admin_no;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_pw() {
		return admin_pw;
	}

	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}

	public String getAdmin_nickname() {
		return admin_nickname;
	}

	public void setAdmin_nickname(String admin_nickname) {
		this.admin_nickname = admin_nickname;
	}

	public String getAdmin_fullname() {
		return admin_fullname;
	}

	public void setAdmin_fullname(String admin_fullname) {
		this.admin_fullname = admin_fullname;
	}

	public String getAdmin_gender() {
		return admin_gender;
	}

	public void setAdmin_gender(String admin_gender) {
		this.admin_gender = admin_gender;
	}

	public String getAdmin_birth() {
		return admin_birth;
	}

	public void setAdmin_birth(String admin_birth) {
		this.admin_birth = admin_birth;
	}

	public String getAdmin_phone() {
		return admin_phone;
	}

	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}

	public Date getAdmin_joindate() {
		return admin_joindate;
	}

	public void setAdmin_joindate(Date admin_joindate) {
		this.admin_joindate = admin_joindate;
	}

	public String getAdmin_status() {
		return admin_status;
	}

	public void setAdmin_status(String admin_status) {
		this.admin_status = admin_status;
	}

	public int getAdmin_login_trial() {
		return admin_login_trial;
	}

	public void setAdmin_login_trial(int admin_login_trial) {
		this.admin_login_trial = admin_login_trial;
	}

}

    
