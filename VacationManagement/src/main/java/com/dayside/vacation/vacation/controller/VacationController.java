package com.dayside.vacation.vacation.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dayside.vacation.common.util.DateUtil;
import com.dayside.vacation.main.model.Board;
import com.dayside.vacation.member.service.MemberService;
import com.dayside.vacation.vacation.model.Vacation;
import com.dayside.vacation.vacation.service.VacationService;

/**
 * 휴가 관련
 * @author April
 *
 */
@Controller
@RequestMapping("/vacation/*")
public class VacationController {

	private static final Logger logger = LoggerFactory.getLogger(VacationController.class);

	@Autowired
	MemberService memberService;
	@Autowired
	VacationService vacationService;

	// 휴가 신청 목록 페이지
	@RequestMapping("/list")
	public ModelAndView listPage(HttpSession session) {
		ModelAndView listView = new ModelAndView();
		listView.setViewName("vacation/list");
		logger.debug("휴가 신청 페이지 컨트롤러");
		return listView;

	}

	// 휴가 목록 가져오기
	@RequestMapping(value = "/search_vacation", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData searchVacation(HttpSession session, @RequestBody Board board) throws VacationException {
		logger.debug("페이지: {}",board.getCurrPage());
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

		if (adminYN.equals(VacationConstants.STRING_N)) {
			result = new ReturnResultData(ResultCodeDesc.ADMINYN_CHECK_FAIL);
		} else if (adminYN.equals(VacationConstants.STRING_Y)) {
			employeeNo = null;
		}

		try {
			list = vacationService.getVacationInfo(employeeNo, board.getSearchWord(), board.getSelectYear(), board.getCurrPage());
			result = new ReturnResultData(ResultCodeDesc.VACATION_SUCCESS);
		} catch (Exception e) {
			result = new ReturnResultData(ResultCodeDesc.VACATION_FAIL);
		}
		
		result = new ReturnResultData(ResultCodeDesc.SUCCESS, list);
		logger.debug("휴가 목록 가져오기 컨트롤러");
		return result;
	}

	// 휴가 신청 페이지
	@RequestMapping(value = "/request")
	public ModelAndView requestPage(HttpSession session) throws VacationException {

		String employeeNo = (String) session.getAttribute("memberSession");

		Map<String, Object> map = memberService.getNoName(employeeNo);

		ModelAndView listView = new ModelAndView();
		listView.setViewName("vacation/request_page");
		listView.addObject("map", map);

		return listView;
	}

	// 휴가 신청 처리
	@RequestMapping(value = "/request_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData requestProcess(@RequestBody Vacation vacation, HttpSession session) throws Exception {
		logger.debug("신청번호: {}",vacation.getVacationApplyNo());
		logger.debug("시작: {}",vacation.getStartDt());
		logger.debug("종료: {}",vacation.getEndDt());
		ReturnResultData result;
		String dateRegExp = "/[0-9]{2}.-[0-9]{2}.-[0-9]{2}$";
		String typeRegExp = "/[A-Z]{6,9}$";
		
		String startDt = DateUtil.dateToStringHypen(vacation.getStartDt());
		String endDt = DateUtil.dateToStringHypen(vacation.getEndDt());

		if (vacation.getStartDt() == null || startDt.matches(dateRegExp)) {
			logger.debug("시작일 에러");
			result = new ReturnResultData(ResultCodeDesc.VACATION_START_DT_FAIL);
		}
		if (vacation.getStartType() == null || vacation.getStartType().matches(typeRegExp)) {
			logger.debug("시작 형태 에러");
			result = new ReturnResultData(ResultCodeDesc.VACATION_START_TYPE_FAIL);
		}
		if (vacation.getEndDt() == null || startDt.matches(dateRegExp)) {
			logger.debug("종료일 에러");
			result = new ReturnResultData(ResultCodeDesc.VACATION_END_DT_FAIL);
		}
		if (vacation.getEndType() != null && vacation.getEndType().matches(typeRegExp)) {
			logger.debug("종료 형태 에러");
			result = new ReturnResultData(ResultCodeDesc.VACATION_END_TYPE_FAIL);

		}

		// 공휴일
		String[] holidays = { "2020-12-25", "2020-12-31", "2021-01-01", "2021-02-11", "2021-02-12", "2021-03-01",
				"2021-05-05", "2021-05-19", "2021-09-20", "2021-09-21", "2021-09-22", "2021-12-31" };

		Date startDate = DateUtil.stringToDate(startDt);
		Date endDate = DateUtil.stringToDate(endDt);

		long calDate = endDate.getTime() - startDate.getTime();
		int calDateDays = (int) (calDate / (24 * 60 * 60 * 1000));
		float half = 0.5f;

		if (vacation.getEndType() == null) {
			vacation.setEndType(VacationConstants.HYPHEN);
		}

		vacation.setStartDt(startDate);
		vacation.setEndDt(endDate);
		vacation.setStartType(vacation.getStartType());
		vacation.setEndType(vacation.getEndType());
		vacation.setBeforeCountDays(0);
		vacation.setAfterCountDays(0);

		// 시작일 = 종료일
		if (calDateDays == 0) {
			logger.debug("시작일 = 종료일");
			// 시작일: 오전 or 오후
			if (!vacation.getStartType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
				logger.debug("시작일: 오전 or 오후");
				vacation.setCountDays(half);
				vacation.setBeforeCountDays(half);
				vacation.setAfterCountDays(half);
				// 시작일: 종일
			} else {
				vacation.setCountDays(calDateDays + 1);
				vacation.setBeforeCountDays(calDateDays + 1);
				vacation.setAfterCountDays(calDateDays + 1);
			}
			// 종료일 - 시작일 = 1
		} else if (calDateDays == 1) {
			logger.debug("종료일 - 시작일 = 1");
			// 시작일: 종일
			if (vacation.getStartType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
				logger.debug("시작일: 종일");
				// 종료일: 종일
				if (vacation.getEndType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
					logger.debug("종료일: 종일");
					vacation.setCountDays(calDateDays + 1);
					vacation.setBeforeCountDays(calDateDays + 1);
					vacation.setAfterCountDays(calDateDays + 1);
					// 종료일: 오후
				} else {
					logger.debug("종료일: 오후");
					vacation.setCountDays(calDateDays + half);
					vacation.setBeforeCountDays(calDateDays + half);
					vacation.setAfterCountDays(calDateDays + half);
				}
				// 시작일: 오후
			} else {
				logger.debug("시작일: 오후");
				// 종료일: 종일
				if (vacation.getEndType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
					logger.debug("종료일: 종일");
					vacation.setCountDays(calDateDays + half);
					vacation.setBeforeCountDays(calDateDays + half);
					vacation.setAfterCountDays(calDateDays + half);
					// 종료일: 오전
				} else {
					logger.debug("종료일: 오전");
					vacation.setCountDays(half + half);
					vacation.setBeforeCountDays(half + half);
					vacation.setAfterCountDays(half + half);
				}
			}
			// 종료일 - 시작일 > 1
		} else if (calDateDays > 1) {
			logger.debug("종료일 - 시작일 > 1");
			// 다음해로 넘어가는 경우 일수 쪼개기
			Calendar sCalendar = DateUtil.dateToCalendar(startDate); // Date -> Calendar
			Calendar eCalendar = DateUtil.dateToCalendar(endDate);
			String sYear = DateUtil.getYearFromCalendar(sCalendar);
			String eYear = DateUtil.getYearFromCalendar(eCalendar);

			if (!sYear.equals(eYear)) {
				logger.debug("시작연도와 종료연도가 다를 경우!");
				float beforeWeekday = 0;
				// 시작 연도 기준으로 마지막 날까지(12/31까지)
				// 기간 중 주말, 공휴일 빼기
				while (sCalendar.get(Calendar.DATE) > 1) { // 시작일이 1보다 클동안(ex. 12/28, 29, 30, 31)
					logger.debug("일: " + sCalendar.get(Calendar.DATE));
					int day = sCalendar.get(Calendar.DAY_OF_WEEK); // 해당 날짜가 이번주의 몇 번째 날인지 구함
					if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY)) {
						beforeWeekday++; // 주말이 아닌 경우 휴가 일수 +1
						logger.debug("일수 추가: " + beforeWeekday);
					}

					for (int j = 0; j < holidays.length; j++) {
						// String 타입의 공휴일을 Date 타입으로 변환하고, 이를 다시 Calendar 타입으로 변환
						Date holiday = DateUtil.stringToDate(holidays[j]);
						Calendar holidayCheck = Calendar.getInstance();
						holidayCheck.setTime(holiday);
						if (sCalendar.compareTo(holidayCheck) == 0) {
							logger.debug("공휴일 당첨!: " + holidayCheck);
							beforeWeekday--; // 공휴일의 경우 휴가 일수 -1
							logger.debug("일수 감소: " + beforeWeekday);
						}
					}

					sCalendar.add(Calendar.DATE, 1); // 1일 증가
				}

				// 시작일: 종일
				if (vacation.getStartType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
					vacation.setBeforeCountDays(beforeWeekday);
					// 시작일: 오후
				} else if (vacation.getStartType().equals(VacationConstants.VACATION_TYPE_AFTERNOON)) {
					vacation.setBeforeCountDays(half + (beforeWeekday - 1));
				}
				// 종료 연도 기준으로 첫 번째 날까지(1/1까지)
				// 기간 중 주말, 공휴일 빼기
				float afterWeekday = 0;
				while (eCalendar.get(Calendar.DATE) != 31) { // 종료일이 1일이 될 때까지(ex. 1/4, 1/3, 1/2, 1/1)
					logger.debug("일: " + eCalendar.get(Calendar.DATE));
					int day = eCalendar.get(Calendar.DAY_OF_WEEK); // 해당 날짜가 이번주의 몇 번째 날인지 구함
					if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY)) {
						afterWeekday++; // 주말이 아닌 경우 휴가 일수 +1
						logger.debug("일수 추가: " + afterWeekday);
					}

					for (int j = 0; j < holidays.length; j++) {
						// String 타입의 공휴일을 Date 타입으로 변환하고, 이를 다시 Calendar 타입으로 변환
						Date holiday = DateUtil.stringToDate(holidays[j]);
						Calendar holidayCheck = Calendar.getInstance();
						holidayCheck.setTime(holiday);
						if (eCalendar.compareTo(holidayCheck) == 0) {
							logger.debug("공휴일 당첨!: " + holidayCheck);
							afterWeekday--; // 공휴일의 경우 휴가 일수 -1
							logger.debug("일수 감소: " + afterWeekday);
						}
					}

					eCalendar.add(Calendar.DATE, -1); // 1일 감소
				}

