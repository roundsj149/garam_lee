package com.explorer.routemap.myfar.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface MyFavoriteLocationSQLMapper {
   
   // 나의 관심 장소 - 전체
   public List<LocationboardVo> selectByMemberNo(@Param("member_no")int member_no, @Param("currPage")int currPage);
   
   // 나의 관심 장소 - 여행지 or 맛집
   public LocationboardVo selectByLocationboardNoAndCategoryNo(@Param("locationboard_no")int locationboard_no, @Param("location_category_no")int location_category_no, @Param("currPage")int currPage);

}