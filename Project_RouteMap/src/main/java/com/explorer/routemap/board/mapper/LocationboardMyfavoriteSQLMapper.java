package com.explorer.routemap.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface LocationboardMyfavoriteSQLMapper {

	// 관심 장소 게시글 불러오기
	public List<LocationboardMyfavoriteVo> selectByMemberNo(int member_no);

	public LocationboardMyfavoriteVo selectMyfavoriteAllCheck(@Param("member_no") int member_no,
			@Param("board_no") int board_no);

	public void insertMyfavoriteCheck(@Param("member_no") int member_no,
			@Param("locationboard_no") int locationboard_no);

	public void deleteMyfavoriteCheck(@Param("member_no") int member_no,
			@Param("locationboard_no") int locationboard_no);
	
	public void deleteMyfavorite(int locationboard_no);
}