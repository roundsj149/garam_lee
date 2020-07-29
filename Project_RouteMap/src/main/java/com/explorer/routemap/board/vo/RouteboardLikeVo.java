package com.explorer.routemap.board.vo;

public class RouteboardLikeVo {

	private int routeboard_like_no;
	private int member_no;
	private int routeboard_no;
	
	public RouteboardLikeVo() {
		super();
	}

	public RouteboardLikeVo(int routeboard_like_no, int member_no, int routeboard_no) {
		super();
		this.routeboard_like_no = routeboard_like_no;
		this.member_no = member_no;
		this.routeboard_no = routeboard_no;
	}

	public int getRouteboard_like_no() {
		return routeboard_like_no;
	}

	public void setRouteboard_like_no(int routeboard_like_no) {
		this.routeboard_like_no = routeboard_like_no;
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
}
