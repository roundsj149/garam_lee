package com.explorer.routemap.report.vo;

import java.util.Date;

public class ReportLocationVo {

	private int report_no;
	private int member_no;
	private int locationboard_no;
	private int report_category_no;
	private Date report_time;
	private String report_status;
	
	public ReportLocationVo() {
		super();
	}

	public ReportLocationVo(int report_no, int member_no, int locationboard_no, int report_category_no,
			Date report_time, String report_status) {
		super();
		this.report_no = report_no;
		this.member_no = member_no;
		this.locationboard_no = locationboard_no;
		this.report_category_no = report_category_no;
		this.report_time = report_time;
		this.report_status = report_status;
	}

	public int getReport_no() {
		return report_no;
	}

	public void setReport_no(int report_no) {
		this.report_no = report_no;
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

	public int getReport_category_no() {
		return report_category_no;
	}

	public void setReport_category_no(int report_category_no) {
		this.report_category_no = report_category_no;
	}

	public Date getReport_time() {
		return report_time;
	}

	public void setReport_time(Date report_time) {
		this.report_time = report_time;
	}

	public String getReport_status() {
		return report_status;
	}

	public void setReport_status(String report_status) {
		this.report_status = report_status;
	}

}
