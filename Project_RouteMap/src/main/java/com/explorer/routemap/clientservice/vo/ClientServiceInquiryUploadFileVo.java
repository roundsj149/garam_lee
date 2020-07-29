    package com.explorer.routemap.clientservice.vo;

import java.util.Date;

public class ClientServiceInquiryUploadFileVo {

	private int inquiry_file_no;
	private int inquiry_no;
	private String inquiry_file_link_path;
	private String inquiry_file_real_path;
	private Date inquiry_file_upload_date;
	
	public ClientServiceInquiryUploadFileVo() {
		super();
	}

	public ClientServiceInquiryUploadFileVo(int inquiry_file_no, int inquiry_no, String inquiry_file_link_path,
			String inquiry_file_real_path, Date inquiry_file_upload_date) {
		super();
		this.inquiry_file_no = inquiry_file_no;
		this.inquiry_no = inquiry_no;
		this.inquiry_file_link_path = inquiry_file_link_path;
		this.inquiry_file_real_path = inquiry_file_real_path;
		this.inquiry_file_upload_date = inquiry_file_upload_date;
	}

	public int getInquiry_file_no() {
		return inquiry_file_no;
	}

	public void setInquiry_file_no(int inquiry_file_no) {
		this.inquiry_file_no = inquiry_file_no;
	}

	public int getInquiry_no() {
		return inquiry_no;
	}

	public void setInquiry_no(int inquiry_no) {
		this.inquiry_no = inquiry_no;
	}

	public String getInquiry_file_link_path() {
		return inquiry_file_link_path;
	}

	public void setInquiry_file_link_path(String inquiry_file_link_path) {
		this.inquiry_file_link_path = inquiry_file_link_path;
	}

	public String getInquiry_file_real_path() {
		return inquiry_file_real_path;
	}

	public void setInquiry_file_real_path(String inquiry_file_real_path) {
		this.inquiry_file_real_path = inquiry_file_real_path;
	}

	public Date getInquiry_file_upload_date() {
		return inquiry_file_upload_date;
	}

	public void setInquiry_file_upload_date(Date inquiry_file_upload_date) {
		this.inquiry_file_upload_date = inquiry_file_upload_date;
	}
	
	
}

    
