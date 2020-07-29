package com.explorer.routemap.board.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface LocationboardLikeSQLMapper {
	
	// 게시물 좋아요 했는지 안했는지 확인
	public LocationboardLikeVo selectByLocationboardNoAndMemberNo(@Param("member_no") int member_no, @Param("locationboard_no") int locationboard_no);
	
	// 좋아요
	public void insertMemberNoAndLocationNo(@Param("member_no") int member_no, @Param("locationboard_no") int locationboard_no);
	
	// 좋아요 취소
	public void deleteMemberNo(@Param("member_no") int member_no, @Param("locationboard_no") int locationboard_no);
	
	// 전체 좋아요 
	public List<LocationboardLikeVo> selectAllLike(int member_no);
	
	// 게시물 1건에 대한 좋아요 수
	public int likeCount(int locationboard_no);
	
	// 글 삭제 시 좋아요 테이블에서 삭제
	public void deleteByLocationboardNo(int locationboard_no);
}
