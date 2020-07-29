package com.explorer.routemap.board.vo;

import java.util.*;

public class RouteboardReplyVo {

	private int routeboard_reply_no;
	private int member_no;
	private int routeboard_no;
	private String routeboard_reply_content;
	private Date routeboard_reply_writedate;
	
	public RouteboardReplyVo() {
		super();
	}

	public RouteboardReplyVo(int routeboard_reply_no, int member_no, int routeboard_no, String routeboard_reply_content,
			Date routeboard_reply_writedate) {
		super();
		this.routeboard_reply_no = routeboard_reply_no;
		this.member_no = member_no;
		this.routeboard_no = routeboard_no;
		this.routeboard_reply_content = routeboard_reply_content;
		this.routeboard_reply_writedate = routeboard_reply_writedate;
	}

	public int getRouteboard_reply_no() {
		return routeboard_reply_no;
	}

	public void setRouteboard_reply_no(int routeboard_reply_no) {
		this.routeboard_reply_no = routeboard_reply_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getRouteboard_no() {
		return routeboard_no;
	}

	public void setRouteboard_no(int routeboard_no) {
		this.routeboard_no = routeboard_no;
	}

	public String getRouteboard_reply_content() {
		return routeboard_reply_content;
	}

	public void setRouteboard_reply_content(String routeboard_reply_content) {
		this.routeboard_reply_content = routeboard_reply_content;
	}

	public Date getRouteboard_reply_writedate() {
		return routeboard_reply_writedate;
	}

	public void setRouteboard_reply_writedate(Date routeboard_reply_writedate) {
		this.routeboard_reply_writedate = routeboard_reply_writedate;
	}
}
