    package com.explorer.routemap.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.FreeboardVo;

public interface FreeboardSQLMapper {

	// 게시물 번호 생성
	public int createFreeboardKey();

	// 자유게시판 글쓰기 //
	public void insert(FreeboardVo freeboardVo);

	// 게시물 읽기
	public FreeboardVo selectByFreeboardNo(int freeboard_no);

	// 게시글 조회수
	public int freeboardReadcount(int freeboard_no);

	// 추천글 선택
	public int selectRecommendFreeboardByNo();

	// 전체글 목록 및 페이징 처리
	public List<FreeboardVo> selectAllBoardList(int currPage);

	// 게시물 제목 검색 //
	public List<FreeboardVo> selectByTitle(@Param("word") String freeboard_title, @Param("currPage") int currPage);

	// 게시물 글쓴이 검색
	public List<FreeboardVo> selectBycontent(int member_no);

	// 글지우기
	public void deleteContent(int freeboard_no);

	// 글 수정하기
	public void update(FreeboardVo freeboardVo);

	// 총 글 갯수
	public int selectAllCount();

	// 총 글 갯수; 제목으로 검색된
	public int selectByTitleCount(String title);

}

    
