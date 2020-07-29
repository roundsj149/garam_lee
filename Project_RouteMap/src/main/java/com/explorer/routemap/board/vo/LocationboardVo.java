package com.explorer.routemap.board.vo;

import java.util.Date;

public class LocationboardVo {
	private int locationboard_no;
	private int member_no;
	private int province_category_no;
	private int location_category_no;
	private int cost_category_no;
	private int type_category_no;
	private int time_category_no;
	private int mood_category_no;
	private String locationboard_title;
	private String locationboard_content;
	private int locationboard_readcount;
	private int locationboard_cost;
	private int locationboard_time;
	private String locationboard_storename;
	private String locationboard_storeaddress;
	private String locationboard_coordinate;
	private String locationboard_storenumber;
	private Date locationboard_writedate;
	
	public LocationboardVo() {
		super();
	}

	public LocationboardVo(int locationboard_no, int member_no, int province_category_no, int location_category_no,
			int cost_category_no, int type_category_no, int time_category_no, int mood_category_no,
			String locationboard_title, String locationboard_content, int locationboard_readcount,
			int locationboard_cost, int locationboard_time, String locationboard_storename,
			String locationboard_storeaddress, String locationboard_coordinate, String locationboard_storenumber,
			Date locationboard_writedate) {
		super();
		this.locationboard_no = locationboard_no;
		this.member_no = member_no;
		this.province_category_no = province_category_no;
		this.location_category_no = location_category_no;
		this.cost_category_no = cost_category_no;
		this.type_category_no = type_category_no;
		this.time_category_no = time_category_no;
		this.mood_category_no = mood_category_no;
		this.locationboard_title = locationboard_title;
		this.locationboard_content = locationboard_content;
		this.locationboard_readcount = locationboard_readcount;
		this.locationboard_cost = locationboard_cost;
		this.locationboard_time = locationboard_time;
		this.locationboard_storename = locationboard_storename;
		this.locationboard_storeaddress = locationboard_storeaddress;
		this.locationboard_coordinate = locationboard_coordinate;
		this.locationboard_storenumber = locationboard_storenumber;
		this.locationboard_writedate = locationboard_writedate;
	}

	public int getLocationboard_no() {
		return locationboard_no;
	}

	public void setLocationboard_no(int locationboard_no) {
		this.locationboard_no = locationboard_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getProvince_category_no() {
		return province_category_no;
	}

	public void setProvince_category_no(int province_category_no) {
		this.province_category_no = province_category_no;
	}

	public int getLocation_category_no() {
		return location_category_no;
	}

	public void setLocation_category_no(int location_category_no) {
		this.location_category_no = location_category_no;
	}

	public int getCost_category_no() {
		return cost_category_no;
	}

	public void setCost_category_no(int cost_category_no) {
		this.cost_category_no = cost_category_no;
	}

	public int getType_category_no() {
		return type_category_no;
	}

	public void setType_category_no(int type_category_no) {
		this.type_category_no = type_category_no;
	}

	public int getTime_category_no() {
		return time_category_no;
	}

	public void setTime_category_no(int time_category_no) {
		this.time_category_no = time_category_no;
	}

	public int getMood_category_no() {
		return mood_category_no;
	}

	public void setMood_category_no(int mood_category_no) {
		this.mood_category_no = mood_category_no;
	}

	public String getLocationboard_title() {
		return locationboard_title;
	}

	public void setLocationboard_title(String locationboard_title) {
		this.locationboard_title = locationboard_title;
	}

	public String getLocationboard_content() {
		return locationboard_content;
	}

	public void setLocationboard_content(String locationboard_content) {
		this.locationboard_content = locationboard_content;
	}

	public int getLocationboard_readcount() {
		return locationboard_readcount;
	}

	public void setLocationboard_readcount(int locationboard_readcount) {
		this.locationboard_readcount = locationboard_readcount;
	}

	public int getLocationboard_cost() {
		return locationboard_cost;
	}

	public void setLocationboard_cost(int locationboard_cost) {
		this.locationboard_cost = locationboard_cost;
	}

	public int getLocationboard_time() {
		return locationboard_time;
	}

	public void setLocationboard_time(int locationboard_time) {
		this.locationboard_time = locationboard_time;
	}

	public String getLocationboard_storename() {
		return locationboard_storename;
	}

	public void setLocationboard_storename(String locationboard_storename) {
		this.locationboard_storename = locationboard_storename;
	}

	public String getLocationboard_storeaddress() {
		return locationboard_storeaddress;
	}

	public void setLocationboard_storeaddress(String locationboard_storeaddress) {
		this.locationboard_storeaddress = locationboard_storeaddress;
	}

	public String getLocationboard_coordinate() {
		return locationboard_coordinate;
	}

	public void setLocationboard_coordinate(String locationboard_coordinate) {
		this.locationboard_coordinate = locationboard_coordinate;
	}

	public String getLocationboard_storenumber() {
		return locationboard_storenumber;
	}

	public void setLocationboard_storenumber(String locationboard_storenumber) {
		this.locationboard_storenumber = locationboard_storenumber;
	}

	public Date getLocationboard_writedate() {
		return locationboard_writedate;
	}

	public void setLocationboard_writedate(Date locationboard_writedate) {
		this.locationboard_writedate = locationboard_writedate;
	}

}