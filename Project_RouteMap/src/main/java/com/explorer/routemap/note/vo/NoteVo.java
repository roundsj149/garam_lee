package com.explorer.routemap.note.vo;

import java.util.Date;

public class NoteVo {
	private int note_no;
	private int send_member_no;
	private String send_member_id;
	private int recv_member_no;
	private String recv_member_id;
	private String note_title;
	private String note_content;
	private Date note_send_date;
	private Date note_read_date;
	private String note_send_delete;
	private String note_recv_delete;
	public NoteVo() {
		super();
	}
	public NoteVo(int note_no, int send_member_no, String send_member_id, int recv_member_no, String recv_member_id,
			String note_title, String note_content, Date note_send_date, Date note_read_date, String note_send_delete,
			String note_recv_delete) {
		super();
		this.note_no = note_no;
		this.send_member_no = send_member_no;
		this.send_member_id = send_member_id;
		this.recv_member_no = recv_member_no;
		this.recv_member_id = recv_member_id;
		this.note_title = note_title;
		this.note_content = note_content;
		this.note_send_date = note_send_date;
		this.note_read_date = note_read_date;
		this.note_send_delete = note_send_delete;
		this.note_recv_delete = note_recv_delete;
	}
	public int getNote_no() {
		return note_no;
	}
	public void setNote_no(int note_no) {
		this.note_no = note_no;
	}
	public int getSend_member_no() {
		return send_member_no;
	}
	public void setSend_member_no(int send_member_no) {
		this.send_member_no = send_member_no;
	}
	public String getSend_member_id() {
		return send_member_id;
	}
	public void setSend_member_id(String send_member_id) {
		this.send_member_id = send_member_id;
	}
	public int getRecv_member_no() {
		return recv_member_no;
	}
	public void setRecv_member_no(int recv_member_no) {
		this.recv_member_no = recv_member_no;
	}
	public String getRecv_member_id() {
		return recv_member_id;
	}
	public void setRecv_member_id(String recv_member_id) {
		this.recv_member_id = recv_member_id;
	}
	public String getNote_title() {
		return note_title;
	}
	public void setNote_title(String note_title) {
		this.note_title = note_title;
	}
	public String getNote_content() {
		return note_content;
	}
	public void setNote_content(String note_content) {
		this.note_content = note_content;
	}
	public Date getNote_send_date() {
		return note_send_date;
	}
	public void setNote_send_date(Date note_send_date) {
		this.note_send_date = note_send_date;
	}
	public Date getNote_read_date() {
		return note_read_date;
	}
	public void setNote_read_date(Date note_read_date) {
		this.note_read_date = note_read_date;
	}
	public String getNote_send_delete() {
		return note_send_delete;
	}
	public void setNote_send_delete(String note_send_delete) {
		this.note_send_delete = note_send_delete;
	}
	public String getNote_recv_delete() {
		return note_recv_delete;
	}
	public void setNote_recv_delete(String note_recv_delete) {
		this.note_recv_delete = note_recv_delete;
	}
	
}
    
