package com.dayside.vacation.member.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dayside.vacation.common.constants.VacationConstants;
import com.dayside.vacation.common.exception.VacationException;
import com.dayside.vacation.common.model.ReturnResultData;
import com.dayside.vacation.common.result.ResultCodeDesc;
import com.dayside.vacation.common.util.HashGeneration;
import com.dayside.vacation.common.util.MailSenderThread;
import com.dayside.vacation.member.model.Auth;
import com.dayside.vacation.member.model.BaseResult;
import com.dayside.vacation.member.model.Member;
import com.dayside.vacation.member.model.UpdatePw;
import com.dayside.vacation.member.service.MemberService;

/**
 * 회원 관련
 * @author April
 *
 */
@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberService memberService;
//	@Autowired
//	JavaMailSender javaMailSender;

	// 로그인 페이지(메인 페이지)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpSession session) {

		ModelAndView loginView = new ModelAndView();
		loginView.setViewName("member/login_page");

		return loginView;
	}

	// 로그인 처리
	@RequestMapping(value = "/login_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData loginProcess(@RequestBody Member member, HttpSession session) throws VacationException {
		logger.debug("===================입력받은 아이디: {}",member);
		ReturnResultData result = null;
		if(member == null) {
			return result = new ReturnResultData(ResultCodeDesc.LOGIN_FAIL);
		}
		
		Map<String, Object> map = memberService.getOneMember(member);
		
		// 아이디, 비밀번호는 맞음
		if (map != null) {
			String employeeNo = (String)map.get("employeeNo");
			String authCertification = memberService.getAuthCertification(employeeNo);
			if (authCertification.equals(VacationConstants.STRING_N)) {
				// 반려
				result = new ReturnResultData(ResultCodeDesc.LOGIN_AUTH_FAIL);
			} else if (map.get("joinStatus").equals(VacationConstants.JOIN_REQUEST)) {
				result = new ReturnResultData(ResultCodeDesc.JOIN_REQUEST);
				// 가입 요청
			} else if (map.get("joinStatus").equals(ResultCodeDesc.JOIN_REJECT)) {
				// 반려
				result = new ReturnResultData(ResultCodeDesc.JOIN_REJECT);

			} else {
				session.setAttribute("memberSession", map.get("employeeNo"));
				logger.debug("사번: {}", map.get("employeeNo"));
				result = new ReturnResultData(ResultCodeDesc.JOIN_APPROVAL);
			}
			// 아이디, 비밀번호 틀림
		} else {
			result = new ReturnResultData(ResultCodeDesc.LOGIN_FAIL);
		}
		return result;
	}

	// 회원가입 페이지
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView joinPage(HttpSession session) {

		ModelAndView loginView = new ModelAndView();
		loginView.setViewName("member/join_page");

		return loginView;
	}

	// 회원가입 처리
	@RequestMapping(value = "/join_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData joinProcess(HttpServletRequest request, @RequestBody Member member) throws VacationException {
		
		ReturnResultData result = null;
		
		// 유효성 검사 및 중복 확인
		String employeeNoRegExp = "^[0-9]{6}$";
		String idRegExp = "^[a-zA-Z0-9-_.]{1,20}@dayside.co.kr$";
		String fullnameRegExp = "^[가-힣]{2,5}$";
		String no = null;
		String id = null;
		
		no = memberService.checkEmployeeNo(member.getEmployeeNo());
		id = memberService.checkEmailId(member.getEmailId());

		String pwCheck = member.getPwCheck();
		List<String> teams = memberService.getAllTeam();
		
		// 사번 유효성 검사 및 중복 확인
		if (member.getEmployeeNo().matches(employeeNoRegExp) == false || member.getEmployeeNo().equals(no)) {
			logger.debug("사번 에러: "+member.getEmployeeNo());
			result = new ReturnResultData(ResultCodeDesc.JOIN_NO_FAIL);
			// 아이디 유효성 검사 및 중복 확인
		} else if (member.getEmailId().matches(idRegExp) == false || member.getEmailId().equals(id)) {
			logger.debug("아이디 에러: "+member.getEmailId());
			result = new ReturnResultData(ResultCodeDesc.JOIN_ID_FAIL);
			// 이름 유효성 검사
		} else if (member.getFullname().matches(fullnameRegExp) == false) {
			logger.debug("이름 에러: "+member.getFullname());
			result = new ReturnResultData(ResultCodeDesc.JOIN_FULLNAME_FAIL);
			// 비밀번호 유효성 검사
		} else if (member.getPw().length() < 8) {
			logger.debug("비밀번호 에러: "+member.getPw());
			result = new ReturnResultData(ResultCodeDesc.JOIN_PW_FAIL);
			// 비밀번호 확인
		} else if (!(member.getPw().equals(pwCheck))) {
			logger.debug("비밀번호 확인 에러: "+member.getPw()+", "+pwCheck);
			result = new ReturnResultData(ResultCodeDesc.JOIN_PWCHECK_FAIL);
			// 소속팀 유효성 검사
		} else if(!teams.contains(member.getTeam())) {
			logger.debug("소속팀 에러");
			result = new ReturnResultData(ResultCodeDesc.JOIN_TEAM_FAIL);
			// 팀장, 관리자 여부 유효성 검사
		} else if (!VacationConstants.STRING_YN.contains(member.getLeaderYn())) {
			logger.debug("팀장 여부 에러");
			result = new ReturnResultData(ResultCodeDesc.JOIN_LEADERYN_FAIL);
		} else if (!VacationConstants.STRING_YN.contains(member.getAdminYn())) {
			logger.debug("관리자 여부 에러");
			result = new ReturnResultData(ResultCodeDesc.JOIN_ADMINYN_FAIL);
		} else {
			Auth auth = new Auth();
			String authKey = UUID.randomUUID().toString();
			auth.setAuthKey(authKey);
			memberService.joinMember(member, auth);
			
			// 회원가입 인증 메일 발송
			MailSenderThread mail = new MailSenderThread();
			try {
				mail.sendEmail(request, authKey, member.getEmailId());
			} catch (Exception e) {
				logger.debug("**********메일 발송 에러************");
				e.printStackTrace();
			}
			result = new ReturnResultData(ResultCodeDesc.JOIN_SUCCESS);
		}
		return result;
	}

	// 메일 인증 처리
	@RequestMapping(value = "/certification_process", method = RequestMethod.GET)
	public ModelAndView certicicationProcess(String auth_key) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(auth_key == null) {
			mav.setViewName("error/errorPage");
			return mav;
		}
		try {
			boolean isExist = memberService.selectAuthKey(auth_key);
			if(isExist) {
				memberService.authCertification(auth_key);
				mav.setViewName("member/certification_complete");
			} else {
				mav.setViewName("error/errorPage");
				return mav;
			}
		} catch (Exception e) {
			mav.setViewName("error/errorPage");
			return mav;
		}
		return mav;
	}

	// 로그아웃
	@RequestMapping(value = "/logout")
	public ModelAndView logoutProcess(HttpSession session) throws VacationException {

		ModelAndView loginView = new ModelAndView();
		BaseResult result = new BaseResult();

		String employeeNo = (String) session.getAttribute("memberSession");
		if (employeeNo == null) {
			result = new BaseResult(ResultCodeDesc.LOGOUT_FAIL);
		} else {
			session.removeAttribute("memberSession");
			session.invalidate();
			result = new BaseResult(ResultCodeDesc.LOGOUT_SUCCESS);
		}
		loginView.setViewName("redirect:/member/login");
		loginView.addObject("result", result);

		return loginView;
	}

	// 비밀번호 변경 페이지
	@RequestMapping(value = "/updatePw")
	public ModelAndView updatePwPage() {

		ModelAndView loginView = new ModelAndView();
		loginView.setViewName("member/update_pw_page");

		return loginView;
	}

	// 비밀번호 변경 처리
	@RequestMapping(value = "/update_pw_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData updatePwProcess(@RequestBody UpdatePw updatePw, HttpSession session) throws Exception {

		ReturnResultData result = new ReturnResultData();

		String employeeNo = (String) session.getAttribute("memberSession");

		// 비밀번호, salt 가져오는 코드 작성
		Map<String, Object> map = memberService.getPwSalt(employeeNo);
		logger.debug("비밀번호: " + map.get("pw"));
		logger.debug("salt: " + map.get("hashSalt"));
		String hashedPw = null;
		String salt = (String) map.get("hashSalt");
		HashGeneration hash = new HashGeneration();

		hashedPw = hash.hashGeneration(updatePw.getCurrPw(), Base64.getDecoder().decode(salt));
		logger.debug("해시된 비번: " + hashedPw);

		if (hashedPw.equals(map.get("pw"))) {

			if (updatePw.getNewPw().length() < 8) {
				result = new ReturnResultData(ResultCodeDesc.UPDATE_NEW_PW_INVALID);
			} else if (!updatePw.getNewPw().equals(updatePw.getNewPwCheck())) {
				result = new ReturnResultData(ResultCodeDesc.UPDATE_NEW_PWCHECK_INVALID);
			} else {
				// 비밀번호 변경
				memberService.updatePw(updatePw.getNewPw(), (String) map.get("hashSalt"), employeeNo);
				result = new ReturnResultData(ResultCodeDesc.UPDATE_SUCCESS);
			}

		} else {
			result = new ReturnResultData(ResultCodeDesc.UPDATE_PW_INCORRECT);
		}

		return result;
	}

	// 사번 중복 확인
	@RequestMapping(value = "/checkEmployeeNo_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData checkNo(@RequestBody Member member) throws VacationException {

		ReturnResultData result;

		String no = memberService.checkEmployeeNo(member.getEmployeeNo());
		// 중복된 사번 없음
		if (no == null) {
			logger.debug("입력 받은 사번: {}", member.getEmployeeNo());
			result = new ReturnResultData(ResultCodeDesc.JOIN_NO_AVAILABLE);

			// 중복된 사번 있음
		} else {
			result = new ReturnResultData(ResultCodeDesc.JOIN_NO_DISAVAILABLE);
		}
		return result;
	}

	// 아이디 중복 확인
	@RequestMapping(value = "/checkEmailId_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData checkId(@RequestBody Member member) throws VacationException {

		ReturnResultData result;
		String id = memberService.checkEmailId(member.getEmailId());

		// 중복된 아이디 없음
		if (id == null) {
			logger.debug("입력 받은 아이디: {}", member.getEmailId());
			result = new ReturnResultData(ResultCodeDesc.JOIN_ID_AVAILABLE);
			// 중복된 아이디 있음
		} else {
			result = new ReturnResultData(ResultCodeDesc.JOIN_ID_DISAVAILABLE);
		}
		return result;
	}

	// 관리자 여부 확인
	@RequestMapping(value = "/adminYN", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData checkAdminYn(HttpSession session) throws VacationException {
		ReturnResultData result = new ReturnResultData();
		String employeeNo = (String) session.getAttribute("memberSession");

		String adminYN = memberService.getAdminYN(employeeNo);
		logger.debug("관리자 여부 확인: {}",adminYN);
		if (adminYN.equals(VacationConstants.STRING_N)) {
			logger.debug("관리자 아님");
			result = new ReturnResultData(ResultCodeDesc.ADMINYN_CHECK_FAIL);
		} else if(adminYN.equals(VacationConstants.STRING_Y)) {
			logger.debug("관리자임");
			result = new ReturnResultData(ResultCodeDesc.ADMINYN_CHECK_SUCCESS);
		} else {
			result = new ReturnResultData(ResultCodeDesc.FAIL);
		}

		return result;
	}
}
