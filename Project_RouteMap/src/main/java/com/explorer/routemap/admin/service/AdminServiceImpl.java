package com.explorer.routemap.admin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.admin.mapper.AdminSQLMapper;
import com.explorer.routemap.board.mapper.LocationboardSQLMapper;
import com.explorer.routemap.board.mapper.ProvinceCategorySQLMapper;
import com.explorer.routemap.board.mapper.RouteboardSQLMapper;
import com.explorer.routemap.board.vo.LocationCategoryVo;
import com.explorer.routemap.board.vo.LocationboardVo;
import com.explorer.routemap.board.vo.ProvinceCategoryVo;
import com.explorer.routemap.board.vo.RouteboardVo;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqCategoryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqVo;
import com.explorer.routemap.clientservice.vo.ClientServiceInquiryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceNoticeVo;
import com.explorer.routemap.clientservice.vo.ClientServicePageNavigatorVo;
import com.explorer.routemap.member.vo.LevelCategoryVo;
import com.explorer.routemap.member.vo.MemberVo;

@Service
public class AdminServiceImpl {

	@Autowired
	AdminSQLMapper adminSQLMapper;
	@Autowired
	private LocationboardSQLMapper locationboardSQLMapper;
	@Autowired
	private RouteboardSQLMapper routeboardSQLMapper;
	@Autowired
	private ProvinceCategorySQLMapper provinceCategorySQLMapper;

