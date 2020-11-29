package com.dayside.vacation.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayside.vacation.member.model.Member;
import com.dayside.vacation.member.service.MemberService;

@Controller
@RequestMapping("/")
public class MemberController {
	
	@Autowired
	MemberService memberService;

	// 로그인 페이지(메인 페이지)
	@RequestMapping("/")
	public String loginPage(Model model, HttpSession session) {
		
		// 로그인 된 상태이면 리스트 페이지로 이동시키기
		if(session.getAttribute("memberSession") != null) {
			return "redirect:/list";
		}
		
		return "member/login_page";
	}
	
	// 로그인 처리
	@RequestMapping("/login_process")
	@ResponseBody
	public String loginProcess(String emailId, String pw, HttpSession session) {
		
		Member member = memberService.getOneMember(emailId, pw);
		
		// 아이디, 비밀번호는 맞음
		if(member != null) {
			
			if(member.getJoinStatus().equals("JOIN_REQUEST")) {
				
				// 가입 요청
				return "JOIN_REQUEST";
				
			} else if(member.getJoinStatus().equals("JOIN_REJECT")) {
				
				// 반려
				return "JOIN_REJECT";
				
			} else {
				
				session.setAttribute("memberSession", member.getEmployeeNo());
				
				return "JOIN_APPROVAL";
				
			}
		// 아이디, 비밀번호 틀림
		} else {
			
			return "FAIL";
		}
	}
	
	// 회원가입 페이지
	@RequestMapping("/join")
	public String joinPage() {
		
		return "member/join_page";
	}
	
	// 회원가입 처리
	@RequestMapping("/join_process")
	@ResponseBody
	public String joinProcess(Member member, String pwCheck) {
		
		// 유효성 검사 및 중복 확인
		String employeeNoRegExp = "^[0-9]{6}$";
		String idRegExp = "^[a-zA-Z0-9-_.]{1,20}@dayside.co.kr$";
		String fullnameRegExp = "^[가-힣]{2,5}$";
		String no = memberService.checkEmployeeNo(member.getEmployeeNo());
		String id = memberService.checkEmailId(member.getEmailId());
		String[] team = memberService.getAllTeam();
		
		// 사번 유효성 검사 및 중복 확인
		if(member.getEmployeeNo().matches(employeeNoRegExp) == false || member.getEmployeeNo().equals(no)) {
			return "NO_FAIL";
		}
		// 아이디 유효성 검사 및 중복 확인
		if(member.getEmailId().matches(idRegExp) == false || member.getEmailId().equals(id)) {
			return "ID_FAIL";
		}
		// 이름 유효성 검사
		if(member.getFullname().matches(fullnameRegExp) == false) {
			return "FULLNAME_FAIL";
		}
		// 비밀번호 유효성 검사
		if(member.getPw().length() < 8) {
			return "PW_FAIL";
		}
		// 비밀번호 확인
		if(!(member.getPw().equals(pwCheck))) {
			return "PWCHECK_FAIL";
		}
		// 소속팀 유효성 검사
		for(int i=0; i<team.length; i++) {
			if(!(member.getTeam().equals(team[i])) && i==team.length-1) {
				return "TEAM_FAIL";
			}
		}
		// 팀장, 관리자 여부 유효성 검사
		if(member.getLeaderYn() != 'Y' || member.getLeaderYn() != 'N') {
			return "LEADERYN_FAIL";
		}
		if(member.getAdminYn() != 'Y' || member.getAdminYn() != 'N') {
			return "ADMINYN_FAIL";
		}
		// 가입 성공 - 유효성 검사 완료 및 null 아님
		if(member.getEmployeeNo() != null && member.getEmailId() != null && member.getFullname() != null &&
				member.getPw() != null && pwCheck != null && !(member.getTeam().equals("DEFAULT"))) { 

			memberService.joinMember(member);
			
			return "SUCCESS";
		}
		return "FAIL";
	}
	
	// 로그아웃
	@RequestMapping("/logout")
	public String logoutProcess(HttpSession session) {
		
		session.removeAttribute("memberSession");
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 사번 중복 확인
	@RequestMapping("/checkEmployeeNo_process")
	@ResponseBody
	public String checkNo(String employeeNo) {
		
		String no = memberService.checkEmployeeNo(employeeNo);
		// 중복된 사번 없음
		if(no == null) {
			return "AVAILABLE";
		// 중복된 사번 있음
		} else {
			return "DISAVAILABLE";
		}
	}
	
	// 아이디 중복 확인
	@RequestMapping("/checkEmailId_process")
	@ResponseBody
	public String checkId(String emailId) {
		
		String id = memberService.checkEmailId(emailId);
		
		// 중복된 아이디 없음
		if(id == null) {
			return "AVAILABLE";
		// 중복된 아이디 있음
		} else {
			return "DISAVAILABLE";
		}
		
	}
}

















