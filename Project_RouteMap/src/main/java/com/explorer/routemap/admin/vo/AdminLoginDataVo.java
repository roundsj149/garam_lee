package com.explorer.routemap.admin.vo;


import java.util.Date;

public class AdminLoginDataVo {

	private int login_no;
	private int member_no;
	private Date login_date;
	private int login_hit;
	
	public AdminLoginDataVo() {
		super();
	}

	public AdminLoginDataVo(int login_no, int member_no, Date login_date, int login_hit) {
		super();
		this.login_no = login_no;
		this.member_no = member_no;
		this.login_date = login_date;
		this.login_hit = login_hit;
	}

	public int getLogin_no() {
		return login_no;
	}

	public void setLogin_no(int login_no) {
		this.login_no = login_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public Date getLogin_date() {
		return login_date;
	}

	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}

	public int getLogin_hit() {
		return login_hit;
	}

	public void setLogin_hit(int login_hit) {
		this.login_hit = login_hit;
	}
	
}