	// 멤버리스트 조회
	public List<Map<String, Object>> getMemberList(ClientServicePageNavigatorVo clientServicePageNavigatorVo,
			int searchVal, String searchWord) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(),
					clientServicePageNavigatorVo.getCountPerPage());
			List<MemberVo> memberList = adminSQLMapper.selectMemberList(rb, searchVal, searchWord);

			for (MemberVo memberVo : memberList) {

				Map<String, Object> map = new HashMap<String, Object>();

				LevelCategoryVo levelVo = adminSQLMapper.selectLelvelByExp(memberVo.getMember_levelexp());

				map.put("memberVo", memberVo);
				map.put("levelVo", levelVo);

				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 멤버카운트 조회
	public int getMemberCount(int searchVal, String searchWord) {
		int memberCount = adminSQLMapper.selectMemberCount(searchVal, searchWord);

		return memberCount;

	}

	// 멤버 상태값 바꾸기
	public void updateMemberStatus(int memberNo, int statusVal) {

		String status = "";

		if (statusVal == 1) {

			status = "Y";

		} else if (statusVal == 2) {

			status = "S";

		} else {

			status = "N";
		}

		adminSQLMapper.updateMemberStatus(memberNo, status);

	}

	// 여행지/맛집 리스트 조회
	public List<Map<String, Object>> getLocationList(ClientServicePageNavigatorVo clientServicePageNavigatorVo,
			int searchVal, String searchWord) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(),
					clientServicePageNavigatorVo.getCountPerPage());
			List<LocationboardVo> locationList = null;

			String memberNo = adminSQLMapper.selectMemberNo(searchWord);

			locationList = adminSQLMapper.selectLocationList(rb, searchVal, searchWord, memberNo);

			for (LocationboardVo locationboardVo : locationList) {

				Map<String, Object> map = new HashMap<String, Object>();

				LocationCategoryVo locationCategoryVo = adminSQLMapper
						.selectLocationCategory(locationboardVo.getLocation_category_no());
				ProvinceCategoryVo provinceCategoryVo = adminSQLMapper
						.selectProvinceCategory(locationboardVo.getProvince_category_no());
				int locationLike = adminSQLMapper.selectLocationLike(locationboardVo.getLocationboard_no());
				String locationMember = adminSQLMapper.selectMember(locationboardVo.getMember_no());

				map.put("locationboardVo", locationboardVo);
				map.put("locationCategoryVo", locationCategoryVo);
				map.put("provinceCategoryVo", provinceCategoryVo);
				map.put("locationLike", locationLike);
				map.put("locationMember", locationMember);

				list.add(map);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return list;

	}

	// 여행지,맛집 카운트 조회
	public int getLocationCount(int searchVal, String searchWord) {

		String memberNo = adminSQLMapper.selectMemberNo(searchWord);

		int listCount = adminSQLMapper.selectLocationListCount(searchVal, searchWord, memberNo);

		return listCount;
	}

	// 루트공유 리스트 조회
	public List<Map<String, Object>> getRouteboard(ClientServicePageNavigatorVo clientServicePageNavigatorVo,
			int searchVal, String searchWord) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {

			// 페이징 - 10개씩 나눠서 DB조회
			RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(),
					clientServicePageNavigatorVo.getCountPerPage());
			// 루트공유리스트
			String memberNo = adminSQLMapper.selectMemberNo(searchWord);
			List<RouteboardVo> routeList = adminSQLMapper.selectRouteList(rb, searchVal, searchWord, memberNo);
			for (RouteboardVo routeboardVo : routeList) {

				Map<String, Object> map = new HashMap<String, Object>();

				// 루트 좋아요 수 찾기
				int routeLike = adminSQLMapper.selectRouteLike(routeboardVo.getRouteboard_no());

				// 루트 작성자 조회하기
				String routeMember = adminSQLMapper.selectMember(routeboardVo.getMember_no());

				map.put("routeboardVo", routeboardVo);
				map.put("routeLike", routeLike);
				map.put("routeMember", routeMember);

				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 루트공유 카운트 조회
	public int getRouteCount(int searchVal, String searchWord) {

		String memberNo = adminSQLMapper.selectMemberNo(searchWord);

		int listCount = adminSQLMapper.selectRouteListCount(searchVal, searchWord, memberNo);

		return listCount;
	}

	// 인기장소 1~6순위 루트 출력
	public Map<String, Object> getSixLCLike() {

		Map<String, Object> map = new HashMap<String, Object>();

		List<Integer> lcCountList = adminSQLMapper.selectLCLikeCount();
		List<Integer> lcNoList = adminSQLMapper.selectLCLikeNo();

		List<LocationboardVo> list = new ArrayList<LocationboardVo>();
		for (int i = 0; i < lcNoList.size(); i++) {
			LocationboardVo lcVo = locationboardSQLMapper.selectByLocationboardNo(lcNoList.get(i));
			list.add(lcVo);

		}

		map.put("count", lcCountList);
		map.put("locationboardVo", list);

		return map;
	}

	// 인기차트 1~6순위 루트 출력
	public Map<String, Object> getSixRTLike() {

		Map<String, Object> map = new HashMap<String, Object>();

		List<Integer> rtCountList = adminSQLMapper.selectRTLikeCount();
		List<Integer> rtNoList = adminSQLMapper.selectRTLikeNo();

		List<RouteboardVo> list = new ArrayList<RouteboardVo>();

		for (int i = 0; i < rtNoList.size(); i++) {
			RouteboardVo rtVo = routeboardSQLMapper.selectRouteByRouteboardNo(rtNoList.get(i));

			list.add(rtVo);
		}

		map.put("count", rtCountList);
		map.put("routeboardVo", list);

		return map;

	}

	// 도넛차트 총 게시글수 ,1~5순위 장소 출력
	public Map<String, Object> getFiveLcBoard() {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String today = format1.format(time);

		Calendar cal = new GregorianCalendar(Locale.KOREA);

		cal.setTime(time);
		cal.add(Calendar.DATE, -1);
		String yesterday = format1.format(cal.getTime());
		cal.add(Calendar.DATE, -1);
		String yester2day = format1.format(cal.getTime());

		Map<String, Object> map = new HashMap<String, Object>();

		// 오늘 1~5순위 장소번호 리스트
		List<Integer> lcProNoList = adminSQLMapper.selectLcProvCatNo(today);

		// 어제 1~5순위 장소번호 리스트
		List<Integer> yesterLcProNoList = adminSQLMapper.selectLcProvCatNo(yesterday);

		// 1~5순위 지역카테고리 VO 리스트
		List<String> todayList = new ArrayList<String>();
		for (int i = 0; i < lcProNoList.size(); i++) {

			ProvinceCategoryVo proVo = provinceCategorySQLMapper.selectByNo(lcProNoList.get(i));
			String proName = proVo.getProvince_category_name();
			todayList.add(proName);
		}

		// 어제 1~5순위 1~5순위 지역카테고리 VO 리스트
		List<String> yesterList = new ArrayList<String>();
		for (int i = 0; i < yesterLcProNoList.size(); i++) {

			ProvinceCategoryVo proVo = provinceCategorySQLMapper.selectByNo(yesterLcProNoList.get(i));
			String proName = proVo.getProvince_category_name();
			yesterList.add(proName);
		}

		// 오늘 1~5순위 장소 게시글 수 리스트
		List<Integer> lcBoardCountList = adminSQLMapper.selectLcboardCount(today);
		// 어제 1~5순위 장소 게시글 수 리스트
		List<Integer> yesterLcBoardCountList = adminSQLMapper.selectLcboardCount(yesterday);

		// 오늘 총 게시글 수
		int lcBoardCount = adminSQLMapper.selectLocationCount(today);
		// 어제 총 게시글 수
		int yesterLcBoardCount = adminSQLMapper.selectLocationCount(yesterday);
		// 엊그제 총 게시글 수
		int yester2LcBoardCount = adminSQLMapper.selectLocationCount(yester2day);

		// 오늘 리스트
		map.put("lcProList", todayList);
		// 오늘 카운트 리스트
		map.put("lcProCountList", lcBoardCountList);
		// 오늘 전체글 카운트
		map.put("lcBoardCount", lcBoardCount);

		// 어제 리스트
		map.put("yesterLcProList", yesterList);
		// 어제 카운트 리스트
		map.put("yesterLcProCountList", yesterLcBoardCountList);
		// 어제 전체글 카운트
		map.put("yesterLcBoardCount", yesterLcBoardCount);
		// 엊그제 전체글 카운트
		map.put("yester2LcBoardCount", yester2LcBoardCount);

		return map;
	}

	// 로그인 시 로그인카운터 증가
	public void insertLoginData(MemberVo memberVo) {

		adminSQLMapper.insertLoginData(memberVo.getMember_no());
	}

	// 스택바 차트!!
	public Map<String, Object> getStackChart() {

		Map<String, Object> map = new HashMap<String, Object>();

		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		String today = format1.format(time);

		Calendar cal = new GregorianCalendar(Locale.KOREA);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.DATE, -6);
		// 오늘기준 이번주 처음 -6일
		String startDaybyWeek = format1.format(cal.getTime());
		cal.add(Calendar.DATE, -1);
		// 오늘기준 저번주 끝 -7일
		String endDaybyLastWeek = format1.format(cal.getTime());
		cal.add(Calendar.DATE, -6);
		// 오늘기준 저번주 처음 -13일
		String startDaybyLastWeek = format1.format(cal.getTime());
		cal.add(Calendar.DATE, -1);
		// 오늘기준 저저번주 끝 -14일
		String endDaybyLast2Week = format1.format(cal.getTime());
		cal.add(Calendar.DATE, -6);
		// 오늘기준 저저번주 끝 -20일
		String startDaybyLast2Week = format1.format(cal.getTime());

		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DATE, +20);

		List<String> list = new ArrayList<String>();
		List<Integer> countList = new ArrayList<Integer>();

		for (int i = 0; i < 7; i++) {
			list.add(format2.format(cal.getTime()));
			countList.add(adminSQLMapper.selectLoginCountbyDate(format1.format(cal.getTime())));
			cal.add(Calendar.DATE, -1);
		}

		List<String> lastList = new ArrayList<String>();
		List<Integer> lastCountList = new ArrayList<Integer>();

		for (int i = 0; i < 7; i++) {
			lastList.add(format2.format(cal.getTime()));
			lastCountList.add(adminSQLMapper.selectLoginCountbyDate(format1.format(cal.getTime())));
			cal.add(Calendar.DATE, -1);
		}

		System.out.println(startDaybyLastWeek);
		System.out.println(endDaybyLastWeek);
		System.out.println(startDaybyLast2Week);
		System.out.println(endDaybyLast2Week);
		// 이번주 로그인 누적 수
		int thisWeekLoginCount = adminSQLMapper.selectLoginWeekCount(startDaybyWeek, today);
		// 저번주 로그인 누적 수
		int lastWeekLoginCount = adminSQLMapper.selectLoginWeekCount(startDaybyLastWeek, endDaybyLastWeek);
		// 저저번주 로그인 누적 수
		int last2WeekLoginCount = adminSQLMapper.selectLoginWeekCount(startDaybyLast2Week, endDaybyLast2Week);

		// 로그인 누적 수
		map.put("thisWeekLoginCount", thisWeekLoginCount);
		map.put("lastWeekLoginCount", lastWeekLoginCount);
		map.put("last2WeekLoginCount", last2WeekLoginCount);
		map.put("list", list);
		map.put("lastList", lastList);
		map.put("countList", countList);
		map.put("lastCountList", lastCountList);

		return map;
	}

	// 공지사항 리스트 조회
	public List<Map<String, Object>> getNoticeList(ClientServicePageNavigatorVo clientServicePageNavigatorVo,
			int searchVal, String searchWord) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			// 페이징 - 10개씩 나눠서 DB조회
			RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(),
					clientServicePageNavigatorVo.getCountPerPage());

			String adminNo = adminSQLMapper.selectAdminNo(searchWord);

			List<ClientServiceNoticeVo> noticeList = adminSQLMapper.selectNoticeList(rb, searchVal, searchWord,
					adminNo);

			for (ClientServiceNoticeVo noticeVo : noticeList) {
				Map<String, Object> map = new HashMap<String, Object>();

				String adminName = adminSQLMapper.selectAdmin(noticeVo.getAdmin_no());

				map.put("noticeVo", noticeVo);
				map.put("adminName", adminName);

				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 공지사항 카운트 조회
	public int getNoticeCount(int searchVal, String searchWord) {

		String adminNo = adminSQLMapper.selectAdminNo(searchWord);

		int noticeCount = adminSQLMapper.selectNoticeCount(searchVal, searchWord, adminNo);

		return noticeCount;
	}

	// 공지사항 글 조회
	public ClientServiceNoticeVo getNoticeContent(int noticeNo) {

		ClientServiceNoticeVo clientServiceNoticeVo = adminSQLMapper.selectNoticeContent(noticeNo);

		return clientServiceNoticeVo;

	}

	// 공지사항 글 등록
	public void writeNotice(int memberNo, String noticeTitle, String noticeContent) {
		adminSQLMapper.insertNotice(memberNo, noticeTitle, noticeContent);
	}

	// 공지사항 수정
	public void updateNotice(String noticeTitle, String noticeContent) {
		adminSQLMapper.updateNotice(noticeTitle, noticeContent);
	}

	// 공지사항 삭제
	public void deleteNotice(int noticeNo) {
		adminSQLMapper.deleteNotice(noticeNo);
	}

	// 자주묻는질문 조회
	public List<Map<String, Object>> getFaqList(String faqCategoryNo, String faqSearchTitle,
			ClientServicePageNavigatorVo clientServicePageNavigatorVo) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<ClientServiceFaqVo> faqList = null;

		try {
			RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(),
					clientServicePageNavigatorVo.getCountPerPage());
			faqList = adminSQLMapper.selectFaqList(faqCategoryNo, faqSearchTitle, rb);

			for (ClientServiceFaqVo faqVo : faqList) {

				Map<String, Object> map = new HashMap<String, Object>();

				ClientServiceFaqCategoryVo faqCtrVo = adminSQLMapper.selectByfaqCtr(faqVo.getFaq_category_no());

				// date format
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				String date = format.format(faqVo.getFaq_writedate());
				Date date2 = format.parse(date);
				faqVo.setFaq_writedate(date2);

				map.put("faqVo", faqVo);
				map.put("faqCtrVo", faqCtrVo);

				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	// 자주묻는 질문 리스트 카운트 조회
	public int getFaqCount(String faqCategoryNo, String faqSearchTitle) {

		return adminSQLMapper.selectFaqCount(faqCategoryNo, faqSearchTitle);

	}

	// 자주묻는 질문 등록
	public void writeFaq(ClientServiceFaqVo clientServiceFaqVo) {
		adminSQLMapper.insertFaq(clientServiceFaqVo);
	}

	// 자주묻는 질문 조회
	public Map<String, Object> getFaqContent(int faqNo) {

		Map<String, Object> map = new HashMap<String, Object>();

		ClientServiceFaqVo clientServiceFaqVo = adminSQLMapper.selectByfaqNo(faqNo);
		String adminName = adminSQLMapper.selectAdmin(clientServiceFaqVo.getAdmin_no());
		ClientServiceFaqCategoryVo clientServiceFaqCategoryVo = adminSQLMapper
				.selectByfaqCtr(clientServiceFaqVo.getFaq_category_no());

		map.put("faqVo", clientServiceFaqVo);
		map.put("adminName", adminName);
		map.put("clientServiceFaqCategoryVo", clientServiceFaqCategoryVo);

		return map;
	}

	// 자주묻는 질문 수정
	public void updateFaq(ClientServiceFaqVo clientServiceFaqVo) {
		adminSQLMapper.updateFaq(clientServiceFaqVo);
	}

	// 자주묻는 질문 삭제
	public void deleteFaq(int faqNo) {
		adminSQLMapper.deleteFaq(faqNo);
	}

	// 사용자 신고 리스트
	public List<Map<String, Object>> getAllMemberBlacklist(int curr_page) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = adminSQLMapper.selectAllMemberBlacklist(curr_page);
		int count = adminSQLMapper.AllMemberBlacklistCount();

		// 리스트에 count 추가하기
		for (Map<String, Object> map : list) {

			map.put("count", count);
		}

//		for(Map<String, Object> aa : list) {
//			System.out.println("서비스 "+aa.get("report_no"));
//		}
		return list;
	}

	// 사용자 신고 확인여부 상태 변환
	public void changeMemberStatus(int report_no, String report_status) {

		System.out.println("서비스쪽report_status" + report_status);
		adminSQLMapper.updateMemberReportStatus(report_no, report_status);

	}

	// 사용자 신고 수
	public int getMemberReportCount() {

		int count = adminSQLMapper.AllMemberBlacklistCount();

		return count;
	}

	// 사용자 신고 미확인 수
	public int getMemberReportStatusNCount() {

		int memberNCount = adminSQLMapper.selectMemberStatusNCount();

		return memberNCount;
	}

	// 여행지/맛집 게시물 신고 리스트
	public List<Map<String, Object>> getAllLocationBlacklist(int curr_page) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = adminSQLMapper.selectAllLocationBlacklist(curr_page);
		int count = adminSQLMapper.AllLocationBlacklistCount();

		// 리스트에 count 추가하기
		for (Map<String, Object> map : list) {

			map.put("count", count);
		}

//			for(Map<String, Object> aa : list) {
//				System.out.println("서비스 "+aa.get("report_no"));
//			}
		return list;
	}

	// 여행지/맛집 신고 확인여부 상태 변환
	public void changeLocationStatus(int report_no, String report_status) {

		System.out.println("서비스쪽report_status" + report_status);
		adminSQLMapper.updateLocationReportStatus(report_no, report_status);

	}

	// 여행지/맛집 게시물 신고 수
	public int getLocationReportCount() {

		int count = adminSQLMapper.AllLocationBlacklistCount();

		return count;
	}

	// 여행지/맛집 게시물 신고 미확인 수
	public int getLocationReportStatusNCount() {

		int locationNCount = adminSQLMapper.selectLocationStatusNCount();

		return locationNCount;
	}

	// 루트 게시물 신고 리스트
	public List<Map<String, Object>> getAllRouteBlacklist(int curr_page) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = adminSQLMapper.selectAllRouteBlacklist(curr_page);
		int count = adminSQLMapper.AllRouteBlacklistCount();

		// 리스트에 count 추가하기
		for (Map<String, Object> map : list) {

			map.put("count", count);
		}

