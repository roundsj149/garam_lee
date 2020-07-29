package com.explorer.routemap.myfar.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface MyLocationSQLMapper {
	
	// 내가 쓴 글 - 전체
	public List<LocationboardVo> selectByMemberNo(@Param("member_no")int member_no, @Param("currPage")int currPage);
	
	// 내가 쓴 글 - 여행지
	public List<LocationboardVo> selectByMemberNoAndCategoryNo(@Param("member_no")int member_no, @Param("location_category_no")int location_category_no, @Param("currPage")int currPage);
}
