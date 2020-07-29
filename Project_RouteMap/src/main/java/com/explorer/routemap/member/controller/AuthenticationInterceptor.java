package com.explorer.routemap.member.controller;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.explorer.routemap.member.service.MemberServiceImpl;
import com.explorer.routemap.member.vo.MemberVo;

// 로그인처리를 담당하는 인터셉터
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Inject
	MemberServiceImpl memberService;

	// preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		// 쿠키 존재하는지 확인
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		// 쿠키가 존재하면 세션 기간 확인
		if (loginCookie != null) {
			 
			MemberVo memberVo = memberService.checkUserWithSessionKey(loginCookie.getValue());
			
			// 세션 기간이 남아있으면 로그인 시켜줌
			if (memberVo != null) {
				session.setAttribute("sessionUser", memberVo);
			}
		}
		return true;
	}

	// 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		if (memberVo != null) {
			session.setAttribute("sessionUser", memberVo);
				
			// 로그인 유지 선택한 사용자가 재접속 시 로그인 유지 기간 7일로 다시 설정
			if (request.getParameter("useCookie") != null) {

				Cookie loginCookie = new Cookie("loginCookie", session.getId());
				loginCookie.setPath("/");
				loginCookie.setMaxAge(60 * 60 * 24 * 7);

				response.addCookie(loginCookie);
			}

		}

	}

}
