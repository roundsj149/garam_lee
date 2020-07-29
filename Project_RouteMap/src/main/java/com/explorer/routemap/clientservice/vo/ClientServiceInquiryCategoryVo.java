    package com.explorer.routemap.clientservice.vo;

public class ClientServiceInquiryCategoryVo {

	private int inquiry_category_no;
	private String inquiry_category_name;
	
	public ClientServiceInquiryCategoryVo() {
		super();
	}
	public ClientServiceInquiryCategoryVo(int inquiry_category_no, String inquiry_category_name) {
		super();
		this.inquiry_category_no = inquiry_category_no;
		this.inquiry_category_name = inquiry_category_name;
	}
	public int getInquiry_category_no() {
		return inquiry_category_no;
	}
	public void setInquiry_category_no(int inquiry_category_no) {
		this.inquiry_category_no = inquiry_category_no;
	}
	public String getInquiry_category_name() {
		return inquiry_category_name;
	}
	public void setInquiry_category_name(String inquiry_category_name) {
		this.inquiry_category_name = inquiry_category_name;
	}

}

    
