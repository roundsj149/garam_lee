package com.explorer.routemap.member.controller;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.explorer.routemap.admin.service.AdminServiceImpl;
import com.explorer.routemap.admin.vo.AdminVo;
import com.explorer.routemap.member.service.*;
import com.explorer.routemap.member.vo.*;
import com.explorer.routemap.util.*;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Autowired
	MemberServiceImpl memberService;
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	AdminServiceImpl adminService;

	// 로그인 페이지
	@RequestMapping("/login_page.do")
	public String loginPage(HttpServletRequest request, Model model, HttpSession session) {
		// 이미 로그인한 상태에서 로그인 페이지 접근 시
		if (session.getAttribute("sessionUser") != null) {

			return "redirect:/error/session_exist_page.do";
		}
		String redirectPage = request.getParameter("uri");

		// 로그인 처리의 로그인 5회 실패쪽에서 보내는 값 받기
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			System.out.println("날아온 번호" + String.valueOf(flashMap.get("member_login_trial")));
			model.addAttribute("member_login_trial", String.valueOf(flashMap.get("member_login_trial")));
		}

		// js에서 날아온 uri값 받아서 로그인 페이지에 보내줌
		model.addAttribute("redirectPage", redirectPage);

		return "member/login_page";
	}

	// 로그인 처리
	@RequestMapping(value = "/login_process.do", method = RequestMethod.POST)
	public String loginProcess(@RequestParam(required = true) String insert_id,
			@RequestParam(required = true) String insert_pw, HttpSession session, HttpServletResponse response,
			String useCookie, Model model, HttpServletRequest request, String redirectPage) throws IOException {

		if (session.getAttribute("sessionUser") != null) {

			// 기존에 login이란 세션 값이 존재한다면
			session.removeAttribute("sessionUser");// 기존값을 제거해 준다.
		}

		if (insert_id != null && insert_pw != null) {

			// 1) 아이디는 맞고 비밀번호는 틀린 경우 memberVo에 값이 담김
			MemberVo memberVo = memberService.loginTrial(insert_id, insert_pw);

			// 비밀번호 틀린 회수가 5회 미만인 경우
			if (memberVo != null && memberVo.getMember_login_trial() < 5 && memberVo.getMember_status().equals("Y")) {
				System.out.println("사용자 입력 아이디" + insert_id);
				System.out.println("사용지 입력 비밀번호" + insert_pw);
				// 로그인 실패회수 1 증가
				memberService.loginTrialCount(memberVo.getMember_id());

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("member_login_trial", 0);

				return "redirect:/member/login_page.do";

				// 비밀번호 틀린 회수가 5회 이상인 경우
			} else if (memberVo != null && memberVo.getMember_login_trial() >= 5
					&& memberVo.getMember_status().equals("Y")) {

				System.out.println(memberVo.getMember_login_trial());

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("member_login_trial", memberVo.getMember_login_trial());

				return "redirect:/member/login_page.do";

			}

			// 2) 아이디와 비밀번호 모두 맞는 경우 userData에 값이 담김
			MemberVo userData = memberService.login(insert_id, insert_pw);

			if (userData == null) { // 로그인 실패

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("member_login_trial", -1);

				return "redirect:/member/login_page.do";

			} else if (userData != null && (userData.getMember_status().equals("N") || userData.getMember_status().equals("S"))) {
				System.out.println(userData.getMember_status());

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("member_login_trial", -10);

				return "redirect:/member/login_page.do";

			} else if (userData != null && userData.getMember_login_trial() >= 5) {

				// 캡차 문자와 비교
				String getAnswer = (String) request.getSession().getAttribute("captcha");
				String answer = request.getParameter("answer");

				// 캡차 문자와 사용자 입력 값이 같은 경우
				if (getAnswer.equals(answer)) {

					System.out.println("로그인 성공");
					session.setAttribute("sessionUser", userData); // 사용자 정보 session처리
					
					// 로그인 성공 시 로그인 누적 카운트 증가
					adminService.insertLoginData((MemberVo)session.getAttribute("sessionUser"));
					// 실시간 세션 생성
					SessionChecker.setSession(session);

					// 로그인 성공했으므로 로그인 시도회수 초기화
					memberService.resetTrialCount(userData.getMember_id());

					return "redirect:/main_page.do";

				} else {

					System.out.println("캡차 문자 틀렸음 - 로그인 실패");

					// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
					FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
					fm.put("member_login_trial", userData.getMember_login_trial());

					return "redirect:/member/login_page.do";
				}
			}

			else if ((userData != null && userData.getMember_login_trial() < 5)) { // 로그인 성공

				memberService.resetTrialCount(userData.getMember_id());
				System.out.println("로그인 성공한 사람 아이디: " + userData.getMember_id());
				System.out.println("로그인 성공: 로그인 시도 회수 - " + userData.getMember_login_trial());
				session.setAttribute("sessionUser", userData); // 사용자 정보 session처리

				if (useCookie != null && useCookie.equals("AAA")) {

					// 쿠키를 생성하고 현재 로그인되어 있을 때 생성되었던 세션의 id를 쿠키에 저장한다.
					Cookie cookie = new Cookie("loginCookie", session.getId());
					// 쿠키를 찾을 경로를 컨텍스트 경로로 변경해 주고...
					cookie.setPath("/");
					int amount = 60 * 60 * 24 * 7;
					cookie.setMaxAge(amount);// 단위는 (초)임으로 7일정도로 유효시간을 설정해 준다.
					// 쿠키를 적용해 준다.
					response.addCookie(cookie);

					// currentTimeMills()가 1/1000초 단위임으로 1000곱해서 더해야함
					Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));
					// 현재 세션 id와 유효시간을 사용자 테이블에 저장한다.
					memberService.keepLogin(userData.getMember_id(), session.getId(), sessionLimit);

				}

				if (redirectPage == null || redirectPage.equals("")) {
					
					// 로그인 성공 시 로그인 누적 카운트 증가
					adminService.insertLoginData((MemberVo)session.getAttribute("sessionUser"));
					// 실시간 세션 생성
					SessionChecker.setSession(session);
					
					redirectPage = "/main_page.do";

				} else {
					// jsp단으로부터 돌아갈 주소값이 저장된 hidden값을 받아서 redirect 주소를 설정해줌
					redirectPage = "../" + redirectPage;

				}
				return "redirect:" + redirectPage;
			}
		}
		return "member/login_fail";
	}

	// 회원가입 페이지
	@RequestMapping("/join_member_page.do")
	public String joinPage(HttpSession session) {

		// 이미 로그인한 상태에서 회원가입 페이지 접근 시
		if (session.getAttribute("sessionUser") != null) {

			return "redirect:/error/session_exist_page.do";
		}

		return "member/join_member_page";
	}

	// 회원 가입 처리
	@RequestMapping("/join_member_process.do")
	public String joinMemberProcess(MemberVo memberVo, @RequestParam(required = true) String year,
			@RequestParam(required = true) String month, @RequestParam(required = true) String day, HttpSession session,
			HttpServletResponse response, @RequestParam(required = true) String member_pw2) throws IOException {

		// 이미 로그인한 상태
		if (session.getAttribute("sessionUser") != null) {

			return "redirect:/error/common_error_page.do";

		}

		// 입력값이 널일 경우
		if (memberVo.getMember_id() == null || memberVo.getMember_fullname() == null
				|| memberVo.getMember_nickname() == null || memberVo.getMember_phone() == null
				|| memberVo.getMember_pw() == null) {

			return "redirect:/error/common_error_page.do";

		} else {

			String id = memberService.checkId(memberVo.getMember_id());
			String nick = memberService.checkNick(memberVo.getMember_nickname());
			if (id != null || nick != null) {
				System.out.println("중복된 아이디 또는 닉네임 존재");
				return "redirect:/error/common_error_page.do";
			}
			// 아이디 정규식 검사
			String idRegExp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.([a-zA-Z]{1,3})$";
			// 비밀번호 정규식 검사
			String pwRegExp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$";
			// 닉네임 정규식 검사
			String nickRegExp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{2,10}$";
			// 이름 정규식 검사
			String nameRegExp = "^[가-힣]{2,6}$";
			// 휴대전화번호 정규식검사
			String phoneRegExp = "^[0-9]{9,11}$";

			System.out.println("아이디 정규식 검사ㅡㅡ" + memberVo.getMember_id().matches(idRegExp));
			System.out.println("비밀번호 정규식 검사ㅡㅡ" + memberVo.getMember_pw().matches(pwRegExp));
			System.out.println("닉네임 정규식 검사ㅡㅡ" + memberVo.getMember_nickname().matches(nickRegExp));
			System.out.println("이름 정규식 검사ㅡㅡ" + memberVo.getMember_fullname().matches(nameRegExp));
			System.out.println("휴대전화번호 정규식 검사ㅡㅡ" + memberVo.getMember_phone().matches(phoneRegExp));

			if (memberVo.getMember_id().matches(idRegExp) == false || memberVo.getMember_pw().matches(pwRegExp) == false
					|| memberVo.getMember_nickname().matches(nickRegExp) == false
					|| memberVo.getMember_fullname().matches(nameRegExp) == false
					|| memberVo.getMember_phone().matches(phoneRegExp) == false) {

				System.out.println("아이디, 비밀번호, 닉네임, 이름 또는 휴대전화번호 형식 안맞음");
				response.sendRedirect("../error/common_error_page.do");

			} else if (!member_pw2.equals(memberVo.getMember_pw())) {

				System.out.println("비밀번호 불일치");
				response.sendRedirect("../error/common_error_page.do");

			} else {
				String member_birth = year + month + day;
				memberVo.setMember_birth(member_birth);
				// 회원 인증 생성
				MemberAuthVo memberAuthVo = new MemberAuthVo();
				String auth_key = UUID.randomUUID().toString();
				memberAuthVo.setAuth_key(auth_key);

				memberService.joinMember(memberVo, memberAuthVo);

				// 메일 발송 시작
				RMMailSenderThread rMMailSenderThread = new RMMailSenderThread(javaMailSender, memberVo.getMember_id(),
						memberAuthVo.getAuth_key());
				rMMailSenderThread.start();
				// 메일 발송 끝

				///////////////////

				return "member/join_member_complete_page";
			}
		}
		return null;
	}

	// 메일 인증 처리
	@RequestMapping("/certification_process.do")
	public String certicicationProcess(@RequestParam(required = true) String auth_key) {

		memberService.certification(auth_key);

		return "member/certification_complete";
	}

	// 인증 성공 페이지
	@RequestMapping("/certification_complete_page.do")
	public String certificationCompletePage() {

		return "member/certification_complete_page";

	}

	// 로그아웃 버튼 눌렀을 때 처리
	@RequestMapping("/logout_process.do")
	public String logoutProcess(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Object obj = session.getAttribute("sessionUser");

		if (obj == null) {
			System.out.println("로그인 안했을 때 로그아웃 처리 페이지 접근");
			return "redirect:/error/common_error_page.do";
		} else if (obj != null) {
			MemberVo memberVo = (MemberVo) obj;
			// null이 아닐 경우 제거
			session.removeAttribute("sessionUser");
			session.invalidate(); //세션 전체를 날려버림

			// 쿠키를 가져와보고
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if (loginCookie != null) {
				// null이 아니면 존재하면!
				loginCookie.setPath("/");
				// 쿠키는 없앨 때 유효시간을 0으로 설정하는 것 !!! invalidate같은거 없음.
				loginCookie.setMaxAge(0);
				// 쿠키 설정을 적용한다.
				response.addCookie(loginCookie);

				// 사용자 테이블에서도 유효기간을 현재시간으로 다시 세팅해줘야함.
				Date date = new Date(System.currentTimeMillis());
				memberService.keepLogin(memberVo.getMember_id(), session.getId(), date);
			}
		}
		return "redirect:/main_page.do";
	}

	// 아이디 중복확인
	@RequestMapping("checkId_process.do")
	@ResponseBody
	public String checkId(@RequestParam(required = true) String id, HttpServletResponse response) throws IOException {

		String get_id = memberService.checkId(id);

		if (get_id == null) { // 중복된 아이디가 없으면

			return "true";
		} else { // 중복된 아이디가 있으면

			return "false";
		}
	}

	// 닉네임 중복확인
	@RequestMapping("checkNick_process.do")
	@ResponseBody
	public String checkNick(@RequestParam(required = true) String nick, HttpServletResponse response)
			throws IOException {

		String get_nick = memberService.checkNick(nick);
		if (get_nick == null) { // 중복된 닉네임이 없으면

			return "true";
		} else { // 중복된 닉네임이 있으면

			return "false";
		}

	}

	// 마이페이지
	@RequestMapping("mypage.do")
	public String mypage(HttpSession session) {

		if (session.getAttribute("sessionUser") == null) {

			return "redirect:/error/no_authority_page.do";
		}
		return "member/mypage";
	}

	// 회원 정보 수정 페이지
	@RequestMapping("user_info_page.do")
	public String userInfoPage(HttpSession session, Model model) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			System.out.println("회원정보 수정 페이지 세션 없이 접근");
			return "redirect:/error/no_authority_page.do";
		} else {

			MemberVo newMemberVo = memberService.myInfoData(memberVo.getMember_no());
			model.addAttribute("memberVo", newMemberVo);
		}

		return "member/user_info_page";
	}

	// 회원 정보 수정 - 비밀번호 변경 페이지
	@RequestMapping("update_password_page.do")
	public String updatePasswordPage(HttpSession session) {

		if (session.getAttribute("sessionUser") == null) {

			return "redirect:/error/no_authority_page.do";
		}

		return "member/update_password_page";
	}

	// 회원 정보 수정 - 비밀번호 변경 시 비밀번호 확인
	@RequestMapping("checkPw_process.do")
	@ResponseBody
	public String checkPw(HttpSession session, @RequestParam(required = true) String pw, HttpServletResponse response)
			throws IOException {

		// 비밀번호 암호화
		String hashCode = RMMessageDigest.digest(pw);
		pw = hashCode;

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		if (memberVo == null) {

			System.out.println("회원정보 수정 - 비밀번호 변경 시 비밀번호 확인 페이지 세션 없이 접근");

			response.sendRedirect("../error/common_error_page.do");

		} else {
			MemberVo newMemberVo = memberService.myInfoData(memberVo.getMember_no());

			if (newMemberVo.getMember_pw().equals(pw)) {
				return "false";
			} else {
				return "true";
			}
		}
		return "false";
	}

	// 회원 정보 수정 - 비밀번호 변경 처리
	@RequestMapping("update_pw_process.do")
	public String updatePwProcess(HttpSession session, @RequestParam(required = true) String current_pw, String new_pw,
			String new_pw2, HttpServletResponse response, HttpServletRequest request) throws IOException {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		// 로그인 안한 상태
		if (memberVo == null) {

			return "redirect:/error/common_error_page.do";

		} else {

			MemberVo newMemberVo = memberService.myInfoData(memberVo.getMember_no());

			// 비밀번호 유효성 검사
			String pwRegExp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$";
			System.out.println("비밀번호 정규식 검사ㅡㅡ" + new_pw.matches(pwRegExp));

			if (new_pw.matches(pwRegExp) == false) {
				System.out.println("비밀번호 형식 x");
				response.sendRedirect("../error/common_error_page.do");
			} else {
				// 비밀번호 암호화
				String hashCode = RMMessageDigest.digest(current_pw);
				current_pw = hashCode;
				hashCode = RMMessageDigest.digest(new_pw);
				new_pw = hashCode;
				hashCode = RMMessageDigest.digest(new_pw2);
				new_pw2 = hashCode;

				// 현재 비밀번호 비교
				if (!current_pw.equals(newMemberVo.getMember_pw())) {

					System.out.println("비밀번호 틀림");
					response.sendRedirect("../error/common_error_page.do");

				} else if (!new_pw.equals(new_pw2)) {

					System.out.println("비밀번호 확인 불일치");
					response.sendRedirect("../error/common_error_page.do");

				} else {

					memberService.changePw(newMemberVo.getMember_id(), new_pw);

					// null이 아닐 경우 제거
					session.removeAttribute("sessionUser");
					session.invalidate();// 세션 전체를 날려버림

					// 쿠키를 가져와보고
					Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
					if (loginCookie != null) {
						// null이 아니면 존재하면!
						loginCookie.setPath("/");
						// 쿠키는 없앨 때 유효시간을 0으로 설정하는 것 !!! invalidate같은거 없음.
						loginCookie.setMaxAge(0);
						// 쿠키 설정을 적용한다.
						response.addCookie(loginCookie);

						// 사용자 테이블에서도 유효기간을 현재시간으로 다시 세팅해줘야함.
						Date date = new Date(System.currentTimeMillis());
						memberService.keepLogin(memberVo.getMember_id(), session.getId(), date);
					}
					System.out.println("비번 변경 완료");
					return "member/update_password_complete_page";
				}
			}

		}
		return null;
	}

	// 회원 정보 수정 - 닉네임 변경 처리
	@RequestMapping("update_nickname_process.do")
	public String updateNicknameProcess(HttpSession session, @RequestParam(required = true) String member_nickname,
			HttpServletResponse response) throws IOException {

		System.out.println("member_nickname: " + member_nickname + "sss");
		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			return "redirect:/error/no_authority_page.do";

		} else {

			MemberVo newMemberVo = memberService.myInfoData(memberVo.getMember_no());

			String nickRegExp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{2,10}$";
			System.out.println("닉네임 정규식 검사ㅡㅡ" + member_nickname.matches(nickRegExp));

			// 닉네임 유효성 검사 실패
			if (member_nickname == null || member_nickname.equals("") || member_nickname.matches(nickRegExp) == false) {
				System.out.println("닉네임 null 또는 정규식 안맞음");
				response.sendRedirect("../error/common_error_page.do");

			} else {

				// 중복되는 닉네임 확인
				String nick = memberService.checkNick(member_nickname);
				if (nick != null) {
					System.out.println("중복되는 닉네임 존재");
					response.sendRedirect("../error/common_error_page.do");
				} else {
					memberService.changeNickname(newMemberVo.getMember_id(), member_nickname);

					return "redirect:/member/user_info_page.do";
				}
			}
		}
		return null;
	}

	// 회원 정보 수정 - 생년월일 변경 처리
	@RequestMapping("update_birthday_process.do")
	public String updateBirthdayProcess(HttpSession session, @RequestParam(required = true) String year,
			@RequestParam(required = true) String month, @RequestParam(required = true) String day) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			return "redirect:/error/no_authority_page.do";

		} else {

			String member_birth = year + month + day;
			MemberVo newMemberVo = memberService.myInfoData(memberVo.getMember_no());
			memberService.changeBirthday(newMemberVo.getMember_id(), member_birth);

			return "redirect:/member/user_info_page.do";

		}
	}

	// 회원 정보 수정 - 휴대전화번호 변경 처리
	@RequestMapping("update_phone_process.do")
	public String updatePhoneProcess(HttpSession session, @RequestParam(required = true) String member_phone,
			HttpServletResponse response) throws IOException {

		System.out.println("member_phone: " + member_phone);
		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			return "redirect:/error/no_authority_page.do";

		} else {

			MemberVo newMemberVo = memberService.myInfoData(memberVo.getMember_no());

			// 휴대전화번호 정규식검사
			String phoneRegExp = "^[0-9]{9,11}$";
			System.out.println("휴대전화번호 정규식 검사ㅡㅡ" + member_phone.matches(phoneRegExp));
			if (member_phone.matches(phoneRegExp) == false) {

				response.sendRedirect("../error/common_error_page.do");
				System.out.println("휴대전화번호 유효성x");

			} else {

				memberService.changePhone(newMemberVo.getMember_id(), member_phone);

				return "redirect:/member/user_info_page.do";
			}
		}
		return null;
	}

	// 회원 탈퇴 안내 페이지
	@RequestMapping("withdrawal_page.do")
	public String withdrawalPage(HttpSession session) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			return "redirect:/error/no_authority_page.do";

		} else {

			return "member/withdrawal_page";
		}
	}

	// 회원 탈퇴 처리
	@RequestMapping("withdrawal_process.do")
	public String withdrawalProcess(HttpSession session, String agreementCheck,
			@RequestParam(required = true) String current_pw) {
		System.out.println("탈퇴");

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		if (memberVo == null) {

			return "redirect:/error/no_authority_page.do";

		} else {

			MemberVo newMemberVo = memberService.myInfoData(memberVo.getMember_no());
			System.out.println("탈퇴 테스트" + newMemberVo.getMember_id());
			System.out.println("탈퇴 체크 여부: " + agreementCheck);
			System.out.println("새로 가져온 비번: " + newMemberVo.getMember_pw());

			// 비밀번호 암호화
			String hashCode = RMMessageDigest.digest(current_pw);
			System.out.println("입력받은 비번: " + hashCode);
			if (agreementCheck == null || !newMemberVo.getMember_pw().equals(hashCode)) {
				System.out.println("들어오면 안됨" + newMemberVo.getMember_id());
				return "redirect:/error/common_error_page.do";

			} else {

				memberService.withdrawal(newMemberVo);
				session.invalidate();

				return "member/withdrawal_complete_page";
			}
		}
	}

	// 캡차 이미지 생성 유틸 실행
	@RequestMapping(value = "captchaImg.do")
	public void captchaImg(HttpServletRequest request, HttpServletResponse response) {
		new CaptchaUtil().captchaImg(request, response);

	}

	// 관리자 로그아웃
	@RequestMapping("/admin_logout_process.do")
	public String adminLogoutProcess(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Object obj = session.getAttribute("sessionAdmin");

		if (obj == null) {
			System.out.println("로그인 안했을 때 로그아웃 처리 페이지 접근");
			return "redirect:/error/common_error_page.do";
		} else if (obj != null) {
			AdminVo adminVo = (AdminVo) obj;
			// null이 아닐 경우 제거
			session.removeAttribute("sessionAdmin");
			//session.invalidate();// 세션 전체를 날려버림
		}
		return "redirect:/main_page.do";
	}

	// 관리자 로그인페이지
	@RequestMapping("/admin_login_page.do")
	public String adminLoginPage() {
		return "member/admin_login_page";
	}

	// 관리자 회원가입 페이지
	@RequestMapping("/join_admin_page.do")
	public String adminJoinPage() {
		return "member/join_admin_page";
	}

	// 관리자 회원가입 처리
	@RequestMapping("/join_admin_process.do")
	public String joinAdminProcess(AdminVo adminVo, @RequestParam(required = true) String year,
			@RequestParam(required = true) String month, @RequestParam(required = true) String day, HttpSession session,
			HttpServletResponse response, @RequestParam(required = true) String member_pw2) throws IOException {
		// 이미 로그인한 상태
		if (session.getAttribute("sessionUser") != null) {

			return "redirect:/error/common_error_page.do";

		}

		// 입력값이 널일 경우
		if (adminVo.getAdmin_id() == null || adminVo.getAdmin_fullname() == null || adminVo.getAdmin_nickname() == null
				|| adminVo.getAdmin_phone() == null || adminVo.getAdmin_pw() == null) {

			return "redirect:/error/common_error_page.do";

		} else {
			String id = memberService.checkAdminId(adminVo.getAdmin_id());
			String nick = memberService.checkAdminNick(adminVo.getAdmin_nickname());
			if (id != null || nick != null) {
				System.out.println("중복된 아이디 또는 닉네임 존재");
				return "redirect:/error/common_error_page.do";
			}
			// 아이디 정규식 검사
			String idRegExp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.([a-zA-Z]{1,3})$";
			// 비밀번호 정규식 검사
			String pwRegExp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$";
			// 닉네임 정규식 검사
			String nickRegExp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]{2,10}$";
			// 이름 정규식 검사
			String nameRegExp = "^[가-힣]{2,6}$";
			// 휴대전화번호 정규식검사
			String phoneRegExp = "^[0-9]{9,11}$";

			System.out.println("아이디 정규식 검사ㅡㅡ" + adminVo.getAdmin_id().matches(idRegExp));
			System.out.println("비밀번호 정규식 검사ㅡㅡ" + adminVo.getAdmin_pw().matches(pwRegExp));
			System.out.println("닉네임 정규식 검사ㅡㅡ" + adminVo.getAdmin_nickname().matches(nickRegExp));
			System.out.println("이름 정규식 검사ㅡㅡ" + adminVo.getAdmin_fullname().matches(nameRegExp));
			System.out.println("휴대전화번호 정규식 검사ㅡㅡ" + adminVo.getAdmin_phone().matches(phoneRegExp));

			if (adminVo.getAdmin_id().matches(idRegExp) == false || adminVo.getAdmin_pw().matches(pwRegExp) == false
					|| adminVo.getAdmin_nickname().matches(nickRegExp) == false
					|| adminVo.getAdmin_fullname().matches(nameRegExp) == false
					|| adminVo.getAdmin_phone().matches(phoneRegExp) == false) {

				System.out.println("아이디, 비밀번호, 닉네임, 이름 또는 휴대전화번호 형식 안맞음");
				response.sendRedirect("../error/common_error_page.do");

			} else if (!member_pw2.equals(adminVo.getAdmin_pw())) {

				System.out.println("비밀번호 불일치");
				response.sendRedirect("../error/common_error_page.do");

			} else {
				String member_birth = year + month + day;
				adminVo.setAdmin_birth(member_birth);

				memberService.joinAdmin(adminVo);

				return "redirect:/member/admin_login_page.do";
			}
		}

		return null;

	}

	// 관리자 아이디 중복확인
	@RequestMapping("checkAdminId_process.do")
	@ResponseBody
	public String checkAdminId(@RequestParam(required = true) String id, HttpServletResponse response)
			throws IOException {

		String get_id = memberService.checkAdminId(id);

		if (get_id == null) { // 중복된 아이디가 없으면

			return "true";
		} else { // 중복된 아이디가 있으면

			return "false";
		}
	}

	// 관리자 닉네임 중복확인
	@RequestMapping("checkAdminNick_process.do")
	@ResponseBody
	public String checkAdminNick(@RequestParam(required = true) String nick, HttpServletResponse response)
			throws IOException {

		String get_nick = memberService.checkAdminNick(nick);
		if (get_nick == null) { // 중복된 닉네임이 없으면

			return "true";
		} else { // 중복된 닉네임이 있으면

			return "false";
		}

	}

	// 관리자 로그인
	@RequestMapping("/admin_login_process.do")
	public String adminLoginProcess(HttpSession session, HttpServletRequest request,
			@RequestParam(required = true) String insert_id, @RequestParam(required = true) String insert_pw) {

		System.out.println("ㅁㅁㅁ");
		if (session.getAttribute("sessionAdmin") != null) {

			// 기존에 login이란 세션 값이 존재한다면
			session.removeAttribute("sessionAdmin");// 기존값을 제거해 준다.
		}

		if (insert_id != null && insert_pw != null) {

			// 1) 아이디는 맞고 비밀번호는 틀린 경우 memberVo에 값이 담김
			AdminVo adminVo = memberService.loginAdminTrial(insert_id, insert_pw);

			// 비밀번호 틀린 회수가 5회 미만인 경우
			if (adminVo != null && adminVo.getAdmin_login_trial() < 5 && adminVo.getAdmin_status().equals("Y")) {
				System.out.println("사용자 입력 아이디" + insert_id);
				System.out.println("사용지 입력 비밀번호" + insert_pw);
				// 로그인 실패회수 1 증가
				memberService.loginAdminTrialCount(adminVo.getAdmin_id());

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("admin_login_trial", 0);

				return "redirect:/member/admin_login_page.do";

				// 비밀번호 틀린 회수가 5회 이상인 경우
			} else if (adminVo != null && adminVo.getAdmin_login_trial() >= 5
					&& adminVo.getAdmin_status().equals("Y")) {

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("admin_login_trial", adminVo.getAdmin_login_trial());

				return "redirect:/member/admin_login_page.do";

			}

			// 2) 아이디와 비밀번호 모두 맞는 경우 userData에 값이 담김
			AdminVo userData = memberService.adminLogin(insert_id, insert_pw);

			if (userData == null) { // 로그인 실패

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("admin_login_trial", -1);

				return "redirect:/member/admin_login_page.do";

			} else if (userData != null && (userData.getAdmin_status().equals("N"))) {

				// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
				FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
				fm.put("admin_login_trial", -10);

				return "redirect:/member/admin_login_page.do";

			} else if (userData != null && userData.getAdmin_login_trial() >= 5) {

				// 캡차 문자와 비교
				String getAnswer = (String) request.getSession().getAttribute("captcha");
				String answer = request.getParameter("answer");

				// 캡차 문자와 사용자 입력 값이 같은 경우
				if (getAnswer.equals(answer)) {

					System.out.println("로그인 성공");
					session.setAttribute("sessionAdmin", userData); // 사용자 정보 session처리

					// 로그인 성공했으므로 로그인 시도회수 초기화
					memberService.resetAdminTrialCount(userData.getAdmin_id());

					return "redirect:/admin/dashboard_page.do";

				} else {

					System.out.println("캡차 문자 틀렸음 - 로그인 실패");

					// 로그인 페이지에 파라미터 안날아가게 하기 위해서 FlashMap 사용
					FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
					fm.put("admin_login_trial", userData.getAdmin_login_trial());

					return "redirect:/member/admin_login_page.do";
				}
			}

			else if ((userData != null && userData.getAdmin_login_trial() < 5)) { // 로그인 성공
				System.out.println("여기!!!");
				memberService.resetAdminTrialCount(userData.getAdmin_id());
				session.setAttribute("sessionAdmin", userData); // 사용자 정보 session처리

				return "redirect:/admin/dashboard_page.do";

			}

		}
		return "member/admin_login_fail";
	}
}
