package com.explorer.routemap.uploadfile.vo;

import java.util.Date;

public class FreeboardUploadFileVo {

	private int file_no;
	private int freeboard_no;
	private String file_link_path;
	private String file_real_path;
	private Date file_upload_date;

	public FreeboardUploadFileVo() {
		super();
	}

	public FreeboardUploadFileVo(int file_no, int freeboard_no, String file_link_path, String file_real_path,
			Date file_upload_date) {
		super();
		this.file_no = file_no;
		this.freeboard_no = freeboard_no;
		this.file_link_path = file_link_path;
		this.file_real_path = file_real_path;
		this.file_upload_date = file_upload_date;
	}

	public int getFile_no() {
		return file_no;
	}

	public void setFile_no(int file_no) {
		this.file_no = file_no;
	}

	public int getFreeboard_no() {
		return freeboard_no;
	}

	public void setFreeboard_no(int freeboard_no) {
		this.freeboard_no = freeboard_no;
	}

	public String getFile_link_path() {
		return file_link_path;
	}

	public void setFile_link_path(String file_link_path) {
		this.file_link_path = file_link_path;
	}

	public String getFile_real_path() {
		return file_real_path;
	}

	public void setFile_real_path(String file_real_path) {
		this.file_real_path = file_real_path;
	}

	public Date getFile_upload_date() {
		return file_upload_date;
	}

	public void setFile_upload_date(Date file_upload_date) {
		this.file_upload_date = file_upload_date;
	}

	
}
