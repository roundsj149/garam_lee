package com.explorer.routemap.board.vo;

public class RouteboardMyfavoriteVo {
	private int rt_myfavorite_no;
	private int member_no;
	private int routeboard_no;
	
	public RouteboardMyfavoriteVo() {
		super();
	}

	public RouteboardMyfavoriteVo(int rt_myfavorite_no, int member_no, int routeboard_no) {
		super();
		this.rt_myfavorite_no = rt_myfavorite_no;
		this.member_no = member_no;
		this.routeboard_no = routeboard_no;
	}

	public int getRt_myfavorite_no() {
		return rt_myfavorite_no;
	}

	public void setRt_myfavorite_no(int rt_myfavorite_no) {
		this.rt_myfavorite_no = rt_myfavorite_no;
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
