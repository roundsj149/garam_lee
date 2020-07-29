package com.explorer.routemap.board.vo;

public class MoodCategoryVo {
	 private int mood_category_no;
	    private String mood_category_name;
	    
		public MoodCategoryVo() {
			super();
		}

		public MoodCategoryVo(int mood_category_no, String mood_category_name) {
			super();
			this.mood_category_no = mood_category_no;
			this.mood_category_name = mood_category_name;
		}

		public int getMood_category_no() {
			return mood_category_no;
		}

		public void setMood_category_no(int mood_category_no) {
			this.mood_category_no = mood_category_no;
		}

		public String getMood_category_name() {
			return mood_category_name;
		}

		public void setMood_category_name(String mood_category_name) {
			this.mood_category_name = mood_category_name;
		}
	    
}
