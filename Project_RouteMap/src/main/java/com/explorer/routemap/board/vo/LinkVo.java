package com.explorer.routemap.board.vo;

public class LinkVo {
	private int link_no;
	private int locationboard_no;
	private int routeboard_no;
	private int link_cost;
	private int link_time;
	private int link_order;
	
	public LinkVo() {
		super();
	}

	public LinkVo(int link_no, int locationboard_no, int routeboard_no, int link_cost, int link_time, int link_order) {
		super();
		this.link_no = link_no;
		this.locationboard_no = locationboard_no;
		this.routeboard_no = routeboard_no;
		this.link_cost = link_cost;
		this.link_time = link_time;
		this.link_order = link_order;
	}

	public int getLink_no() {
		return link_no;
	}

	public void setLink_no(int link_no) {
		this.link_no = link_no;
	}

	public int getLocationboard_no() {
		return locationboard_no;
	}

	public void setLocationboard_no(int locationboard_no) {
		this.locationboard_no = locationboard_no;
	}

	public int getRouteboard_no() {
		return routeboard_no;
	}

	public void setRouteboard_no(int routeboard_no) {
		this.routeboard_no = routeboard_no;
	}

	public int getLink_cost() {
		return link_cost;
	}

	public void setLink_cost(int link_cost) {
		this.link_cost = link_cost;
	}

	public int getLink_time() {
		return link_time;
	}

	public void setLink_time(int link_time) {
		this.link_time = link_time;
	}

	public int getLink_order() {
		return link_order;
	}

	public void setLink_order(int link_order) {
		this.link_order = link_order;
	}
}