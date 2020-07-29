package com.explorer.routemap.board.vo;

public class ProvinceCategoryVo {
	private int province_category_no;
    private String province_category_name;
    
	public ProvinceCategoryVo() {
		super();
	}

	public ProvinceCategoryVo(int province_category_no, String province_category_name) {
		super();
		this.province_category_no = province_category_no;
		this.province_category_name = province_category_name;
	}

	public int getProvince_category_no() {
		return province_category_no;
	}

	public void setProvince_category_no(int province_category_no) {
		this.province_category_no = province_category_no;
	}

	public String getProvince_category_name() {
		return province_category_name;
	}

	public void setProvince_category_name(String province_category_name) {
		this.province_category_name = province_category_name;
	}
}
