package com.explorer.routemap.board.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.board.service.*;

@Controller
@RequestMapping("/routeboard/*")
public class RouteboardController {

	@Autowired
	RouteboardServiceImpl routeboardService;
	@Autowired
	LocationboardServiceImpl locationboardService;

	// 루트 공유 - rt_main_process
	// lc_main_process
	@RequestMapping("/rt_main_process.do")
	@ResponseBody
	public List<Map<String, Object>> routeboardMainProcess(HttpSession session, Model model,
			@RequestParam(value = "provinceCheckboxArr[]", required = false, defaultValue = "0") int[] province_checkbox_no,
			@RequestParam(value = "costCheckboxArr[]", required = false, defaultValue = "0") int[] cost_checkbox_no,
			@RequestParam(value = "timeCheckboxArr[]", required = false, defaultValue = "0") int[] time_checkbox_no,
			@RequestParam(value = "alignNum", required = false, defaultValue = "1") int align_no,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage)

	{

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		int member_no = 0;

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			member_no = 0;

		} else {

			member_no = memberVo.getMember_no();

		}
		list = routeboardService.getSpecificBoardList(member_no, province_checkbox_no, cost_checkbox_no,
				time_checkbox_no, align_no, searchWord, currPage);
		
		for(Map<String, Object> map : list) {
			
			RouteboardVo routeboardVo = (RouteboardVo) map.get("routeboardVo");
			
			routeboardVo
			.setRouteboard_content(routeboardVo.getRouteboard_content().replace("\r\n", "<br>"));
			
		}

