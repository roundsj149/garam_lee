package com.explorer.routemap.myfar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface MyRouteSQLMapper {

	// 내가 쓴 글 - 전체
	public List<RouteboardVo> selectByMemberNo(@Param("member_no")int member_no, @Param("currPage")int currPage);
	
	// 나의 루트 새로 만들기
	public void insertMyRoute(@Param("member_no")int member_no, @Param("routeboard_title")String routeboard_title);
}
