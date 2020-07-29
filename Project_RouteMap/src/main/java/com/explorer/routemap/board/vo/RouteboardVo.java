package com.explorer.routemap.board.vo;

import java.util.Date;

public class RouteboardVo {

	private int routeboard_no;
	private int member_no;
	private int time_category_no;
	private int cost_category_no;
	private String routeboard_title;
	private String routeboard_content;
	private int routeboard_readcount;
	private int routeboard_cost;
	private int routeboard_time;
	private String routeboard_share;
	private Date routeboard_writedate;
	
	public RouteboardVo() {
		super();
	}

	public RouteboardVo(int routeboard_no, int member_no, int time_category_no, int cost_category_no,
			String routeboard_title, String routeboard_content, int routeboard_readcount, int routeboard_cost,
			int routeboard_time, String routeboard_share, Date routeboard_writedate) {
		super();
		this.routeboard_no = routeboard_no;
		this.member_no = member_no;
		this.time_category_no = time_category_no;
		this.cost_category_no = cost_category_no;
		this.routeboard_title = routeboard_title;
		this.routeboard_content = routeboard_content;
		this.routeboard_readcount = routeboard_readcount;
		this.routeboard_cost = routeboard_cost;
		this.routeboard_time = routeboard_time;
		this.routeboard_share = routeboard_share;
		this.routeboard_writedate = routeboard_writedate;
	}

	public int getRouteboard_no() {
		return routeboard_no;
	}

	public void setRouteboard_no(int routeboard_no) {
		this.routeboard_no = routeboard_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getTime_category_no() {
		return time_category_no;
	}

	public void setTime_category_no(int time_category_no) {
		this.time_category_no = time_category_no;
	}

	public int getCost_category_no() {
		return cost_category_no;
	}

	public void setCost_category_no(int cost_category_no) {
		this.cost_category_no = cost_category_no;
	}

	public String getRouteboard_title() {
		return routeboard_title;
	}

	public void setRouteboard_title(String routeboard_title) {
		this.routeboard_title = routeboard_title;
	}

	public String getRouteboard_content() {
		return routeboard_content;
	}

	public void setRouteboard_content(String routeboard_content) {
		this.routeboard_content = routeboard_content;
	}

	public int getRouteboard_readcount() {
		return routeboard_readcount;
	}

	public void setRouteboard_readcount(int routeboard_readcount) {
		this.routeboard_readcount = routeboard_readcount;
	}

	public int getRouteboard_cost() {
		return routeboard_cost;
	}

	public void setRouteboard_cost(int routeboard_cost) {
		this.routeboard_cost = routeboard_cost;
	}

	public int getRouteboard_time() {
		return routeboard_time;
	}

	public void setRouteboard_time(int routeboard_time) {
		this.routeboard_time = routeboard_time;
	}

	public String getRouteboard_share() {
		return routeboard_share;
	}

	public void setRouteboard_share(String routeboard_share) {
		this.routeboard_share = routeboard_share;
	}

	public Date getRouteboard_writedate() {
		return routeboard_writedate;
	}

	public void setRouteboard_writedate(Date routeboard_writedate) {
		this.routeboard_writedate = routeboard_writedate;
	}

}