//				for(Map<String, Object> aa : list) {
//					System.out.println("서비스 "+aa.get("report_no"));
//				}
		return list;
	}

	// 루트 신고 확인여부 상태 변환
	public void changeRouteStatus(int report_no, String report_status) {

		System.out.println("서비스쪽report_status" + report_status);
		adminSQLMapper.updateRouteReportStatus(report_no, report_status);

	}

	// 루트공유 게시물 신고 수
	public int getRouteReportCount() {

		int count = adminSQLMapper.AllRouteBlacklistCount();

		return count;
	}

	// 루트공유 게시물 신고 미확인 수
	public int getRouteReportStatusNCount() {

		int routeNCount = adminSQLMapper.selectRouteStatusNCount();

		return routeNCount;
	}

	// 1:1 문의 리스트
	public List<Map<String, Object>> getInquiryList(ClientServicePageNavigatorVo clientServicePageNavigatorVo,
			String categoryNo, String searchWord, int categoryVal) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			// 페이징 - 10개씩 나눠서 DB조회
			RowBounds rb = new RowBounds(clientServicePageNavigatorVo.getStratRecord(),
					clientServicePageNavigatorVo.getCountPerPage());

			String memberNo = adminSQLMapper.selectMemberNo(searchWord);

			List<ClientServiceInquiryVo> inquiryList = adminSQLMapper.selectInquiryList(categoryNo, searchWord, rb,
					memberNo, categoryVal);

			for (ClientServiceInquiryVo inquiryVo : inquiryList) {
				Map<String, Object> map = new HashMap<String, Object>();

				String categoryName = adminSQLMapper.selectByInquiryCtr(inquiryVo.getInquiry_category_no());
				String memberName = adminSQLMapper.selectMember(inquiryVo.getMember_no());

				map.put("inquiryVo", inquiryVo);
				map.put("categoryName", categoryName);
				map.put("memberName", memberName);

				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 1:1 문의 리스트 카운트 조회
	public int getInquiryCount(String categoryNo, String searchWord, int categoryVal) {

		String memberNo = adminSQLMapper.selectMemberNo(searchWord);
		int inquiryListCount = adminSQLMapper.selectInquiryCount(categoryNo, searchWord, memberNo, categoryVal);

		return inquiryListCount;
	}

}
