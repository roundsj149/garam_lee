package com.explorer.routemap.board.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.explorer.routemap.board.service.*;
import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.uploadfile.vo.LocationboardUploadFileVo;

@Controller
@RequestMapping("/locationboard/*")
public class LocationboardController {

	@Autowired
	LocationboardServiceImpl locationboardService;

	// 장소 게시판 - 글쓰기 페이지
	@RequestMapping("/lc_write_content_page.do")
	public String writeContentPage(HttpSession session, HttpServletResponse response) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {
			response.sendRedirect("../error/no_authority_page.do");
		}

		return "locationboard/lc_write_content_page";
	}

	// 장소 게시판 - 글쓰기 처리
	@RequestMapping("/lc_write_content_process.do")
	public String writeContentProcess(LocationboardVo locationboardVo,
			@RequestParam(required = true) int locationboard_time_hour,
			@RequestParam(required = true) int locationboard_time_minute, HttpSession session,
			MultipartFile[] upload_files, HttpServletResponse response) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

		} else if (locationboardVo.getLocationboard_title() == null
				|| locationboardVo.getLocationboard_title().equals("")
				|| locationboardVo.getLocationboard_content() == null
				|| locationboardVo.getLocationboard_content().equals("")) {

			System.out.println("글쓰기 테스트: " + locationboardVo.getLocationboard_title());
			response.sendRedirect("../error/common_error_page.do");

		} else {

			// 시간 분으로 바꿔서 삽입
			int locationboard_time = locationboard_time_hour * 60 + locationboard_time_minute;

			locationboardVo.setLocationboard_time(locationboard_time);

			// 시간 카테고리
			if (locationboard_time < 60) {
				locationboardVo.setTime_category_no(1);
			}
			if (locationboard_time >= 60 && locationboard_time < 180) {
				locationboardVo.setTime_category_no(2);
			}
			if (locationboard_time >= 180 && locationboard_time < 300) {
				locationboardVo.setTime_category_no(3);
			}
			if (locationboard_time >= 300) {
				locationboardVo.setTime_category_no(4);
			}

			// 비용 카테고리
			int cost = locationboardVo.getLocationboard_cost();

			if (cost < 10000) {
				locationboardVo.setCost_category_no(1);
			}
			if (cost >= 10000 && cost < 30000) {
				locationboardVo.setCost_category_no(2);
			}
			if (cost >= 30000 && cost < 50000) {
				locationboardVo.setCost_category_no(3);
			}
			if (cost >= 50000) {
				locationboardVo.setCost_category_no(4);
			}

			// 주소 안나오는 곳 있음 - 예외처리
			String addressCut = "";

			if (locationboardVo.getLocationboard_storeaddress().equals("")) {
				locationboardVo.setLocationboard_storeaddress("-");
				addressCut = "정보없음";

			} else {

				addressCut = locationboardVo.getLocationboard_storeaddress().substring(0, 2);
				System.out.println("addressCut" + addressCut);

			}

			// 제목, 내용 특수문자 치환
			String title = StringEscapeUtils.escapeHtml4(locationboardVo.getLocationboard_title());
			String content = StringEscapeUtils.escapeHtml4(locationboardVo.getLocationboard_content());
			locationboardVo.setLocationboard_title(title);
			locationboardVo.setLocationboard_content(content);
			locationboardVo.setMember_no(memberVo.getMember_no());
			locationboardService.writeContent(locationboardVo, addressCut);

			if(upload_files == null) {
				
				response.sendRedirect("../error/common_error_page.do");
				
			} else {
				
				for (MultipartFile file : upload_files) {
					if (file.getOriginalFilename().isEmpty()) {

						continue;

					} else {

						locationboardService.uploadfiles(file, locationboardVo.getLocationboard_no());
					}
				}
			}
			return "redirect:/locationboard/lc_main_page.do";

		}
		return null;
	}

	// lc_main_process
	@RequestMapping("/lc_main_process.do")
	@ResponseBody
	public List<Map<String, Object>> locationboardMainProcess(HttpSession session, Model model,
			@RequestParam(value = "provinceCheckboxArr[]", required = false, defaultValue = "0") int[] province_checkbox_no,
			@RequestParam(value = "categoryNum", required = false, defaultValue = "0") int category_no,
			@RequestParam(value = "costCheckboxArr[]", required = false, defaultValue = "0") int[] cost_checkbox_no,
			@RequestParam(value = "typeCheckboxArr[]", required = false, defaultValue = "0") int[] type_checkbox_no,
			@RequestParam(value = "timeCheckboxArr[]", required = false, defaultValue = "0") int[] time_checkbox_no,
			@RequestParam(value = "moodCheckboxArr[]", required = false, defaultValue = "0") int[] mood_checkbox_no,
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
		list = locationboardService.getSpecificBoardList(member_no, province_checkbox_no, category_no, cost_checkbox_no,
				type_checkbox_no, time_checkbox_no, mood_checkbox_no, align_no, searchWord, currPage);

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

	@RequestMapping("/lc_main_page.do")
	public String locationboardMainPage(HttpSession session, Model model,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage, String search_word) {

		return "locationboard/lc_main_page";
	}

	// 장소 게시판 - 글 읽기 페이지
	@RequestMapping("/lc_read_content_page.do")
	public String readContentPage(@RequestParam(required = true) int locationboard_no, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		// 글쓴이 member_no 불러오기 & 있지도 않은 게시물 번호 입력 걸러내기
		int member_no_check = locationboardService.getMemberNo(locationboard_no);
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

			LocationboardMyfavoriteVo farVo = locationboardService.getfavoirte(member_no, locationboard_no);

			Map<String, Object> map = locationboardService.readContent(locationboard_no, response, request);
			LocationboardVo locationboardVo = (LocationboardVo) map.get("locationboardVo");

			locationboardVo
					.setLocationboard_content(locationboardVo.getLocationboard_content().replace("\r\n", "<br>"));

			// 게시물 1건에 대한 좋아요 수
			int like_count = locationboardService.likeCount(locationboard_no);

			// jsp - data
			model.addAttribute("data", map);
			model.addAttribute("like_count", like_count);
			model.addAttribute("farVo", farVo);

			return "locationboard/lc_read_content_page";
		}
		return "locationboard/lc_read_content_page";
	}

	// 장소 게시판 - 글 삭제 처리
	@RequestMapping("/lc_delete_process.do") // required=true로 설정해서 글번호 없이 주소 접속 시 400번 에러
	public String deleteContentProcess(HttpSession session, @RequestParam(required = true) int locationboard_no,
			HttpServletResponse response) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/common_error_page.do");

		} else {

			// 글쓴이 member_no 불러오기 & 있지도 않은 게시물 번호 입력 걸러내기
			int member_no = locationboardService.getMemberNo(locationboard_no);
			System.out.println("글쓴이 번호: " + member_no);

			if (member_no == 0) {

				response.sendRedirect("../error/error400_page.do");

				// 세션 번호와 글쓴이 번호 다를 경우
			} else if (memberVo.getMember_no() != member_no) {

				System.out.println("세션 번호와 글쓴이번호 다름");

				response.sendRedirect("../error/no_authority_page.do");

			} else { // 글쓴이 자신
				System.out.println("내가 쓴 글 내가 삭제");
				locationboardService.deleteContent(locationboard_no);

				return "redirect:/locationboard/lc_main_page.do";
			}
		}
		return null;
	}

	// 장소 게시판 - 글 수정 페이지
	@RequestMapping("/lc_update_content_page.do")
	public String updateContentPage(HttpSession session, @RequestParam(required = true) int locationboard_no,
			Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		// 로그인 안한 경우
		if (memberVo == null) {

			response.sendRedirect("../error/common_error_page.do");
		}

		else {

			// 글쓴이 member_no 불러오기 & 있지도 않은 게시물 번호 입력 걸러내기
			int member_no = locationboardService.getMemberNo(locationboard_no);
			System.out.println("글쓴이 번호: " + member_no);

			if (member_no == 0) {

				response.sendRedirect("../error/error400_page.do");

				// 세션 번호와 글쓴이 번호가 다를 경우
			} else if (memberVo.getMember_no() != member_no) {

				System.out.println("세션 번호와 글쓴이번호 다름");

				response.sendRedirect("../error/no_authority_page.do");

			} else { // 글쓴이 자신
				Map<String, Object> map = locationboardService.readContent(locationboard_no, response, request);
				LocationboardVo lv = (LocationboardVo) map.get("locationboardVo");
				System.out.println("타이틀" + lv.getLocationboard_title());
				// jsp - data
				model.addAttribute("data", map);

				return "locationboard/lc_update_content_page";
			}
		}
		return null;
	}

	// 장소 게시판 - 글 수정 처리
	@RequestMapping("/lc_update_content_process.do")
	public String updateContentProcess(String[] delete, @RequestParam(required = true) int locationboard_no,
			LocationboardVo locationboardVo, @RequestParam(required = true) int locationboard_time_hour,
			@RequestParam(required = true) int locationboard_time_minute, MultipartFile[] upload_files,
			HttpSession session, HttpServletResponse response) throws IOException {
		System.out.println("비용 카테고리: " + locationboardVo.getCost_category_no());
		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/common_error_page.do");

		} else if (locationboardVo.getLocationboard_title() == null
				|| locationboardVo.getLocationboard_title().equals("")
				|| locationboardVo.getLocationboard_content() == null
				|| locationboardVo.getLocationboard_content().equals("")) {

			System.out.println("글수정 테스트: " + locationboardVo.getLocationboard_title());
			response.sendRedirect("../error/common_error_page.do");

		} else {

			// 글쓴이 member_no 불러오기 & 있지도 않은 게시물번호 입력 걸러내기
			int member_no = locationboardService.getMemberNo(locationboard_no);
			System.out.println("글쓴이 번호: " + member_no);

			// 없는 게시물 수정 처리 요청할 경우
			if (member_no == 0) {

				response.sendRedirect("../error/error400_page.do");

				// 세션 번호와 글쓴이 번호가 다를 경우
			} else if (memberVo.getMember_no() != member_no) {

				System.out.println("세션 번호와 글쓴이번호 다름");

				response.sendRedirect("../error/no_authority_page.do");

			} else {

				// 시간 분으로 바꿔서 삽입
				int locationboard_time = locationboard_time_hour * 60 + locationboard_time_minute;
				locationboardVo.setLocationboard_no(locationboard_no);
				locationboardVo.setLocationboard_time(locationboard_time);

				// 시간 카테고리
				if (locationboard_time < 60) {
					locationboardVo.setTime_category_no(1);
				}
				if (locationboard_time >= 60 && locationboard_time < 180) {
					locationboardVo.setTime_category_no(2);
				}
				if (locationboard_time >= 180 && locationboard_time < 300) {
					locationboardVo.setTime_category_no(3);
				}
				if (locationboard_time >= 300) {
					locationboardVo.setTime_category_no(4);
				}

				// 비용 카테고리
				int cost = locationboardVo.getLocationboard_cost();

				if (cost < 10000) {
					locationboardVo.setCost_category_no(1);
				}
				if (cost >= 10000 && cost < 30000) {
					locationboardVo.setCost_category_no(2);
				}
				if (cost >= 30000 && cost < 50000) {
					locationboardVo.setCost_category_no(3);
				}
				if (cost >= 50000) {
					locationboardVo.setCost_category_no(4);
				}

				// 주소 안나오는 곳 있음 - 예외처리
				String addressCut = "";

				if (locationboardVo.getLocationboard_storeaddress().equals("")
						|| locationboardVo.getLocationboard_storeaddress().equals("-")) {
					locationboardVo.setLocationboard_storeaddress("-");
					addressCut = "정보없음";

				} else {

					addressCut = locationboardVo.getLocationboard_storeaddress().substring(0, 2);

				}

				// 제목, 내용 특수문자 치환
				String title = StringEscapeUtils.escapeHtml4(locationboardVo.getLocationboard_title());
				String content = StringEscapeUtils.escapeHtml4(locationboardVo.getLocationboard_content());
				locationboardVo.setLocationboard_title(title);
				locationboardVo.setLocationboard_content(content);
				locationboardVo.setMember_no(memberVo.getMember_no());
				locationboardService.updateContent(locationboardVo, addressCut);

				if(upload_files == null) {
					
					response.sendRedirect("../error/common_error_page.do");
					
				} else {
					// 새로 추가된 파일 처리
					for (MultipartFile file : upload_files) {
						if (file.getOriginalFilename().isEmpty()) {

							continue;

						} else {

							locationboardService.uploadfiles(file, locationboardVo.getLocationboard_no());
						}
					}

					// 삭제된 파일 처리
					if (delete != null) {
						for (String file_no : delete) {
							locationboardService.deleteFile(file_no);
						}
					}
				}

				return "redirect:/locationboard/lc_read_content_page.do?locationboard_no=" + locationboard_no;
			}
		}
		return null;
	}

	// 장소 게시판 - 댓글 쓰기 처리
	@RequestMapping("/lc_write_reply_process.do")
	@ResponseBody
	public void writeReplyProcess(@RequestParam(required = true) int locationboard_no,
			LocationboardReplyVo locationboardReplyVo, HttpSession session, HttpServletResponse response)
			throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

		} else {

			// 있지도 않은 게시물 번호 입력 걸러내기
			int member_no = locationboardService.getMemberNo(locationboard_no);

			if (member_no == 0) {

				response.sendRedirect("../error/common_error_page.do");

			} else {
				
				locationboardReplyVo.setMember_no(memberVo.getMember_no());
				System.out.println("locationboard_no"+locationboard_no);
				locationboardService.writeReply(locationboard_no, locationboardReplyVo);
			}

		}
	}

	// 장소 게시판 - 댓글 목록
	@RequestMapping("/lc_get_reply_list.do")
	@ResponseBody
	public List<Map<String, Object>> getReplyList(HttpSession session, int locationboard_no) {

		int member_no;
		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo != null) {
			member_no = memberVo.getMember_no();
		} else {
			member_no = 0;
		}

		List<Map<String, Object>> list = locationboardService.getRepleList(locationboard_no, member_no);

		return list;

	}

	// 장소 게시판 - 댓글 삭제 처리
	@RequestMapping("/lc_delete_reply_process.do")
	@ResponseBody
	public void deleteReplyProcess(HttpSession session, HttpServletResponse response,
			@RequestParam(required = true) int locatinoboard_reply_no) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

		} else {
			System.out.println("세션 member_no: " + memberVo.getMember_no());
			// 댓글쓴사람 member_no 가져오기 &있지도 않은 댓글 번호 입력 걸러내기
			int member_no = locationboardService.getReplyMemberNo(locatinoboard_reply_no);
			System.out.println("댓글 작성자 member_no: " + member_no);
			if (member_no == 0) {

				response.sendRedirect("../error/common_error_page.do");

				// 다른 사람이 남의 댓글 삭제 시도할 때
			} else if (memberVo.getMember_no() != member_no) {

				System.out.println("세션 번호와 댓글 작성자 번호 다름");
				response.sendRedirect("../error/no_authority_page.do");
				System.out.println("리다이렉트는 왜안되지ㅡㅡ");

			} else {

				System.out.println("댓글 삭제");
				locationboardService.deleteReply(locatinoboard_reply_no);

			}
		}
	}

	// 장소 게시판 - 댓글 수정 처리
	@RequestMapping("/lc_update_reply_process.do")
	@ResponseBody
	public void updateReplyProcess(HttpSession session, HttpServletResponse response,
			LocationboardReplyVo locationboardReplyVo) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			response.sendRedirect("../error/no_authority_page.do");

		} else {
			System.out.println("세션 member_no: " + memberVo.getMember_no());
			// 댓글 쓴 사람 member_no 가져오기 & 있지도 않은 댓글 번호 입력 걸러내기
			int member_no = locationboardService.getReplyMemberNo(locationboardReplyVo.getLocatinoboard_reply_no());
			System.out.println("작성자 member_no" + member_no);
			if (member_no == 0) {

				response.sendRedirect("../error/common_error_page.do");

				// 다른 사람이 남의 댓글 수정 시도할 때
			} else if (memberVo.getMember_no() != member_no) {

				System.out.println("세션  - 작성자 번호 불일치");
				response.sendRedirect("../error/no_authority_page.do");

			} else {
				System.out.println("세선 - 작성자 번호 일치");
				locationboardService.updateReply(locationboardReplyVo);

			}
		}

	}

	// 장소 게시판 - 좋아요 처리, 좋아요 수
	//
	@RequestMapping("/like_process.do")
	@ResponseBody
	public Map<String, Object> likeProcess(@RequestParam(required = true) int locationboard_no, HttpSession session) {
		
		System.out.println("ㅅㄷㄴㅅ");
		int check_member_no = 0;

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		// 로그인 했으면
		if (memberVo != null) {

			check_member_no = locationboardService.likeCheck(locationboard_no, memberVo.getMember_no());
			System.out.println("ㄹㄹ"+locationboard_no);
			System.out.println("check_member_no"+check_member_no);

		}

		// 체크 안했으면
		if (check_member_no != 0) {

			int count = locationboardService.likeCount(locationboard_no);
			String value = "true";

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("count", count);
			map.put("value", value);

			return map; // 좋아요 이미 함 -> 빈 하트로 바뀌어야 함

		} else {

			int count = locationboardService.likeCount(locationboard_no);
			String value = "false";

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("count", count);
			map.put("value", value);

			return map; // 좋아요한 적 없음 -> 빨간 하트로 바뀌어야 함
		}
	}

	// 장소 게시판 - 좋아요 전체 출력
	@RequestMapping("/like_all_process.do")
	@ResponseBody
	public List<LocationboardLikeVo> likeAllProcess(HttpSession session) {

		List<LocationboardLikeVo> list = new ArrayList<LocationboardLikeVo>();

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo != null) {
			list = locationboardService.likeAllCheck(memberVo.getMember_no());
		}

		return list;
	}

	// 장소 게시판 - 관심깃발 등록,삭제
	@RequestMapping(value = "/favorite_process.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> favoriteProcess(HttpServletRequest request, HttpSession session) {

		int locationboard_no = Integer.parseInt(request.getParameter("locationboard_no"));

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		Map<String, Object> map = locationboardService.favoriteCheck(memberVo.getMember_no(), locationboard_no);

		map.put("locationboard_no", locationboard_no);

		return map;
	}

}
