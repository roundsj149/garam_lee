package com.explorer.routemap.admin.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.explorer.routemap.board.vo.LocationCategoryVo;
import com.explorer.routemap.board.vo.LocationboardVo;
import com.explorer.routemap.board.vo.ProvinceCategoryVo;
import com.explorer.routemap.board.vo.RouteboardVo;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqCategoryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceNoticeVo;
import com.explorer.routemap.member.vo.LevelCategoryVo;
import com.explorer.routemap.member.vo.MemberVo;

public interface AdminSQLMapper {

	// 대쉬보드 인기장소 좋아요 글 번호
	public List<Integer> selectLCLikeCount();

	// 대쉬보드 인기장소 좋아요 글 번호
	public List<Integer> selectLCLikeNo();

	// 대쉬보드 인기루트 좋아요 글 번호
	public List<Integer> selectRTLikeNo();

	// 대쉬보드 인기루트 좋아요 글 좋아요수
	public List<Integer> selectRTLikeCount();

	// 여행지 맛집 게시글 총 갯수
	public int selectLocationCount(String time1);

	// 인기장소 지역 5순위
	public List<Integer> selectLcProvCatNo(String time1);

	// 인기장소 지역 5순위 게시글 수
	public List<Integer> selectLcboardCount(String time1);

	// 로그인 기록 데이터
	public void insertLoginData(int member_no);

	// 로그인 횟수 날짜범위
	public int selectLoginWeekCount(@Param("startDay") String startDay, @Param("endDay") String endDay);

	// 해당 날짜 로그인 횟수 전에 널값 방지
	public void insertLoginDataDate(@Param("date") Date date);

	// 해당 날짜 로그인 횟수
	public int selectLoginCountbyDate(@Param("date") String date);

	// 멤버 리스트 조회
	public List<MemberVo> selectMemberList(RowBounds rb, @Param("searchVal") int searchVal,
			@Param("searchWord") String searchWord);

	// 멤버 카운트 조회
	public int selectMemberCount(@Param("searchVal") int searchVal, @Param("searchWord") String searchWord);

	// 레벨 조회
	public LevelCategoryVo selectLelvelByExp(int member_levelexp);

	// 멤버 상태값 변경
	public void updateMemberStatus(@Param("memberNo") int memberNo, @Param("status") String status);

	// 여행지,맛집 리스트 조회
	public List<LocationboardVo> selectLocationList(RowBounds rb, @Param("searchVal") int searchVal,
			@Param("searchWord") String searchWord, @Param("memberNo") String memberNo);

	// 여행지,맛집 카운트 조회
	public int selectLocationListCount(@Param("searchVal") int searchVal, @Param("searchWord") String searchWord,
			@Param("memberNo") String memberNo);

	// 여행지,맛집 카테고리 조회
	public LocationCategoryVo selectLocationCategory(int location_category_no);

	// 여행지,맛집 지역카테고리 조회
	public ProvinceCategoryVo selectProvinceCategory(int province_category_no);

	// 여행지,맛집 좋아요 수 조회
	public int selectLocationLike(int locationboard_no);

	// 작성자 닉네임 조회
	public String selectMember(int member_no);

	// 작성자 번호 조회
	public String selectMemberNo(String searchWord);

	// 루트공유 리스트 조회
	public List<RouteboardVo> selectRouteList(RowBounds rb, @Param("searchVal") int searchVal,
			@Param("searchWord") String searchWord, @Param("memberNo") String memberNo);

	// 루트공유 카운트 조회
	public int selectRouteListCount(@Param("searchVal") int searchVal, @Param("searchWord") String searchWord,
			@Param("memberNo") String memberNo);

	// 루트공유의 첫번째 여행지 번호 조회
	public int selectRouteProvinceNo(int routeboard_no);

	// 루트공유의 첫번쨰 여행지의 프로빈스 조회
	public int selectLocationProvince(int locationboard_no);

	// 루트 공유 좋아요 수 조회
	public int selectRouteLike(int Routeboard_no);

	// 공지사항 리스트 조회
	public List<ClientServiceNoticeVo> selectNoticeList(RowBounds rb, @Param("searchVal") int searchVal,
			@Param("searchWord") String searchWord, @Param("adminNo") String adminNo);

