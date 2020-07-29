package com.explorer.routemap.board.mapper;

import java.util.*;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface LocationboardSQLMapper {

	
	//public List<Map<String , String>> test();
	
	// 게시물 번호 생성(글쓰기와 동시에 파일 업로드를 위해 생성했음)
	public int createLocationboardKey();

	// 여행지/맛집 글쓰기
	public void insert(LocationboardVo locationboardVo);

	// 게시물 읽기
	public LocationboardVo selectByLocationboardNo(int locationboard_no);

	// 게시물 수정
	public void update(LocationboardVo locationboardVo);

	// 게시글 조회수
	public int updateReadcount(int locationboard_no);

	// 좋아요 수 많은 글 3건 선택
	public int[] selectMaxLikeLocationboardByNo();

	// 최신글 3건 선택
	public int selectNewLocationboardByNo();

	// 전체 글 읽기
	public List<LocationboardVo> selectAllBoardList(int currPage);

	// 게시물 제목 검색
	public List<LocationboardVo> selectByTitle(@Param("search_word") String locationboard_title,
			@Param("currPage") int currPage);

	// 지역별 게시물 리스트
	public List<LocationboardVo> selectByProvinceCategoryNo(@Param("currPage") int currPage,
			@Param("province_category_no") int[] province_category_no);

	// 글 검색 처리
	public List<LocationboardVo> selectBySearch(@Param("currPage") int currPage,
			@Param("province_category_no") int[] province_category_no, @Param("select_search_no") int select_search_no,
			@Param("search_word") String search_word, @Param("select_cost") int[] select_cost,
			@Param("select_type") int[] select_type);

	// 전체 게시글 수 처리
	public int selectByLocationboardAllCount();

	// 검색 게시글 수 처리
	public int selectByLocationboardSearchCount(@Param("province_category_no") int[] province_category_no,
			@Param("select_search_no") int select_search_no, @Param("search_word") String search_word);

	// 게시물 삭제
	public void deleteByLocationboardNo(int locationboard_no);
	
	// 게시물 글읽기 ajaxㅜㅜ - 페이징있음
	public List<LocationboardVo> selectAllWithCondition(Map<String,Object> map);
	
	// 게시물 글읽기 ajaxㅜㅜ - 페이징 없음
	public int countAllWithCondition(Map<String,Object> map2);
	
	// 좋아요 순 정렬을 위한 locationboard_no 추출
	public int[] likeAlign();
	
	// 게시글 번호로 글쓴이 뽑아오기
	public int selectMemberNoByLocationboardNo(int locationboard_no);

	// 게시글 댓글 번호로 댓글 작성자 뽑아오기
	public int selectMemberNoByLocationboardReplyNo(int locatinoboard_reply_no);
	
//	// 좋아요 내가 하는건지 확인
//	public int selectLikeMemberNoByLocationboardNo(@Param("locationboard_no")int locationboard_no, @Param("member_no")int member_no);
	
}