		// 좋아요순 정렬일 때 페이징
		if (align_no == 3) {

			List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
			newList = list;
			System.out.println("controller 152줄");
			list = new ArrayList<Map<String, Object>>();

			for (int i = (currPage - 1) * 12 + 1; i <= (currPage - 1) * 12 + 12; i++) {
				if (i <= newList.size()) { // 3건 > get(0), get(1), get(2)
					System.out.println("controller 157줄: " + i);
					list.add(newList.get(i - 1));
				}
			}
		}
		return list;
	}

	// 루트 공유 - 메인 페이지
	@RequestMapping("/rt_main_page.do")
	public String mainPage(HttpSession session, Model model,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage, String search_word) {

		return "/routeboard/rt_main_page";
	}

	// 루트 공유 - 글쓰기 페이지
	@RequestMapping("/rt_write_content_page.do")
	public String writeContentPage(HttpSession session, Model model, HttpServletResponse response,
			String routeboard_title) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");
		}

		System.out.println("테스트 회원 번호 : " + memberVo.getMember_no());

		List<Map<String, Object>> myfavoriteLocationboardList = routeboardService
				.getMyfavoriteLocationboardList(memberVo.getMember_no());
		Map<String, Object> map = new HashMap<String, Object>();

		RouteboardVo routeboardVo = new RouteboardVo();
		routeboardVo.setRouteboard_title(routeboard_title);

		map.put("routeboardVo", routeboardVo);

		model.addAttribute("routeboardData", map);
		model.addAttribute("myfavoriteLocationboardList", myfavoriteLocationboardList);

		return "routeboard/rt_write_content_page";

	}

	// 루트 공유 - 글쓰기 처리
	@RequestMapping("/rt_write_content_process.do")
	public String writeContentProcess(@RequestParam(required = true) int[] locationboard_no,
			@RequestParam(required = true) int[] link_cost, @RequestParam(required = true) int[] link_order,
			RouteboardVo routeboardVo, @RequestParam(required = true) int[] link_time_hour,
			@RequestParam(required = true) int[] link_time_minute, HttpSession session, HttpServletResponse response)
			throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		// 로그인 안된 상태
		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

			// 제목, 내용 입력 안할 때
		} else if (routeboardVo.getRouteboard_title() == null || routeboardVo.getRouteboard_title().equals("")
				|| routeboardVo.getRouteboard_content() == null || routeboardVo.getRouteboard_content().equals("")) {

			System.out.println("글쓰기 테스트: " + routeboardVo.getRouteboard_title());
			response.sendRedirect("../error/common_error_page.do");

		} else {
			routeboardVo.setMember_no(memberVo.getMember_no());

			String title = StringEscapeUtils.escapeHtml4(routeboardVo.getRouteboard_title());
			String content = StringEscapeUtils.escapeHtml4(routeboardVo.getRouteboard_content());
			routeboardVo.setRouteboard_title(title);
			routeboardVo.setRouteboard_content(content);
			System.out.println("title" + title);
			System.out.println("content" + content);
			// 시간, 비용 카테고리 삽입을 위해 위에서 받아온 routeboardVo로 글쓰기 처리한 후 다시 routeboardVo를 뽑아옴
			RouteboardVo returnRouteboardVo = routeboardService.writeContent(routeboardVo);

			int routeboard_cost = 0;
			int routeboard_time = 0;

			// 장소 하나도 등록 안했을 때
			if (locationboard_no.length == 0) {

				response.sendRedirect("../error/common_error_page.do");

			}
			for (int i = 0; i < locationboard_no.length; i++) {

				LocationboardVo locationboardVo = new LocationboardVo();
				locationboardVo.setLocationboard_no(locationboard_no[i]);
				routeboardVo.setRouteboard_cost(routeboard_cost);
				routeboardVo.setRouteboard_time(routeboard_time);

				// 시간 분으로 바꿔서 삽입
				int link_time = link_time_hour[i] * 60 + link_time_minute[i];

				LinkVo linkVo = new LinkVo();
				linkVo.setLink_order(link_order[i]);
				linkVo.setLink_cost(link_cost[i]);
				linkVo.setLink_time(link_time);

				routeboard_cost += linkVo.getLink_cost();
				routeboard_time += linkVo.getLink_time();

				routeboardService.linkLocation(linkVo, locationboardVo, routeboardVo);

			}

			returnRouteboardVo.setRouteboard_cost(routeboard_cost);
			returnRouteboardVo.setRouteboard_time(routeboard_time);

			if (routeboard_cost < 30000) {
				returnRouteboardVo.setCost_category_no(1);
			} else if (routeboard_cost >= 30000 && routeboard_cost < 60000) {
				returnRouteboardVo.setCost_category_no(2);
			} else if (routeboard_cost >= 60000 && routeboard_cost < 90000) {
				returnRouteboardVo.setCost_category_no(3);
			} else if (routeboard_cost >= 90000) {
				returnRouteboardVo.setCost_category_no(4);
			}

			if (routeboard_time < 180) {
				returnRouteboardVo.setTime_category_no(1);
			} else if (routeboard_time >= 180 && routeboard_time < 360) {
				returnRouteboardVo.setTime_category_no(2);
			} else if (routeboard_time >= 360 && routeboard_time < 540) {
				returnRouteboardVo.setTime_category_no(3);
			} else if (routeboard_time >= 540) {
				returnRouteboardVo.setTime_category_no(4);
			}

			routeboardService.rewriteContent(returnRouteboardVo);

			return "redirect:/routeboard/rt_main_page.do";

		}
		return null;
	}

	// 루트 공유 - 글 읽기
	@RequestMapping("/rt_read_content_page.do")
	public String readContentPage(@RequestParam(required = true) int routeboard_no, Model model, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		// 글쓴이 member_no 불러오기 & 있지도 않은 게시물 번호 입력 걸러내기
		int member_no_check = routeboardService.getMemberNo(routeboard_no);
		System.out.println("글쓴이 번호: " + member_no_check);

		if (member_no_check == 0) {

			response.sendRedirect("../error/error400_page.do");

		} else {

			// 좋아요 출력 - 로그인/비로그인 구분(member_no=0 : 비로그인)
			int member_no = 0;
			if (memberVo == null) {
				member_no = 0;
			} else {
				member_no = memberVo.getMember_no();
			}

			// 게시물 1건에 대한 좋아요 수
			int like_count = routeboardService.likeCount(routeboard_no);

			Map<String, Object> map = routeboardService.readRouteContent(routeboard_no, member_no, response, request);
			List<Map<String, Object>> list = routeboardService.readLocationContent(routeboard_no, member_no);

			RouteboardVo routeboardVo = (RouteboardVo) map.get("routeboardVo");
			
			routeboardVo
			.setRouteboard_content(routeboardVo.getRouteboard_content().replace("\r\n", "<br>"));
			
			model.addAttribute("routeboardData", map);
			model.addAttribute("locationboardDataList", list);
			model.addAttribute("route_like_count", like_count);

			return "/routeboard/rt_read_content_page";
		}
		return "/routeboard/rt_read_content_page";

	}

	// 루트 공유 - 글 수정 페이지
	@RequestMapping("/rt_update_content_page.do")
	public String updateContentPage(HttpSession session, Model model, int routeboard_no, HttpServletResponse response,
			HttpServletRequest request) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		int member_no = 0;
		if (memberVo == null) {
			member_no = 0;
		} else {
			member_no = memberVo.getMember_no();
		}

		List<Map<String, Object>> myfavoriteLocationboardList = routeboardService
				.getMyfavoriteLocationboardList(memberVo.getMember_no());

		model.addAttribute("myfavoriteLocationboardList", myfavoriteLocationboardList);

		Map<String, Object> map = routeboardService.readRouteContent(routeboard_no, member_no, response, request);
		List<Map<String, Object>> list = routeboardService.readLocationContent(routeboard_no, member_no);

		model.addAttribute("routeboardData", map);
		model.addAttribute("locationboardDataList", list);

		return "routeboard/rt_update_content_page";

	}

	// 루트 공유 - 글 삭제
	@RequestMapping("/rt_delete_content_process.do")
	public String deleteContentProcess(int routeboard_no) {

		routeboardService.deleteContent(routeboard_no);

		return "redirect:/routeboard/rt_main_page.do";
	}

	// 루트 공유 - 글 수정 처리
	@RequestMapping("/rt_update_content_process.do")
	public String updateContentProcess(@RequestParam(required = true) int[] locationboard_no,
			@RequestParam(required = true) int[] link_cost, @RequestParam(required = true) int[] link_order,
			RouteboardVo routeboardVo, @RequestParam(required = true) int[] link_time_hour,
			@RequestParam(required = true) int[] link_time_minute, HttpSession session, HttpServletResponse response)
			throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		// 로그인 안된 상태
		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

			// 제목, 내용 입력 안할 때
		} else if (routeboardVo.getRouteboard_title() == null || routeboardVo.getRouteboard_title().equals("")
				|| routeboardVo.getRouteboard_content() == null || routeboardVo.getRouteboard_content().equals("")) {

			System.out.println("글쓰기 테스트: " + routeboardVo.getRouteboard_title());
			response.sendRedirect("../error/common_error_page.do");

		} else {
			routeboardVo.setMember_no(memberVo.getMember_no());

			String title = StringEscapeUtils.escapeHtml4(routeboardVo.getRouteboard_title());
			String content = StringEscapeUtils.escapeHtml4(routeboardVo.getRouteboard_content());
			routeboardVo.setRouteboard_title(title);
			routeboardVo.setRouteboard_content(content);
			System.out.println("title" + title);
			System.out.println("content" + content);
			// 시간, 비용 카테고리 삽입을 위해 위에서 받아온 routeboardVo로 글 수정 처리한 후 다시 routeboardVo를 뽑아옴
			RouteboardVo returnRouteboardVo = routeboardService.updateContent(memberVo, routeboardVo);

			int routeboard_cost = 0;
			int routeboard_time = 0;

			// 장소 하나도 등록 안했을 때
			if (locationboard_no.length == 0) {

				response.sendRedirect("../error/common_error_page.do");

			}

			// 링크 리셋 : 루트 글 업데이트를 위하여
			routeboardService.resetLink(returnRouteboardVo);

			for (int i = 0; i < locationboard_no.length; i++) {

				LocationboardVo locationboardVo = new LocationboardVo();
				locationboardVo.setLocationboard_no(locationboard_no[i]);
				routeboardVo.setRouteboard_cost(routeboard_cost);
				routeboardVo.setRouteboard_time(routeboard_time);

				// 시간 분으로 바꿔서 삽입
				int link_time = link_time_hour[i] * 60 + link_time_minute[i];

				LinkVo linkVo = new LinkVo();
				linkVo.setLink_order(link_order[i]);
				linkVo.setLink_cost(link_cost[i]);
				linkVo.setLink_time(link_time);

				routeboard_cost += linkVo.getLink_cost();
				routeboard_time += linkVo.getLink_time();

				routeboardService.linkLocation(linkVo, locationboardVo, routeboardVo);

			}

			returnRouteboardVo.setRouteboard_cost(routeboard_cost);
			returnRouteboardVo.setRouteboard_time(routeboard_time);

			if (routeboard_cost < 30000) {
				returnRouteboardVo.setCost_category_no(1);
			} else if (routeboard_cost >= 30000 && routeboard_cost < 60000) {
				returnRouteboardVo.setCost_category_no(2);
			} else if (routeboard_cost >= 60000 && routeboard_cost < 90000) {
				returnRouteboardVo.setCost_category_no(3);
			} else if (routeboard_cost >= 90000) {
				returnRouteboardVo.setCost_category_no(4);
			}

			if (routeboard_time < 180) {
				returnRouteboardVo.setTime_category_no(1);
			} else if (routeboard_time >= 180 && routeboard_time < 360) {
				returnRouteboardVo.setTime_category_no(2);
			} else if (routeboard_time >= 360 && routeboard_time < 540) {
				returnRouteboardVo.setTime_category_no(3);
			} else if (routeboard_time >= 540) {
				returnRouteboardVo.setTime_category_no(4);
			}

			routeboardService.rewriteContent(returnRouteboardVo);

			return "redirect:/routeboard/rt_main_page.do";

		}
		return null;
	}

	// 루트 공유 - 댓글쓰기 처리
	@RequestMapping("/rt_write_reply_process.do")
	@ResponseBody
	public void writeReplyProcess(@RequestParam(required = true) int routeboard_no,
			@RequestParam(required = true) String routeboard_reply_content, HttpSession session,
			HttpServletResponse response) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

		} else {

			routeboardService.writeReply(memberVo.getMember_no(), routeboard_no, routeboard_reply_content);
		}
	}

	// 루트 공유 - 댓글 목록 불러오기
	@RequestMapping("rt_get_reply_list.do")
	@ResponseBody
	public List<Map<String, Object>> getReplyList(HttpSession session, int routeboard_no) {

		int member_no = 0;

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		// 줗아요 출력 - 로그인/비로그인
		if (memberVo == null) {

			member_no = 0;
		} else {

			member_no = memberVo.getMember_no();
		}

		List<Map<String, Object>> list = routeboardService.getRepleList(routeboard_no, member_no);

		return list;
	}

	// 루트 공유 - 댓글 삭제
	@RequestMapping("rt_delete_reply_process.do")
	@ResponseBody
	public void deleteReply(HttpSession session, HttpServletResponse response,
			@RequestParam(required = true) int routeboard_reply_no) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

		} else {
			System.out.println("세션 member_no: " + memberVo.getMember_no());
			// 댓글쓴사람 member_no 가져오기 & 있지도 않은 댓글 번호 입력 걸러내기
			int member_no = routeboardService.getReplyMemberNo(routeboard_reply_no);
			System.out.println("댓글 작성자 member_no: " + member_no);
			if (member_no == 0) {

				response.sendRedirect("../error/common_error_page.do");

				// 다른 사람이 남의 댓글 삭제 시도할 때
			} else if (memberVo.getMember_no() != member_no) {

				System.out.println("세션 번호와 댓글 작성자 번호 다름");

			} else {
				routeboardService.deleteReply(routeboard_reply_no);
			}
		}
	}

	// 루트 공유 - 댓글 수정
	@RequestMapping("rt_update_reply_process.do")
	@ResponseBody
	public void updateReply(int routeboard_reply_no, String routeboard_reply_content, HttpServletResponse response,
			HttpSession session) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

		} else {
			System.out.println("세션 member_no: " + memberVo.getMember_no());
			// 댓글 쓴 사람 member_no 가져오기 & 있지도 않은 댓글 번호 입력 걸러내기
			int member_no = routeboardService.getReplyMemberNo(routeboard_reply_no);
			System.out.println("작성자 member_no" + member_no);
			if (member_no == 0) {

				response.sendRedirect("../error/common_error_page.do");

				// 다른 사람이 남의 댓글 수정 시도할 때
			} else if (memberVo.getMember_no() != member_no) {

				System.out.println("세션  - 작성자 번호 불일치");
				response.sendRedirect("../error/no_authority_page.do");

			} else {

				routeboardService.updateReply(routeboard_reply_content, routeboard_reply_no);
			}
		}
	}

	// 루트 게시판 - 좋아요 처리, 좋아요 수
	@RequestMapping("/like_process.do")
	@ResponseBody
	public Map<String, Object> likeProcess(int routeboard_no, HttpSession session) {

		int check_member_no = 0;
		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo != null) {
			check_member_no = routeboardService.likeCheck(routeboard_no, memberVo.getMember_no());
		}
		if (check_member_no != 0) {

			int count = routeboardService.likeCount(routeboard_no);
			String value = "true";

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("count", count);
			map.put("value", value);

			return map; // 좋아요 이미 함 -> 빈 하트로 바뀌어야 함

		} else {

			int count = routeboardService.likeCount(routeboard_no);
			String value = "false";

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("count", count);
			map.put("value", value);

			return map; // 좋아요한 적 없음 -> 빨간 하트로 바뀌어야 함
		}
	}

	// 루트 게시판 - 좋아요 전체 출력
	@RequestMapping("/like_all_process.do")
	@ResponseBody
	public List<RouteboardLikeVo> likeAllProcess(HttpSession session) {

		List<RouteboardLikeVo> list = new ArrayList<RouteboardLikeVo>();

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		if (memberVo != null) {
			list = routeboardService.likeAllCheck(memberVo.getMember_no());
		}

		return list;
	}

	// 루트 공유 - 관심 장소 게시글 폼에 가져오기
	@ResponseBody
	@RequestMapping("/call_my_favorite_location_content_page.do")
	public Map<String, Object> callMyfavoriteLocationContentPage(int locationboard_no, HttpServletResponse response,
			HttpServletRequest request) {

		System.out.println("관심 장소 게시글 번호 : " + locationboard_no);

		Map<String, Object> map = locationboardService.readContent(locationboard_no, response, request);

		return map;

	}

	@RequestMapping(value = "/favorite_process.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> favoriteProcess(HttpServletRequest request, HttpSession session) {

		int routeboard_no = Integer.parseInt(request.getParameter("routeboard_no"));

		System.out.println(routeboard_no);

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {
			memberVo.setMember_no(0);
		}

		Map<String, Object> map = routeboardService.favoriteCheck(memberVo.getMember_no(), routeboard_no);

		map.put("routeboard_no", routeboard_no);

		return map;

	}
}
