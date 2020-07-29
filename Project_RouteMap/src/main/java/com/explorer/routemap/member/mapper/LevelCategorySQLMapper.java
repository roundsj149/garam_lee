package com.explorer.routemap.member.mapper;

import com.explorer.routemap.member.vo.LevelCategoryVo;

public interface LevelCategorySQLMapper {
   
   // 유제 레벨 확인
   public LevelCategoryVo selectByExp(int member_levelexp);
   
}