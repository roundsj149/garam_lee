package com.explorer.routemap.board.vo;

public class TypeCategoryVo {
	private int type_category_no;
    private String type_category_name;
    
	public TypeCategoryVo() {
		super();
	}

	public TypeCategoryVo(int type_category_no, String type_category_name) {
		super();
		this.type_category_no = type_category_no;
		this.type_category_name = type_category_name;
	}

	public int getType_category_no() {
		return type_category_no;
	}

	public void setType_category_no(int type_category_no) {
		this.type_category_no = type_category_no;
	}

	public String getType_category_name() {
		return type_category_name;
	}

	public void setType_category_name(String type_category_name) {
		this.type_category_name = type_category_name;
	}
}
