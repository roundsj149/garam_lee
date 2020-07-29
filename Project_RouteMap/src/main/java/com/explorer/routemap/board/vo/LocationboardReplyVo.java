package com.explorer.routemap.board.vo;

import java.util.Date;

public class LocationboardReplyVo {
    private int locatinoboard_reply_no;
    private int member_no;
    private int locationboard_no;
    private String locationboard_reply_content;
    private Date locationboard_reply_writedate;
    
	public LocationboardReplyVo() {
		super();
	}

	public LocationboardReplyVo(int locatinoboard_reply_no, int member_no, int locationboard_no,
			String locationboard_reply_content, Date locationboard_reply_writedate) {
		super();
		this.locatinoboard_reply_no = locatinoboard_reply_no;
		this.member_no = member_no;
		this.locationboard_no = locationboard_no;
		this.locationboard_reply_content = locationboard_reply_content;
		this.locationboard_reply_writedate = locationboard_reply_writedate;
	}

	public int getLocatinoboard_reply_no() {
		return locatinoboard_reply_no;
	}

	public void setLocatinoboard_reply_no(int locatinoboard_reply_no) {
		this.locatinoboard_reply_no = locatinoboard_reply_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getLocationboard_no() {
		return locationboard_no;
	}

	public void setLocationboard_no(int locationboard_no) {
		this.locationboard_no = locationboard_no;
	}

	public String getLocationboard_reply_content() {
		return locationboard_reply_content;
	}

	public void setLocationboard_reply_content(String locationboard_reply_content) {
		this.locationboard_reply_content = locationboard_reply_content;
	}

	public Date getLocationboard_reply_writedate() {
		return locationboard_reply_writedate;
	}

	public void setLocationboard_reply_writedate(Date locationboard_reply_writedate) {
		this.locationboard_reply_writedate = locationboard_reply_writedate;
	}
    
}
