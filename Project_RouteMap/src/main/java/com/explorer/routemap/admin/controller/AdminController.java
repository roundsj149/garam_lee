package com.explorer.routemap.admin.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.explorer.routemap.admin.service.AdminServiceImpl;
import com.explorer.routemap.admin.vo.AdminVo;
import com.explorer.routemap.board.service.LocationboardServiceImpl;
import com.explorer.routemap.clientservice.service.ClientServiceServiceImpl;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqCategoryVo;
import com.explorer.routemap.clientservice.vo.ClientServiceFaqVo;
import com.explorer.routemap.clientservice.vo.ClientServiceNoticeVo;
import com.explorer.routemap.clientservice.vo.ClientServicePageNavigatorVo;
import com.explorer.routemap.member.controller.SessionChecker;
import com.explorer.routemap.member.vo.MemberVo;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	@Autowired
	private ClientServiceServiceImpl clientServiceService;
	@Autowired
	private AdminServiceImpl adminService;

	final static int countPerPage = 10;
	final static int pagePerGroup = 5;

	//자주묻는 질문 리스트
		@RequestMapping("/faq_page.do")
		public String faqPage(HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			return "admin/faq_page";
		}
		
		//자주묻는 질문 리스트
		@RequestMapping("/faq_list.do")
		@ResponseBody
		public Map<String,Object> faqProcess(
				@RequestParam(value = "currPage", required = false , defaultValue = "1") int currPage,
				@RequestParam(value = "faqCategoryNo", required = false, defaultValue = "0") String faqCategoryNo,
				@RequestParam(value = "faqSearch", required = false, defaultValue = "") String faqSearch) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, adminService.getFaqCount(faqCategoryNo, faqSearch));
			
			List<Map<String,Object>> faqList = adminService.getFaqList(faqCategoryNo,faqSearch, clientServicePageNavigatorVo);
			
			map.put("faqList",faqList);
			map.put("pageVo",clientServicePageNavigatorVo);
			map.put("faqCtgNo",faqCategoryNo);
			map.put("faqSearch",faqSearch);
			
			return map;
		}
		
		//자주묻는 질문 작성
		@RequestMapping("/faq_write_page.do")
		public String faqWritePage(HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			return "admin/faq_write_page";
		}
		
		//자주묻는 질문 작성
		@RequestMapping("faq_write_process.do")
		@ResponseBody
		public Map<String,Object> faqWriteProcess(HttpSession session, HttpServletRequest request) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
		
			String faqCategoryNo = request.getParameter("faqCategoryNo");
			String faqTitle = request.getParameter("faqTitle");
			String faqContent = request.getParameter("faqContent");
			
			ClientServiceFaqVo clientServiceFaqVo = new ClientServiceFaqVo();
			clientServiceFaqVo.setAdmin_no(adminVo.getAdmin_no());
			clientServiceFaqVo.setFaq_title(faqTitle);
			clientServiceFaqVo.setFaq_content(faqContent);
			clientServiceFaqVo.setFaq_category_no(faqCategoryNo);
			
			adminService.writeFaq(clientServiceFaqVo);
			
			return map;
		}
		
		//자주묻는 질문 조회
		@RequestMapping("faq_content_page.do")
		public String faqContentPage(int faqNo, Model model, HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			model.addAttribute("faqNo",faqNo);
			
			return "admin/faq_content_page";
		}
		
		//자주묻는 질문 조회
		@RequestMapping("faq_content_process.do")
		@ResponseBody
		public Map<String,Object> faqContentProcess(HttpServletRequest request) {
			
			String faqNo = request.getParameter("faqNo");
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			Map<String,Object> faqContent = adminService.getFaqContent(Integer.parseInt(faqNo));
			
			map.put("faqContent", faqContent);
			
			return map;
		}
		
		//자주묻는 질문 수정
		@RequestMapping("faq_update_page.do")
		public String faqUpdatePage(int faqNo, Model model, HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			Map<String,Object> faqContent = adminService.getFaqContent(faqNo);
			
			model.addAttribute("faqContent",faqContent);
			
			return "admin/faq_update_page";
		}
		
		//자주묻는 질문 수정
		@RequestMapping("faq_update_process.do")
		@ResponseBody
		public Map<String,Object> faqUpdateProcess(HttpServletRequest request) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			ClientServiceFaqVo clientServiceFaqVo = new ClientServiceFaqVo();
			
			clientServiceFaqVo.setFaq_category_no(request.getParameter("faqUpdateCategory"));
			clientServiceFaqVo.setFaq_title(request.getParameter("faqUpdateTitle"));
			clientServiceFaqVo.setFaq_content(request.getParameter("faqUpdateContent"));
			clientServiceFaqVo.setFaq_no(Integer.parseInt(request.getParameter("faqNo")));
			adminService.updateFaq(clientServiceFaqVo);
			
			int faqNo = Integer.parseInt(request.getParameter("faqNo"));
			
			map.put("faqNo",faqNo);
			map.put("returnCode","0000");
			map.put("returnMsg","글 수정을 완료하였습니다.");
			
			return map;
		}
		
		//자주묻는 질문 삭제
		@RequestMapping("/faq_delete_process.do")
		@ResponseBody
		public Map<String,Object> faqDeleteProcess(HttpServletRequest request) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			adminService.deleteFaq(Integer.parseInt(request.getParameter("faqNo")));
			
			map.put("returnCode","0000");
			map.put("returnMsg","글 삭제가 완료되었습니다");
			
			return map;
		}
		

		
		//공지사항 리스트
		@RequestMapping("/notice_process.do")
		@ResponseBody
		public Map<String,Object> noticeProcess(
				@RequestParam(value = "currPage", required = false , defaultValue = "1") int currPage,
				@RequestParam(value = "searchVal", required = false, defaultValue = "0") int searchVal ,
				@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, adminService.getNoticeCount(searchVal, searchWord));
			List<Map<String,Object>> noticeList = adminService.getNoticeList(clientServicePageNavigatorVo,searchVal,searchWord);
			
			map.put("noticeList",noticeList);
			map.put("pageVo", clientServicePageNavigatorVo);
			
			return map;
		}

		//공지사항 글쓰기
		@RequestMapping("/notice_write_page.do")
		public String noticeWritePage(HttpSession session, Model model) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy.MM.dd", Locale.KOREA );
			Date today = new Date ();
			String getToday = dateFormat.format(today);

			model.addAttribute("date",getToday);
			return "admin/notice_write_page";
		
		}

		//공지사항 글쓰기
		@RequestMapping("/notice_write_process.do")
		@ResponseBody
		public Map<String,Object> noticeWriteProcess(HttpSession session, HttpServletRequest request) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			
			if(adminVo == null) {
				
				map.put("returnMsg","1111");
				System.out.println("test");
				return map;
			
			} else {
			
				int adminNo = adminVo.getAdmin_no();
				String noticeTitle = request.getParameter("noticeTitle");
				String noticeContent = request.getParameter("noticeContent");
		
				adminService.writeNotice(adminNo, noticeTitle, noticeContent);
				
				map.put("returnMsg","0000");
				System.out.println("test2");
				return map;
			}
		}
		
		//공지사항 조회
		@RequestMapping("/notice_content_page.do")
		public String noticeContentPage(int noticeNo, Model model, HttpSession session){
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			model.addAttribute("noticeNo",noticeNo);
			
			return "admin/notice_content_page";
		}
		
		//공지사항 조회
		@RequestMapping("/notice_content_process.do")
		@ResponseBody
		public Map<String,Object> noticeContentProcess(HttpServletRequest request) {

			Map<String,Object> map = new HashMap<String,Object>();
			
			String noticeNo = request.getParameter("noticeNo");
			
			ClientServiceNoticeVo clientServiceNoticeVo = adminService.getNoticeContent(Integer.parseInt(noticeNo));
			
			map.put("noticeVo",clientServiceNoticeVo);
			
			return map;
		}
		
		//공지사항 수정
		@RequestMapping("/notice_update_page.do")
		public String noticeUpdatePage(HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			return "admin/notice_update_page";
		}
		
		//공지사항 수정
		@RequestMapping("/notice_update_process.do")
		@ResponseBody
		public Map<String,Object> noticeUpdateProcess(HttpSession session, HttpServletRequest request) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			//공지사항 글쓴 관리자 넘버
			int adminNo = Integer.parseInt(request.getParameter("adminNo"));
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionUser");
			
			//공지사항 글쓴 관리자 넘버가 세션관리자 넘버와 맞지 않으면 
			if(adminVo.getAdmin_no() != adminNo) {
				
				map.put("returnMsg","1111");
				return map;
				
			} else {
				
				String noticeTitle = request.getParameter("noticeTitle");
				String noticeContent = request.getParameter("noticeContent");

				adminService.updateNotice(noticeTitle, noticeContent);

				map.put("returnMsg","0000");
				
				return map;	
			}
		}
		
		//공지사항 삭제
		@RequestMapping("/notice_delete_process.do")
		@ResponseBody
		public Map<String,Object> noticeDeleteProcess(HttpSession session, HttpServletRequest request) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			int adminNo = Integer.parseInt(request.getParameter("adminNo"));
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			
			int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
			
			//공지사항 글쓴 관리자 넘버가 세션관리자 넘버와 맞지 않으면 
			if(adminVo.getAdmin_no() != adminNo) {
				
				map.put("returnMsg","1111");
				return map;
				
			} else {
				
				adminService.deleteNotice(noticeNo);

				map.put("returnMsg","0000");
				
				return map;	
			}
			
		}
			
		//1:1문의 리스트
		@RequestMapping("inquiry_page.do")
		public String inquiryPage(HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			return "admin/inquiry_page";
		}
		
		//1:1 문의 리스트
		@RequestMapping("inquiry_process.do")
		@ResponseBody
		public Map<String,Object> inquiryProcess (
				@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage, 
				@RequestParam(value = "categoryNo", required = false, defaultValue = "0") String categoryNo, 
				@RequestParam(value = "searchWord", required = false, defaultValue = "2") String searchWord,
				@RequestParam(value = "categoryVal", required = false, defaultValue = "0") int categoryVal) {
		
			Map<String,Object> map = new HashMap<String,Object>();
		
			ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, adminService.getInquiryCount(categoryNo, searchWord, categoryVal));
			
			List<Map<String,Object>> list = adminService.getInquiryList(clientServicePageNavigatorVo, categoryNo, searchWord, categoryVal);
			
			map.put("list",list);
			
			return map;
			
		}
		
		@RequestMapping("/member_list_page.do")
		public String memberListPage(HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			return "admin/member_list_page";
		}
		
		

		
		//회원 리스트 출력 ajax
		@RequestMapping("/member_list_process.do")
		@ResponseBody
		public Map<String,Object> memberListProcess(
				@RequestParam(value = "currPage", required = false , defaultValue = "1") int currPage,
				@RequestParam(value = "searchVal", required = false, defaultValue = "0") int searchVal,
				@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord) {
			
			System.out.println(searchVal);
			System.out.println(searchWord);
			
			Map<String,Object> map = new HashMap<String,Object>();
			ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, adminService.getMemberCount(searchVal, searchWord));
			
			List<Map<String,Object>> memberList = adminService.getMemberList(clientServicePageNavigatorVo, searchVal, searchWord);

			map.put("pageVo",clientServicePageNavigatorVo);
			map.put("memberList", memberList);
			
			return map;
		}
		
		//회원 상태값 변경 ajax
		@RequestMapping("/member_status_process.do")
		@ResponseBody
		public void memberStatusProcess(HttpServletRequest request) {
			int memberNo = Integer.parseInt(request.getParameter("memberNo"));
			int statusVal = Integer.parseInt(request.getParameter("statusVal"));
			
			adminService.updateMemberStatus(memberNo, statusVal);
		}
		
	
		@RequestMapping("/locationboard_list_process.do")
		@ResponseBody
		public Map<String,Object> locationboardListProcess(
				@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage, 
				@RequestParam(value = "searchVal", required = false, defaultValue = "0") int searchVal,
				@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord ) {
				
			Map<String,Object> map = new HashMap<String,Object>();
			
			ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, adminService.getLocationCount(searchVal, searchWord));
			List<Map<String,Object>> locationList = adminService.getLocationList(clientServicePageNavigatorVo, searchVal, searchWord);
			
			map.put("pageVo",clientServicePageNavigatorVo);
			map.put("locationList", locationList);
			
			return map;
		}
		
		//루트공유 게시판 리스트
		@RequestMapping("routeboard_list_page.do")
		public String routeboardListPage(HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			return "admin/routeboard_list_page";
		}

		
		@RequestMapping("routeboard_list_process.do")
		@ResponseBody
		public Map<String,Object> routeboardListProcess(			
				@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage, 
				@RequestParam(value = "searchVal", required = false, defaultValue = "0") int searchVal,
				@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			ClientServicePageNavigatorVo clientServicePageNavigatorVo = new ClientServicePageNavigatorVo(countPerPage, pagePerGroup, currPage, adminService.getRouteCount(searchVal, searchWord));
			List<Map<String,Object>> routeList = adminService.getRouteboard(clientServicePageNavigatorVo, searchVal, searchWord);
			
			map.put("routeList", routeList);
			map.put("pageVo",clientServicePageNavigatorVo);
			return map;
		}
		
		//대쉬보드
		@RequestMapping("dashboard_page.do")
		public String dashboardPage(HttpSession session) {
			
			AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
			if(adminVo == null) {
				return "redirect:/member/admin_login_page.do";
			}
			
			return "admin/dashboard_page";
		}

		
		@RequestMapping("dashboard_process.do")
		@ResponseBody
		public Map<String,Object> dashboardProcess(HttpSession session) {
			
			Map<String,Object> bigMap = new HashMap<String,Object>();
			
			Map<String,Object> map = adminService.getSixLCLike();
			Map<String,Object> map2 = adminService.getSixRTLike();
			Map<String,Object> map3 = adminService.getFiveLcBoard();
			Map<String,Object> stackMap = adminService.getStackChart();
			
			bigMap.put("lcMap",map);
			bigMap.put("rtMap",map2);
			bigMap.put("donutMap",map3);
			bigMap.put("stackMap",stackMap);
			
			return bigMap;
		}
		
		//실시간 접속자 조회 차트
		@RequestMapping("flot_process.do")
		@ResponseBody
		public Map<String,Object> flotProcess() {
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("x", SessionChecker.getNowUser());
			return map;
		}

	// 사용자 신고리스트
	@RequestMapping("member_blacklist_page.do")
	public String memberBlacklistPage() {
		return "admin/member_blacklist_page";
	}

	// 사용자 신고리스트 호출
	@RequestMapping("member_blacklist_process.do")
	@ResponseBody
	public List<Map<String, Object>> memberBlacklistProcess(
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		List<Map<String, Object>> list = adminService.getAllMemberBlacklist(currPage);

		// for(Map<String, Object> aa : list) {
//			System.out.println("컨트롤러 "+aa.get("report_no"));
//		}

		return list;
	}

	// 사용자 신고리스트 상태 변환
	@RequestMapping("member_blacklist_check.do")
	@ResponseBody
	public void memberBlacklistCheck(int report_no, String report_status) {
		System.out.println("status" + report_status);
		adminService.changeMemberStatus(report_no, report_status);

	}

	// 사용자 신고 리스트 수
	@RequestMapping("member_blacklist_count_process.do")
	@ResponseBody
	public int memberBlacklistCountProcess() {

		int count = adminService.getMemberReportCount();

		return count;
	}

	// 사용자 신고 미확인 수
	@RequestMapping("member_blacklist_n_count_process.do")
	@ResponseBody
	public int memberBlacklistNCountProcess() {

		int memberNCount = adminService.getMemberReportStatusNCount();

		return memberNCount;
	}

	// 여행지/맛집 게시판 리스트
	@RequestMapping("/locationboard_list_page.do")
	public String locationboardListPage(HttpSession session) {
		
		AdminVo adminVo = (AdminVo)session.getAttribute("sessionAdmin");
		if(adminVo == null) {
			return "redirect:/member/admin_login_page.do";
		}
		
		return "admin/locationboard_list_page";
	}


	// 여행지/맛집 신고 게시물 리스트
	@RequestMapping("/locationboard_blacklist_page.do")
	public String locationboardBlacklistPage() {
		return "admin/locationboard_blacklist_page";
	}

	// 여행지/맛집 신고 게시물 리스트 호출

	@RequestMapping("location_blacklist_process.do")
	@ResponseBody
	public List<Map<String, Object>> locationBlacklistProcess(
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		List<Map<String, Object>> list = adminService.getAllLocationBlacklist(currPage);

		// for(Map<String, Object> aa : list) {
//			System.out.println("컨트롤러 "+aa.get("report_no"));
//		}

		return list;
	}

	// 여행지/맛집 신고리스트 상태 변환
	@RequestMapping("location_blacklist_check.do")
	@ResponseBody
	public void locationBlacklistCheck(int report_no, String report_status) {
		System.out.println("status" + report_status);
		adminService.changeLocationStatus(report_no, report_status);

	}

	// 여행지/맛집 게시물 신고 리스트 수
	@RequestMapping("location_blacklist_count_process.do")
	@ResponseBody
	public int locationBlacklistCountProcess() {

		int count = adminService.getLocationReportCount();

		return count;
	}
	
	// 여행지/맛집 게시물 신고 미확인 수
	@RequestMapping("location_blacklist_n_count_process.do")
	@ResponseBody
	public int locationBlacklistNCountProcess() {

		int locationNCount = adminService.getLocationReportStatusNCount();

		return locationNCount;
	}
	

	// 루트공유 신고 게시물 리스트
	@RequestMapping("/routeboard_blacklist_page.do")
	public String routeboardBlacklistPage() {
		return "admin/routeboard_blacklist_page";
	}

	// 루트공유 신고 게시물 리스트 호출

	@RequestMapping("route_blacklist_process.do")
	@ResponseBody
	public List<Map<String, Object>> routeBlacklistProcess(
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		List<Map<String, Object>> list = adminService.getAllRouteBlacklist(currPage);

		// for(Map<String, Object> aa : list) {
//				System.out.println("컨트롤러 "+aa.get("report_no"));
//			}

		return list;
	}

	// 루트공유 게시물 신고리스트 상태 변환
	@RequestMapping("route_blacklist_check.do")
	@ResponseBody
	public void routeBlacklistCheck(int report_no, String report_status) {
		System.out.println("status" + report_status);
		adminService.changeRouteStatus(report_no, report_status);

	}

	// 루트공유 게시물 신고 리스트 수
	@RequestMapping("route_blacklist_count_process.do")
	@ResponseBody
	public int routeBlacklistCountProcess() {

		int count = adminService.getRouteReportCount();

		return count;
	}
	
	// 루트공유 게시물 신고 미확인 수
	@RequestMapping("route_blacklist_n_count_process.do")
	@ResponseBody
	public int routeBlacklistNCountProcess() {

		int routeNCount = adminService.getRouteReportStatusNCount();

		return routeNCount;
	}

	// 자유게시판 리스트
	@RequestMapping("freeboard_list_page.do")
	public String freeboardListPage() {
		return "admin/freeboard_list_page";
	}


	

}
