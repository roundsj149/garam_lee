package com.explorer.routemap.board.mapper;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.RouteboardMyfavoriteVo;

public interface RouteboardMyfavoriteSQLMapper {

	
	public RouteboardMyfavoriteVo selectMyfavoriteAllCheck(@Param("member_no") int member_no, @Param("routeboard_no") int routeboard_no);
	
	public void insertMyfavoriteCheck(@Param("member_no") int member_no, @Param("routeboard_no") int routeboard_no);
	
	public void deleteMyfavoriteCheck(@Param("member_no") int member_no, @Param("routeboard_no") int routeboard_no);
	
	public void deleteMyfavorite(int routeboard_no);

}
