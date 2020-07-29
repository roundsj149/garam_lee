package com.explorer.routemap.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.LocationboardVo;
import com.explorer.routemap.board.vo.RouteboardVo;

public interface RouteboardSQLMapper {

	// 전체 글 출력
	public List<RouteboardVo> selectByTitle(@Param("currPage") int currPage, @Param("search_word") String search_word);

	// 루트공유 글읽기
	public RouteboardVo selectRouteByRouteboardNo(int routeboard_no);

	// 루트공유 조회수
	public void updateReadcount(int routeboard_no);

	// 루트공유 글삭제
	public void delete(int routeboard_no);

	// 좋아요 수 많은 글 3건 선택
	public int[] selectMaxLikeRouteboardByNo();

	// 루트 게시글 번호 생성
	public int createRouteboardKey();

	// 루트글 쓰기
	public void insert(RouteboardVo routeboardVo);

	// 루트글쓰기 - 소요시간, 비용, 카테고리 갱신
	public void updateCostAndTime(RouteboardVo routeboardVo);

	// 게시물 글읽기 ajaxㅜㅜ - 페이징있음
	public List<RouteboardVo> selectAllWithCondition(Map<String, Object> map);

	// 게시물 글읽기 ajaxㅜㅜ - 페이징 없음
	public int countAllWithCondition(Map<String, Object> map2);

	// 루트글 수정
	public void update(RouteboardVo routeboardVo);

	// 좋아요 순 정렬을 위한 routeboard_no 추출
	public int[] likeAlign();

	// 게시글 번호로 글쓴이 뽑아오기
	public int selectMemberNoByRouteboardNo(int routeboard_no);

	// 게시글 댓글 번호로 댓글 작성자 뽑아오기
	public int selectMemberNoByRouteboardReplyNo(int routeboard_reply_no);

}