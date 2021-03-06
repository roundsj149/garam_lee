package com.dayside.vacation.common.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.dayside.vacation.member.model.BaseResult;

/**
 * aop - 파라미터 / return 확인
 * @author April
 *
 */
@Component
@Aspect
public class LoggerAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

	@Pointcut("execution(* com..controller.MemberController.joinProcess(..))")
	public void joinPointcut() {
	}
	@Pointcut("execution(* com..controller.OvertimeController.requestProcess(..))")
	public void requestPointcut() {
	}

	@Before("joinPointcut()")
	public void joinParameter(JoinPoint joinPoint) {
		Object[] signatureArgs = joinPoint.getArgs();
		for (Object signatureArg : signatureArgs) {
			LOGGER.debug("===================회원가입 파라미터: " + signatureArg);
		}
	}

	@AfterReturning(pointcut = "joinPointcut()", returning="result")
	public void joinReturn(BaseResult result) {
		LOGGER.debug("====================리턴 코드: " + result.getReturnCode());
		LOGGER.debug("====================리턴 설명: " + result.getReturnDesc());
	}
	
	@Before("requestPointcut()")
	public void requestParameter(JoinPoint joinPoint) {
		Object[] signatureArgs = joinPoint.getArgs();
		for (Object signatureArg : signatureArgs) {
			LOGGER.debug("===================초과근무 신청 파라미터: " + signatureArg);
		}
	}

	@AfterReturning(pointcut = "requestPointcut()", returning="result")
	public void requestReturn(BaseResult result) {
		LOGGER.debug("====================리턴 코드: " + result.getReturnCode());
		LOGGER.debug("====================리턴 설명: " + result.getReturnDesc());
	}

}
