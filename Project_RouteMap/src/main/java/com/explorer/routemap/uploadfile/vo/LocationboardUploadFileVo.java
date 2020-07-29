package com.explorer.routemap.uploadfile.vo;

import java.util.Date;

public class LocationboardUploadFileVo {
	private int locationboard_file_no;
	private int locationboard_no;
	private String locationboard_originalname;
	private String locationboard_file_link_path;
	private String locationboard_file_real_path;
	private Date locationboard_file_upload_date;
	
	public LocationboardUploadFileVo() {
		super();
	}

	public LocationboardUploadFileVo(int locationboard_file_no, int locationboard_no, String locationboard_originalname,
			String locationboard_file_link_path, String locationboard_file_real_path,
			Date locationboard_file_upload_date) {
		super();
		this.locationboard_file_no = locationboard_file_no;
		this.locationboard_no = locationboard_no;
		this.locationboard_originalname = locationboard_originalname;
		this.locationboard_file_link_path = locationboard_file_link_path;
		this.locationboard_file_real_path = locationboard_file_real_path;
		this.locationboard_file_upload_date = locationboard_file_upload_date;
	}

	public int getLocationboard_file_no() {
		return locationboard_file_no;
	}

	public void setLocationboard_file_no(int locationboard_file_no) {
		this.locationboard_file_no = locationboard_file_no;
	}

	public int getLocationboard_no() {
		return locationboard_no;
	}

	public void setLocationboard_no(int locationboard_no) {
		this.locationboard_no = locationboard_no;
	}

	public String getLocationboard_originalname() {
		return locationboard_originalname;
	}

	public void setLocationboard_originalname(String locationboard_originalname) {
		this.locationboard_originalname = locationboard_originalname;
	}

	public String getLocationboard_file_link_path() {
		return locationboard_file_link_path;
	}

	public void setLocationboard_file_link_path(String locationboard_file_link_path) {
		this.locationboard_file_link_path = locationboard_file_link_path;
	}

	public String getLocationboard_file_real_path() {
		return locationboard_file_real_path;
	}

	public void setLocationboard_file_real_path(String locationboard_file_real_path) {
		this.locationboard_file_real_path = locationboard_file_real_path;
	}

	public Date getLocationboard_file_upload_date() {
		return locationboard_file_upload_date;
	}

	public void setLocationboard_file_upload_date(Date locationboard_file_upload_date) {
		this.locationboard_file_upload_date = locationboard_file_upload_date;
	}

}
