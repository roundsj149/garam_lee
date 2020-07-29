package com.explorer.routemap.board.vo;

public class LocationCategoryVo {
	 private int location_category_no;
	    private String location_category_name;
	    
		public LocationCategoryVo() {
			super();
		}

		public LocationCategoryVo(int location_category_no, String location_category_name) {
			super();
			this.location_category_no = location_category_no;
			this.location_category_name = location_category_name;
		}

		public int getLocation_category_no() {
			return location_category_no;
		}

		public void setLocation_category_no(int location_category_no) {
			this.location_category_no = location_category_no;
		}

		public String getLocation_category_name() {
			return location_category_name;
		}

		public void setLocation_category_name(String location_category_name) {
			this.location_category_name = location_category_name;
		}
}