				// 종료일: 종일
				if (vacation.getEndType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
					vacation.setAfterCountDays(afterWeekday);
				} else if (vacation.getEndType().equals(VacationConstants.VACATION_TYPE_MORNING)) {
					vacation.setAfterCountDays(half + (afterWeekday - 1));
				}
			} else {

				// 기간 중 주말, 공휴일 빼기
				Calendar start = DateUtil.dateToCalendar(startDate); // Date -> Calendar
				Calendar end = DateUtil.dateToCalendar(endDate);
				int weekday = 0;

				while (!start.after(end)) { // 시작일이 종료일보다 작거나 같을 동안(괄호 안의 날짜보다 나중인지 판단)
					int day = start.get(Calendar.DAY_OF_WEEK); // 해당 날짜가 이번주의 몇 번째 날인지 구함
					if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY)) {
						weekday++; // 주말이 아닌 경우 휴가 일수 +1
					}

					for (int i = 0; i < holidays.length; i++) {
						// String 타입의 공휴일을 Date 타입으로 변환하고, 이를 다시 Calendar 타입으로 변환
						Date holiday = DateUtil.stringToDate(holidays[i]);
						Calendar holidayCheck = DateUtil.dateToCalendar(holiday);
						if (start.compareTo(holidayCheck) == 0) {
							logger.debug("공휴일 당첨!: " + holidayCheck);
							weekday--; // 공휴일의 경우 휴가 일수 -1
						}
					}

					start.add(Calendar.DATE, 1); // 1일 증가
				}

				// 시작일: 종일
				if (vacation.getStartType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
					// 종료일: 종일
					if (vacation.getEndType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
						vacation.setCountDays(weekday);
						vacation.setBeforeCountDays(weekday);
						vacation.setAfterCountDays(weekday);
						// 종료일: 오후
					} else {
						vacation.setCountDays((weekday - 1) + half);
						vacation.setBeforeCountDays((weekday - 1) + half);
						vacation.setAfterCountDays((weekday - 1) + half);
					}
					// 시작일: 오후
				} else {
					// 종료일: 종일
					if (vacation.getEndType().equals(VacationConstants.VACATION_TYPE_ALLDAY)) {
						vacation.setCountDays(half + (weekday - 1));
						vacation.setBeforeCountDays(half + (weekday - 1));
						vacation.setAfterCountDays(half + (weekday - 1));
						// 종료일: 오전
					} else {
						vacation.setCountDays(half + (weekday - 2) + half);
						vacation.setBeforeCountDays(half + (weekday - 2) + half);
						vacation.setAfterCountDays(half + (weekday - 2) + half);
					}
				}
			}
		}
		// 휴가 신청
		if(vacation.getVacationApplyNo() == null) {
			logger.debug("휴가 신청 부분");
			String employeeNo = (String) session.getAttribute("memberSession");
			vacation.setEmployeeNo(employeeNo);
			
			try {
				vacationService.requestVacation(vacation);
				result = new ReturnResultData(ResultCodeDesc.VACATION_SUCCESS);
			} catch (Exception e) {
				result = new ReturnResultData(ResultCodeDesc.VACATION_FAIL);
			}
			// 휴가 수정
		} else {
			logger.debug("휴가 수정 부분");
			vacation.setVacationApplyNo(vacation.getVacationApplyNo());
			try {
				vacationService.updateVacation(vacation);
				result = new ReturnResultData(ResultCodeDesc.VACATION_SUCCESS);
				logger.debug("휴가 수정 성공");
			} catch (Exception e) {
				result = new ReturnResultData(ResultCodeDesc.VACATION_FAIL);
				logger.debug("휴가 수정 실패");
			}
		}
		return result;
	}

	// 휴가 승인 일수
	@RequestMapping(value = "/count_vacation", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData countVacation(HttpSession session) throws VacationException {
		logger.debug("휴가 승인 일수 조회");
		ReturnResultData result;
		float cnt = 0.0f;
		String employeeNo = (String) session.getAttribute("memberSession");
		Date date = new Date();
		String year = DateUtil.dateToStringYear(date);

		try {
			cnt = vacationService.getApprovalVacationDays(employeeNo, null, year);
			logger.debug("휴가 승인 일수 조회 성공");
			result = new ReturnResultData(ResultCodeDesc.APPROVAL_COUNT_SUCCESS);
		} catch (Exception e) {
			logger.debug("휴가 승인 일수 조회 에러");
			result = new ReturnResultData(ResultCodeDesc.APPROVAL_COUNT_FAIL);
		}

		result = new ReturnResultData(ResultCodeDesc.SUCCESS, cnt);

		return result;
	}

	// 휴가 취소
	@RequestMapping(value = "/cancel_process", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public 	ReturnResultData cancelProcess(@RequestBody Vacation vacation) throws VacationException {

		ReturnResultData result = new ReturnResultData();
		String applyNoRegExp = "^[0-9]{14}.-[0-9]{4}$";

		if (vacation.getVacationApplyNo() == null || vacation.getVacationApplyNo().matches(applyNoRegExp)) {
			logger.debug("신청번호 에러: " + vacation.getVacationApplyNo());
			result = new ReturnResultData(ResultCodeDesc.VACATION_NO_INVALID);
		} else {
			logger.debug("휴가 취소 성공: " + vacation.getVacationApplyNo());
			vacationService.cancelVacation(vacation.getVacationApplyNo());
			result = new ReturnResultData(ResultCodeDesc.CANCEL_SUCCESS);
		}

		return result;
	}

	// 휴가 수정 페이지
	@RequestMapping(value = "/update_page")
	public ModelAndView updatePage(String vacationApplyNo) throws VacationException {
		ModelAndView listView = new ModelAndView();
		logger.debug("휴가 수정 컨트롤러: {}",vacationApplyNo);
		if(vacationApplyNo == null) {
			//TODO: 휴가 신청 번호 없을 때 에러페이지로 넘기기
			// listView.setViewName("errorPage");
		} else {
			Map<String, Object> map = vacationService.getOneVacationInfo(vacationApplyNo);
			listView.setViewName("vacation/update_page");
			listView.addObject("vacationInfo", map);
		}

		return listView;
	}

	// 휴가 상태
	@RequestMapping(value = "/vacation_status", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData vacationStatus(HttpSession session, @RequestBody Vacation vacation) throws VacationException {

		Map<String, Object> map = vacationService.getVacationStatus(vacation.getVacationApplyNo());

		String employeeNo = (String) session.getAttribute("memberSession");
		
		ReturnResultData result = new ReturnResultData();

		if (employeeNo.equals(map.get("employeeNo"))) {
			result = new ReturnResultData(ResultCodeDesc.SUCCESS, map.get("vacationStatus"));
		}
		return result;
	}

	// 총 게시물 수, 총 승인 휴가 일수
	@RequestMapping(value = "/count_all_vacation", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData countAllVacation(HttpSession session, @RequestBody Board board) throws VacationException {

		ReturnResultData result;
		Map<String,Object> map = new HashMap<String,Object>();
		String employeeNo = (String) session.getAttribute("memberSession");
		String adminYN = memberService.getAdminYN(employeeNo);
		int cntAllVacation = 0;
		float cntApprovalDays = 0;

		if (adminYN.equals(VacationConstants.STRING_N)) {
			result = new ReturnResultData(ResultCodeDesc.ADMINYN_CHECK_FAIL);
		}
		if (adminYN.equals(VacationConstants.STRING_Y)) {
			employeeNo = null;
		}

		try {
			cntAllVacation = vacationService.getAllVacationCount(employeeNo, board.getSearchWord(), board.getSelectYear());
			logger.debug("총 게시물 수(컨트롤러): {}", cntAllVacation);
		} catch (Exception e) {
			result = new ReturnResultData(ResultCodeDesc.COUNT_FAIL);
		}

		try {
			cntApprovalDays = vacationService.getApprovalVacationDays(employeeNo, board.getSearchWord(), board.getSelectYear());

		} catch (Exception e) {
			result = new ReturnResultData(ResultCodeDesc.APPROVAL_COUNT_FAIL);
		}
		map.put("cntAllVacation", cntAllVacation);
		map.put("cntApprovalDays", cntApprovalDays);
		
		result = new ReturnResultData(ResultCodeDesc.SUCCESS, map);
		return result;
	}
}
