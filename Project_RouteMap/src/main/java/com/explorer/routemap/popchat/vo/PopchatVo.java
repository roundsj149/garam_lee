package com.explorer.routemap.popchat.vo;

import java.util.Date;

public class PopchatVo {
	
    private int popchat_no;
    private int member_no;
    private String popchat_content;
    private Date popchat_writedate;
    
	public PopchatVo() {
		super();
	}

	public int getPopchat_no() {
		return popchat_no;
	}

	public void setPopchat_no(int popchat_no) {
		this.popchat_no = popchat_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public String getPopchat_content() {
		return popchat_content;
	}

	public void setPopchat_content(String popchat_content) {
		this.popchat_content = popchat_content;
	}

	public Date getPopchat_writedate() {
		return popchat_writedate;
	}

	public void setPopchat_writedate(Date popchat_writedate) {
		this.popchat_writedate = popchat_writedate;
	}
	
}
