package com.explorer.routemap.board.vo;

public class LocationboardLikeVo {

	private int locationboard_like_no;
	private int member_no;
	private int locationboard_no;
	
	public LocationboardLikeVo() {
		super();
	}

	public LocationboardLikeVo(int locationboard_like_no, int member_no, int locationboard_no) {
		super();
		this.locationboard_like_no = locationboard_like_no;
		this.member_no = member_no;
		this.locationboard_no = locationboard_no;
	}

	public int getLocationboard_like_no() {
		return locationboard_like_no;
	}

	public void setLocationboard_like_no(int locationboard_like_no) {
		this.locationboard_like_no = locationboard_like_no;
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
}
