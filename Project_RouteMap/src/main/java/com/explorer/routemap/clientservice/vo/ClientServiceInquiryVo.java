    package com.explorer.routemap.clientservice.vo;

import java.util.Date;

public class ClientServiceInquiryVo {

	private int inquiry_row;
	private int inquiry_no;
	private int member_no;
	private int inquiry_category_no;
	private String inquiry_title;
	private String inquiry_content;
	private Date inquiry_writedate;
	private String inquiry_answer_key;
	
	public ClientServiceInquiryVo() {
		super();
	}

	public ClientServiceInquiryVo(int inquiry_row, int inquiry_no, int member_no, int inquiry_category_no,
			String inquiry_title, String inquiry_content, Date inquiry_writedate, String inquiry_answer_key) {
		super();
		this.inquiry_row = inquiry_row;
		this.inquiry_no = inquiry_no;
		this.member_no = member_no;
		this.inquiry_category_no = inquiry_category_no;
		this.inquiry_title = inquiry_title;
		this.inquiry_content = inquiry_content;
		this.inquiry_writedate = inquiry_writedate;
		this.inquiry_answer_key = inquiry_answer_key;
	}

	public int getInquiry_row() {
		return inquiry_row;
	}

	public void setInquiry_row(int inquiry_row) {
		this.inquiry_row = inquiry_row;
	}

	public int getInquiry_no() {
		return inquiry_no;
	}

	public void setInquiry_no(int inquiry_no) {
		this.inquiry_no = inquiry_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getInquiry_category_no() {
		return inquiry_category_no;
	}

	public void setInquiry_category_no(int inquiry_category_no) {
		this.inquiry_category_no = inquiry_category_no;
	}

	public String getInquiry_title() {
		return inquiry_title;
	}

	public void setInquiry_title(String inquiry_title) {
		this.inquiry_title = inquiry_title;
	}

	public String getInquiry_content() {
		return inquiry_content;
	}

	public void setInquiry_content(String inquiry_content) {
		this.inquiry_content = inquiry_content;
	}

	public Date getInquiry_writedate() {
		return inquiry_writedate;
	}

	public void setInquiry_writedate(Date inquiry_writedate) {
		this.inquiry_writedate = inquiry_writedate;
	}

	public String getInquiry_answer_key() {
		return inquiry_answer_key;
	}

	public void setInquiry_answer_key(String inquiry_answer_key) {
		this.inquiry_answer_key = inquiry_answer_key;
	}

	
}

    
