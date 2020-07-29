    package com.explorer.routemap.board.vo;

import java.util.Date;

public class FreeboardCommentVo {

	private int freeboard_reply_no;
	private int member_no;
	private int freeboard_no;
	private String freeboard_reply_content;
	private Date freeboard_reply_writedate;
	
	public FreeboardCommentVo() {
		super();
	}

	public FreeboardCommentVo(int freeboard_reply_no, int member_no, int freeboard_no, String freeboard_reply_content,
			Date freeboard_reply_writedate) {
		
		this.freeboard_reply_no = freeboard_reply_no;
		this.member_no = member_no;
		this.freeboard_no = freeboard_no;
		this.freeboard_reply_content = freeboard_reply_content;
		this.freeboard_reply_writedate = freeboard_reply_writedate;
	}

	public int getFreeboard_reply() {
		return freeboard_reply_no;
	}

	public void setFreeboard_reply(int freeboard_reply_no) {
		this.freeboard_reply_no = freeboard_reply_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getFreeboard_no() {
		return freeboard_no;
	}

	public void setFreeboard_no(int freeboard_no) {
		this.freeboard_no = freeboard_no;
	}

	public String getFreeboard_reply_content() {
		return freeboard_reply_content;
	}

	public void setFreeboard_reply_content(String freeboard_reply_content) {
		this.freeboard_reply_content = freeboard_reply_content;
	}

	public Date getFreeboard_reply_writedate() {
		return freeboard_reply_writedate;
	}

	public void setFreeboard_reply_writedate(Date freeboard_reply_writedate) {
		this.freeboard_reply_writedate = freeboard_reply_writedate;
	}
	
}

    
