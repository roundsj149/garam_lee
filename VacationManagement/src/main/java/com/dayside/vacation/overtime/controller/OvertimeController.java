package com.dayside.vacation.overtime.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
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
import com.dayside.vacation.common.util.DateUtil;
import com.dayside.vacation.main.model.Board;
import com.dayside.vacation.member.service.MemberService;
import com.dayside.vacation.overtime.model.Overtime;
import com.dayside.vacation.overtime.model.OvertimeRequest;
import com.dayside.vacation.overtime.service.OvertimeService;

/**
 * 초과근무 관련
 * @author April
 *
 */
@Controller
@RequestMapping("/overtime/*")
public class OvertimeController {

	private static final Logger logger = LoggerFactory.getLogger(OvertimeController.class);

	@Autowired
	MemberService memberService;
	@Autowired
	OvertimeService overtimeService;

	// 초과근무 신청 목록 페이지
	@RequestMapping(value = "/list")
	public ModelAndView listPage() {

		ModelAndView listView = new ModelAndView();
		listView.setViewName("overtime/list");

		return listView;

	}

	// 초과근무 목록 가져오기
	@RequestMapping(value = "/search_overtime", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData searchOvertime(HttpSession session, @RequestBody Board board) throws VacationException {

		ReturnResultData result = new ReturnResultData();
		String selectYearRegExp = "/^[0-9]{4}$";
		String currPageRegExp = "/^[0-9]{1,10}$";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (board.getSelectYear() == null || board.getSelectYear().matches(selectYearRegExp)) {
			logger.debug("연도 에러: " + board.getSelectYear());
			result = new ReturnResultData(ResultCodeDesc.VACATION_YEAR_INVALID);
		}
		if (Integer.toString(board.getCurrPage()).matches(currPageRegExp)) {
			logger.debug("페이지 에러: " + Integer.toString(board.getCurrPage()));
			result = new ReturnResultData(ResultCodeDesc.VACATION_PAGE_INVALID);
		}
		if (board.getCurrPage() < 1) {
			board.setCurrPage(1);
		}

		String employeeNo = (String) session.getAttribute("memberSession");

		String adminYN = memberService.getAdminYN(employeeNo);

		if (adminYN == null) {
			result = new ReturnResultData(ResultCodeDesc.ADMINYN_CHECK_FAIL);
		}
		if (adminYN.equals(VacationConstants.STRING_Y)) {
			employeeNo = null;
		}

		try {
			list = overtimeService.getOvertimeInfo(employeeNo, board.getSearchWord(), board.getSelectYear(), board.getCurrPage());
			result = new ReturnResultData(ResultCodeDesc.VACATION_SUCCESS);
		} catch (Exception e) {
			result = new ReturnResultData(ResultCodeDesc.VACATION_FAIL);
		}

		result = new ReturnResultData(ResultCodeDesc.SUCCESS, list);
		logger.debug("초과근무 목록 가져오기 컨트롤러");
		return result;
	}

	// 초과근무 신청 페이지
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView requestPage(HttpSession session) {

		String employeeNo = (String) session.getAttribute("memberSession");

		Map<String, Object> map = memberService.getNoName(employeeNo);

		ModelAndView listView = new ModelAndView();
		listView.setViewName("overtime/request_page");
		listView.addObject("map", map);

		return listView;
	}

	// 초과근무 신청 처리
	@RequestMapping(value = "/request_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData requestProcess(HttpSession session, @RequestBody OvertimeRequest overtimeRequest) throws ParseException {
		logger.debug("시간, 분: {}, {}",overtimeRequest.getStartHour(),overtimeRequest.getStartMinute());
		logger.debug("시간, 분: {}, {}",overtimeRequest.getEndHour(),overtimeRequest.getEndMinute());
		ReturnResultData result;
		String employeeNo = (String) session.getAttribute("memberSession");

		String dateRegExp = "/^[0-9]{2}.-[0-9]{2}.-[0-9]{2}$";
		String typeRegExp = "/^[A-Z]{7,9}$";

		if (overtimeRequest.getStartDt() == null || overtimeRequest.getStartDt().matches(dateRegExp)) {
			logger.debug("시작일 에러");
			result = new ReturnResultData(ResultCodeDesc.VACATION_START_DT_FAIL);
		}
		if (overtimeRequest.getEndDt() == null || overtimeRequest.getEndDt().matches(dateRegExp)) {
			logger.debug("종료일 에러");
			result = new ReturnResultData(ResultCodeDesc.VACATION_END_DT_FAIL);
		}
		if (overtimeRequest.getOvertimeType() == null || overtimeRequest.getOvertimeType().matches(typeRegExp)) {
			logger.debug("종료 형태 에러");
			result = new ReturnResultData(ResultCodeDesc.VACATION_START_TYPE_FAIL);
		}

		Date startDt = DateUtil.stringToDate(overtimeRequest.getStartDt());
		Date endDt = DateUtil.stringToDate(overtimeRequest.getEndDt());

		Calendar cal = DateUtil.dateToCalendarTime(startDt, overtimeRequest.getStartHour(), overtimeRequest.getStartMinute());
		Date sDate = cal.getTime();
		cal = DateUtil.dateToCalendarTime(endDt, overtimeRequest.getEndHour(), overtimeRequest.getEndMinute());
		Date eDate = cal.getTime();
		logger.debug("출근일시: " + sDate);
		logger.debug("퇴근일시: " + eDate);

		String start = DateUtil.dateToStringHypen(sDate);
		String end = DateUtil.dateToStringHypen(eDate);
		logger.debug("치환 전: \n{}",overtimeRequest.getOvertimeContent());
		String content = StringEscapeUtils.escapeHtml4(overtimeRequest.getOvertimeContent());
		logger.debug("치환 후: \n{}",content);
		Overtime overtime = Overtime.builder()
								.employeeNo(employeeNo)
								.startDt(start)
								.endDt(end)
								.overtimeType(overtimeRequest.getOvertimeType())
								.overtimeContent(content)
								.build();
		try {
			overtimeService.requestOvertime(overtime);
			result = new ReturnResultData(ResultCodeDesc.VACATION_SUCCESS);
		} catch (Exception e) {
			result = new ReturnResultData(ResultCodeDesc.VACATION_FAIL);
		}
		return result;
	}

	// 총 게시물 수
	@RequestMapping(value = "/count_all_overtime", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData countAllOvertime(HttpSession session, @RequestBody Board board) throws VacationException {

		ReturnResultData result = new ReturnResultData();
		String employeeNo = (String) session.getAttribute("memberSession");
		String adminYN = memberService.getAdminYN(employeeNo);
		int cntAllOvertime = 0;

		if (adminYN == null) {
			result = new ReturnResultData(ResultCodeDesc.ADMINYN_CHECK_FAIL);
		}
		if (adminYN.equals(VacationConstants.STRING_Y)) {
			employeeNo = null;
		}

		try {
			cntAllOvertime = overtimeService.getAllOvertimeCount(employeeNo, board.getSearchWord(), board.getSelectYear());
			result = new ReturnResultData(ResultCodeDesc.COUNT_SUCCESS);
			logger.debug("총 게시물 수(컨트롤러): {}", cntAllOvertime);
		} catch (Exception e) {
			result = new ReturnResultData(ResultCodeDesc.COUNT_FAIL);
		}

		result = new ReturnResultData(ResultCodeDesc.SUCCESS, cntAllOvertime);

		return result;
	}

}
