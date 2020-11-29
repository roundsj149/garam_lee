package com.dayside.vacation.main.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dayside.vacation.common.constants.VacationConstants;
import com.dayside.vacation.common.model.ReturnResultData;
import com.dayside.vacation.common.result.ResultCodeDesc;
import com.dayside.vacation.common.util.GsonUtil;
import com.dayside.vacation.member.model.BaseResult;

@Controller
@RequestMapping("/main/*")
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * 세션 확인
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/session_check", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ReturnResultData sessionCheck(HttpSession session) {
		logger.debug("세션 체크");
		ReturnResultData result = null;
		
		String employeeNo = (String) session.getAttribute("memberSession");
		if(employeeNo == null) {
			logger.debug("세션 없음");
			result = new ReturnResultData(ResultCodeDesc.SESSION_CHECK_N, null);
		} else {
			logger.debug("세션 있음");
			result = new ReturnResultData(ResultCodeDesc.SESSION_CHECK_Y, null);
		}
		logger.debug("세션 체크 끝");
		String a = GsonUtil.serialization(result);
		logger.debug("serialization"+a);
		return result;
	}
	
	/**
	 * 에러페이지
	 * @return
	 */
	@RequestMapping(value="/errorPage", method = RequestMethod.GET)
	public ModelAndView errorPage() {
		ModelAndView mav = new ModelAndView();
		logger.debug("에러페이지 연결 컨트롤러");
		mav.setViewName("error/errorPage");
		return mav;
	}
}
