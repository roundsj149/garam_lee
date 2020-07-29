package com.explorer.routemap.member.vo;

public class LevelCategoryVo {

    private int level_category_no;
    private int minimum_exp;
    private int maximum_exp;
    
   public LevelCategoryVo() {
      super();
   }

   public int getLevel_category_no() {
      return level_category_no;
   }

   public void setLevel_category_no(int level_category_no) {
      this.level_category_no = level_category_no;
   }

   public int getMinimum_exp() {
      return minimum_exp;
   }

   public void setMinimum_exp(int minimum_exp) {
      this.minimum_exp = minimum_exp;
   }

   public int getMaximum_exp() {
      return maximum_exp;
   }

   public void setMaximum_exp(int maximum_exp) {
      this.maximum_exp = maximum_exp;
   }
    
}