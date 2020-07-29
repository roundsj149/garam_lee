package com.explorer.routemap.report.vo;

import java.util.Date;

public class ReportMemberVo {
	
	private int report_no;
    private int reporting_member_no;
    private int reported_member_no;
    private int report_category_no;
    private Date report_time;
    private String report_status;
    
	public ReportMemberVo() {
		super();
	}

	public ReportMemberVo(int report_no, int reporting_member_no, int reported_member_no, int report_category_no,
			Date report_time, String report_status) {
		super();
		this.report_no = report_no;
		this.reporting_member_no = reporting_member_no;
		this.reported_member_no = reported_member_no;
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

	public int getReporting_member_no() {
		return reporting_member_no;
	}

	public void setReporting_member_no(int reporting_member_no) {
		this.reporting_member_no = reporting_member_no;
	}

	public int getReported_member_no() {
		return reported_member_no;
	}

	public void setReported_member_no(int reported_member_no) {
		this.reported_member_no = reported_member_no;
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