	// 공지사항 리스트 카운트 조회
	public int selectNoticeCount(@Param("searchVal") int searchVal, @Param("searchWord") String searchWord,
			@Param("adminNo") String adminNo);

	// 공지사항 글등록
	public void insertNotice(@Param("adminNo") int adminNo, @Param("noticeTitle") String noticeTitle,
			@Param("noticeContent") String noticeContent);

	// 공지사항 수정
	public void updateNotice(@Param("noticeTitle") String noticeTitle, @Param("noticeContent") String noticeContent);

	// 공지사항 삭제
	public void deleteNotice(@Param("noticeNo") int noticeNo);

	// 공지사항 조회
	public ClientServiceNoticeVo selectNoticeContent(int noticeNo);

	// 자주묻는 질문 리스트 조회
	public List<ClientServiceFaqVo> selectFaqList(@Param("faqCategoryNo") String faqCategoryNo,
			@Param("faqSearchTitle") String faqSearchTitle, RowBounds rb);

	// 자주묻는 질문 리스트 카운트 조회
	public int selectFaqCount(@Param("faqCategoryNo") String faqCategoryNo,
			@Param("faqSearchTitle") String faqSearchTitle);

	// 자주묻는 질문 카테고리 이름 조회
	public ClientServiceFaqCategoryVo selectByfaqCtr(String faq_category_no);

	// 자주묻는 질문 작성
	public void insertFaq(ClientServiceFaqVo clientServiceFaqVo);

	// 자주묻는 질문 조회
	public ClientServiceFaqVo selectByfaqNo(int faqNo);

	// 자주묻는 질문 수정
	public void updateFaq(ClientServiceFaqVo clientServiceFaqVo);

	// 자주묻는 질문 삭제
	public void deleteFaq(int faqNo);

	// 관리자 닉네임 검색
	public String selectAdmin(int adminNo);

	// 관리자 번호 검색
	public String selectAdminNo(String searchWord);

	// 사용자 신고 리스트
	public List<Map<String, Object>> selectAllMemberBlacklist(int curr_page);

	// 사용자 신고 수
	public int AllMemberBlacklistCount();

	// 사용자 신고 확인여부 상태 변환
	public void updateMemberReportStatus(@Param("report_no") int report_no,
			@Param("report_status") String report_status);

	// 사용자 신고 미확인 개수
	public int selectMemberStatusNCount();

	// 여행지/맛집 게시물 신고 리스트
	public List<Map<String, Object>> selectAllLocationBlacklist(int curr_page);

	// 여행지/맛집 게시물 신고 수
	public int AllLocationBlacklistCount();

	// 여행지/맛집 신고 확인여부 상태 변환
	public void updateLocationReportStatus(@Param("report_no") int report_no,
			@Param("report_status") String report_status);

	// 여행지/맛집 게시물 신고 미확인 개수
	public int selectLocationStatusNCount();

	// 루트 게시물 신고 리스트
	public List<Map<String, Object>> selectAllRouteBlacklist(int curr_page);

	// 루트 게시물 신고 수
	public int AllRouteBlacklistCount();

	// 루트 신고 확인여부 상태 변환
	public void updateRouteReportStatus(@Param("report_no") int report_no,
			@Param("report_status") String report_status);

	// 루트공유 게시물 신고 미확인 개수
	public int selectRouteStatusNCount();

	// 1:1문의 리스트 조회
	public List<ClientServiceInquiryVo> selectInquiryList(@Param("categoryNo") String inquiryCategoryNo,
			@Param("searchWord") String searchWord, RowBounds rb, @Param("memberNo") String memberNo,
			@Param("categoryVal") int categoryVal);

	// 1:1문의 리스트 카운트 조회
	public int selectInquiryCount(@Param("categoryNo") String inquiryCategoryNo, @Param("searchWord") String searchWord,
			@Param("memberNo") String memberNo, @Param("categoryVal") int categoryVal);

	// 1:1문의 카테고리 이름 조회
	public String selectByInquiryCtr(int categoryNo);
	// 1:1문의 답변 작성
	// 1:1문의 답변 수정
	// 1:1문의 답변 삭제

}
