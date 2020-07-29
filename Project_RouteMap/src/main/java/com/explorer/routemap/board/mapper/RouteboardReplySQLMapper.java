package com.explorer.routemap.board.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface RouteboardReplySQLMapper {

	// 루트 공유 - 댓글 쓰기
	public void insert(@Param("member_no") int member_no, @Param("routeboard_no") int routeboard_no,
			@Param("routeboard_reply_content") String routeboard_reply_content);

	// 루트공유 - 댓글 불러오기
	public List<RouteboardReplyVo> selectByRouteboardNo(int routeboard_no);

	// 루트 공유 - 댓글 삭제
	public void delete(int routeboard_reply_no);

	// 루트 공유 - 댓글 수정
	public void update(@Param("routeboard_reply_content") String routeboard_reply_content,
			@Param("routeboard_reply_no") int routeboard_reply_no);

	// 루트 공유 댓글 삭제 - 루트공유 게시물 삭제 시 같이 삭제
	public void deleteAll(int routeboard_no);

	// 댓글 작성자
	public RouteboardReplyVo selectByReplyNo(int routeboard_reply_no);
}