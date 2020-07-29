package com.explorer.routemap.uploadfile.vo;

import java.util.Date;

public class PopchatUploadFileVo {

	private int popchat_file_no;
	private int popchat_no;
	private String popchat_file_originalname;
	private String popchat_file_link_path;
	private String popchat_file_real_path;
	private Date popchat_file_upload_date;
	
	public PopchatUploadFileVo() {
		super();
	}

	public int getPopchat_file_no() {
		return popchat_file_no;
	}

	public void setPopchat_file_no(int popchat_file_no) {
		this.popchat_file_no = popchat_file_no;
	}

	public int getPopchat_no() {
		return popchat_no;
	}

	public void setPopchat_no(int popchat_no) {
		this.popchat_no = popchat_no;
	}

	public String getPopchat_file_originalname() {
		return popchat_file_originalname;
	}

	public void setPopchat_file_originalname(String popchat_file_originalname) {
		this.popchat_file_originalname = popchat_file_originalname;
	}

	public String getPopchat_file_link_path() {
		return popchat_file_link_path;
	}

	public void setPopchat_file_link_path(String popchat_file_link_path) {
		this.popchat_file_link_path = popchat_file_link_path;
	}

	public String getPopchat_file_real_path() {
		return popchat_file_real_path;
	}

	public void setPopchat_file_real_path(String popchat_file_real_path) {
		this.popchat_file_real_path = popchat_file_real_path;
	}

	public Date getPopchat_file_upload_date() {
		return popchat_file_upload_date;
	}

	public void setPopchat_file_upload_date(Date popchat_file_upload_date) {
		this.popchat_file_upload_date = popchat_file_upload_date;
	}
	
}
