package com.explorer.routemap.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface RouteboardLikeSQLMapper {

	// 게시물 좋아요 했는지 안했는지 확인
	public RouteboardLikeVo selectByRouteboardNoAndMemberNo(@Param("member_no") int member_no, @Param("routeboard_no") int routeboard_no);
	
	// 좋아요
	public void insertMemberNoAndRouteNo(@Param("member_no") int member_no, @Param("routeboard_no") int routeboard_no);
	
	// 좋아요 취소
	public void deleteMemberNo(@Param("member_no") int member_no, @Param("routeboard_no") int routeboard_no);
	
	// 전체 좋아요 
	public List<RouteboardLikeVo> selectAllLike(int member_no);
	
	// 게시물 1건에 대한 좋아요 수
	public int likeCount(int routeboard_no);
	
	// 루트 글 삭제 시 좋아요도 삭제
	public void deleteByRouteboardNo(int routeboard_no);
	
	
	
}
