package com.dayside.vacation.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dayside.vacation.common.model.ReturnResultData;
import com.dayside.vacation.common.result.ResultCodeDesc;
import com.dayside.vacation.member.model.BaseResult;

@ControllerAdvice({"com.dayside.vacation"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VacationExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(VacationExceptionHandler.class);
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ReturnResultData exceptionHandler(Exception e) {
		logger.debug("익셉션 핸들러");
		ReturnResultData result = null;
		
		if(e instanceof VacationException) {
			logger.debug("익셉션 핸들러 - VacationException");
			logger.debug("메시지: ",e.getMessage());
			logger.debug("toString: ",e.toString());
			e.getStackTrace();
			result = new ReturnResultData((ResultCodeDesc)((VacationException) e).getResultCodeDesc());
			logger.debug(result.getBaseResult().getReturnCode());
			logger.debug(result.getBaseResult().getReturnDesc());
			
		} else {
			logger.debug("VacationException 아닌 다른 Exception");
			exceptionHandler2(e);
		}
		
		return result;
	}
	
	public ModelAndView exceptionHandler2(Exception e) {
		logger.debug("익셉션 핸들러 - else -> mav");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/errorPage");
		
		return mav;
	}
}
