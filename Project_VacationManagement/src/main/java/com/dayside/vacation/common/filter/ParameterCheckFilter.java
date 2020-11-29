package com.dayside.vacation.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dayside.vacation.common.constants.UrlConstants;
import com.dayside.vacation.common.constants.VacationConstants;
import com.dayside.vacation.common.exception.VacationException;
import com.dayside.vacation.common.result.ResultCodeDesc;
import com.dayside.vacation.common.util.GsonUtil;
import com.dayside.vacation.common.util.HttpRequestWrapper;
import com.dayside.vacation.common.util.HttpResponseWrapper;
import com.dayside.vacation.main.model.Board;
import com.dayside.vacation.member.model.BaseResult;
import com.dayside.vacation.member.model.Member;
import com.dayside.vacation.member.model.UpdatePw;
import com.dayside.vacation.overtime.model.OvertimeRequest;
import com.dayside.vacation.vacation.model.Vacation;

/**
 * 각종 파라미터 검증(POST방식)
 * @author April
 *
 */
@Component
public class ParameterCheckFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ParameterCheckFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		logger.debug("파라미터 검증");
		BaseResult result = null;
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String endPoint = req.getRequestURI();
		logger.debug("Filter preFilter Chain  endPoint : {}",  endPoint);   	
    	
    	HttpRequestWrapper requestWrapper = new HttpRequestWrapper(req);
		HttpResponseWrapper responseWrapper = new HttpResponseWrapper(resp);
    	String requestBody = requestWrapper.getRequestBody();    	
    	logger.debug("RequestBody : {}  ",  requestBody);
    	
    	try {
    		validationParameter(endPoint, requestBody);
    	} catch (VacationException e){
    		result = new BaseResult(e.getResultCodeDesc());
    		String strRespData = GsonUtil.serialization(result);
    		responseWrapper.setContentType("application/json;charset=utf-8");
    		responseWrapper.getOutputStream().write(strRespData.getBytes("UTF-8"));
    		logger.debug("responseBody: {}",responseWrapper.getResponseBody());
    		return;
    	}
		chain.doFilter(requestWrapper, responseWrapper);
		logger.debug("회원가입 필터 끝");
	}
	
	public void validationParameter(String endPoint, String requestBody) throws VacationException {
		logger.debug("파라미터 검증 시작");
		// 회원가입 파라미터 검증
		if(endPoint.equals(UrlConstants.JOIN_PROCESS_URL)) {
			logger.debug("회원가입 URL");
			Member member = GsonUtil.deserialization(requestBody, Member.class);
			if(member == null) {
				throw new VacationException(ResultCodeDesc.MEMBER_NULL);
			} else if(member.getEmployeeNo() == null) {
				throw new VacationException(ResultCodeDesc.JOIN_NO_FAIL);
			} else if(member.getEmailId() == null) {
				logger.debug("아이디 null");
				throw new VacationException(ResultCodeDesc.JOIN_ID_FAIL);
			} else if(member.getFullname() == null) {
				logger.debug("이름 null");
				throw new VacationException(ResultCodeDesc.JOIN_FULLNAME_FAIL);
			} else if(member.getPw() == null) {
				logger.debug("비밀번호 null");
				throw new VacationException(ResultCodeDesc.JOIN_PW_FAIL);
			} else if(member.getPwCheck() == null) {
				logger.debug("비밀번호 확인 null");
				throw new VacationException(ResultCodeDesc.JOIN_PWCHECK_FAIL);
			} else if(!VacationConstants.TEAM.contains(member.getTeam())) {
				logger.debug("팀 x");
				throw new VacationException(ResultCodeDesc.JOIN_TEAM_FAIL);
			} else if(!VacationConstants.STRING_YN.contains(member.getLeaderYn())) {
				logger.debug("팀장 x");
				throw new VacationException(ResultCodeDesc.JOIN_LEADERYN_FAIL);
			} else if(!VacationConstants.STRING_YN.contains(member.getAdminYn())) {
				logger.debug("관리자 x");
				throw new VacationException(ResultCodeDesc.JOIN_ADMINYN_FAIL);
			}
			// 로그인 파라미터 검증
		} else if(endPoint.equals(UrlConstants.LOGIN_PROCESS_URL)) {
			logger.debug("로그인 프로세스 필터");
			Member member = GsonUtil.deserialization(requestBody, Member.class);
			logger.debug("확인");
			if(member == null) {
				logger.debug("멤버 객체 null");
				throw new VacationException(ResultCodeDesc.MEMBER_NULL);
			} else if(member.getEmailId() == null) {
				logger.debug("아이디 null");
				throw new VacationException(ResultCodeDesc.JOIN_ID_FAIL);
			}
			if(member.getPw() == null) {
				logger.debug("비밀번호 null");
				throw new VacationException(ResultCodeDesc.JOIN_PW_FAIL);
			}
		} else if(endPoint.equals(UrlConstants.VACATION_APPLY_PROCESS_URL)) {
			logger.debug("휴가 신청 프로세스 필터");
			Vacation vacation = GsonUtil.deserialization(requestBody, Vacation.class);
			logger.debug("확인");
			if(vacation == null) {
				logger.debug("휴가 객체 null");
				throw new VacationException(ResultCodeDesc.VACATION_NULL);
			} else if(vacation.getStartDt() == null) {
				logger.debug("시작일 null");
				throw new VacationException(ResultCodeDesc.VACATION_START_DT_FAIL);
			} else if(vacation.getStartType() == null) {
				logger.debug("시작 유형 null");
				throw new VacationException(ResultCodeDesc.VACATION_START_TYPE_FAIL);
			} else if(vacation.getEndDt() == null) {
				logger.debug("종료일 null");
				throw new VacationException(ResultCodeDesc.VACATION_END_DT_FAIL);
			} 
		} else if(endPoint.equals(UrlConstants.VACATION_STATUS_URL)) {
			Vacation vacation = GsonUtil.deserialization(requestBody, Vacation.class);
			if(vacation == null) {
				logger.debug("휴가 객체 null");
				throw new VacationException(ResultCodeDesc.VACATION_NULL);
			} else if(vacation.getVacationApplyNo() == null) {
				logger.debug("휴가신청번호 null");
				throw new VacationException(ResultCodeDesc.VACATION_NO_INVALID);
			}
		} else if(endPoint.equals(UrlConstants.VACATION_CANCEL_PROCESS_URL)) {
			Vacation vacation = GsonUtil.deserialization(requestBody, Vacation.class);
			if(vacation == null) {
				logger.debug("휴가 객체 null");
				throw new VacationException(ResultCodeDesc.VACATION_NULL);
			} else if(vacation.getVacationApplyNo() == null) {
				logger.debug("휴가신청번호 null");
				throw new VacationException(ResultCodeDesc.VACATION_NO_INVALID);
			}
		} else if(endPoint.equals(UrlConstants.UPDATE_PW_PROCESS_URL)) {
			UpdatePw updatePw = GsonUtil.deserialization(requestBody, UpdatePw.class);
			if(updatePw == null) {
				logger.debug("비밀번호 변경 객체 null");
				throw new VacationException(ResultCodeDesc.VACATION_NO_INVALID);
			} else if(updatePw.getCurrPw() == null) {
				logger.debug("현재 비밀번호 null");
				throw new VacationException(ResultCodeDesc.JOIN_PW_FAIL);
			} else if(updatePw.getNewPw() == null) {
				logger.debug("새로운 비밀번호 null");
				throw new VacationException(ResultCodeDesc.UPDATE_NEW_PW_INVALID);
			} else if(updatePw.getNewPwCheck() == null) {
				logger.debug("새로운 비밀번호 확인 null");
				throw new VacationException(ResultCodeDesc.UPDATE_NEW_PWCHECK_INVALID);
			}
		} else if(endPoint.equals(UrlConstants.JOIN_EMPLOYEENO_URL)) {
			logger.debug("사번 검증");
			Member member = GsonUtil.deserialization(requestBody, Member.class);
			logger.debug("확인");
			if(member == null) {
				logger.debug("멤버 객체 null");
				throw new VacationException(ResultCodeDesc.MEMBER_NULL);
			} else if(member.getEmployeeNo() == null) {
				logger.debug("사번 null");
				throw new VacationException(ResultCodeDesc.JOIN_NO_FAIL);
			}
		} else if(endPoint.equals(UrlConstants.JOIN_EMAILID_URL)) {
			logger.debug("아이디 검증");
			Member member = GsonUtil.deserialization(requestBody, Member.class);
			logger.debug("확인");
			if(member == null) {
				logger.debug("멤버 객체 null");
				throw new VacationException(ResultCodeDesc.MEMBER_NULL);
			} else if(member.getEmailId() == null) {
				logger.debug("아이디 null");
				throw new VacationException(ResultCodeDesc.JOIN_ID_FAIL);
			}
		} else if(endPoint.equals(UrlConstants.VACATION_SEARCH_URL)) {
			logger.debug("휴가 검색 검증");
			Board board = GsonUtil.deserialization(requestBody, Board.class);
			logger.debug("확인");
			if(board == null) {
				logger.debug("board 객체 null");
				throw new VacationException(ResultCodeDesc.BOARD_NULL);
			} else if(board.getSelectYear() == null) {
				logger.debug("연도 null");
				throw new VacationException(ResultCodeDesc.VACATION_YEAR_INVALID);
			} 
		} else if(endPoint.equals(UrlConstants.VACATION_COUNT_ALL_URL)) {
			logger.debug("총 게시물 수, 휴가 승인 일수 검증");
			Board board = GsonUtil.deserialization(requestBody, Board.class);
			logger.debug("확인");
			if(board == null) {
				logger.debug("board 객체 null");
				throw new VacationException(ResultCodeDesc.BOARD_NULL);
			} else if(board.getSelectYear() == null) {
				logger.debug("연도 null");
				throw new VacationException(ResultCodeDesc.VACATION_YEAR_INVALID);
			} 
		} else if(endPoint.equals(UrlConstants.OVERTIME_SEARCH_URL)) {
			logger.debug("초과근무 검색 검증");
			Board board = GsonUtil.deserialization(requestBody, Board.class);
			logger.debug("확인");
			if(board == null) {
				logger.debug("board 객체 null");
				throw new VacationException(ResultCodeDesc.BOARD_NULL);
			} else if(board.getSelectYear() == null) {
				logger.debug("연도 null");
				throw new VacationException(ResultCodeDesc.VACATION_YEAR_INVALID);
			} 
		} else if(endPoint.equals(UrlConstants.OVERTIME_APPLY_PROCESS_URL)) {
			OvertimeRequest overtimeRequest = GsonUtil.deserialization(requestBody, OvertimeRequest.class);
			if(overtimeRequest == null) {
				logger.debug("overtimeRequest 객체 null");
				throw new VacationException(ResultCodeDesc.OVERTIME_REQUEST_NULL);
			} else if(overtimeRequest.getStartDt() == null) {
				logger.debug("출근일 null");
				throw new VacationException(ResultCodeDesc.VACATION_START_DT_FAIL);
			} else if(overtimeRequest.getEndDt() == null) {
				logger.debug("출근일 null");
				throw new VacationException(ResultCodeDesc.VACATION_END_DT_FAIL);
			} else if(overtimeRequest.getOvertimeType() == null) {
				logger.debug("근무 타입 null");
				throw new VacationException(ResultCodeDesc.OVERTIME_TYPE_FAIL);
			} 
		}
	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String FilterParam = filterConfig.getInitParameter("encoding");
		logger.debug("FilterParam : "+FilterParam);

	}
}
