package com.explorer.routemap.report.vo;

public class ReportContentCategoryVo {

	private int report_category_no;
    private String report_category_name;
    
	public ReportContentCategoryVo() {
		super();
	}

	public ReportContentCategoryVo(int report_category_no, String report_category_name) {
		super();
		this.report_category_no = report_category_no;
		this.report_category_name = report_category_name;
	}

	public int getReport_category_no() {
		return report_category_no;
	}

	public void setReport_category_no(int report_category_no) {
		this.report_category_no = report_category_no;
	}

	public String getReport_category_name() {
		return report_category_name;
	}

	public void setReport_category_name(String report_category_name) {
		this.report_category_name = report_category_name;
	}
}
