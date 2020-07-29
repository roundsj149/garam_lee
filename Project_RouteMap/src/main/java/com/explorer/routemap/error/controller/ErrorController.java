package com.explorer.routemap.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/*")
public class ErrorController {

	@RequestMapping("/no_authority_page.do")
	public String noAuthorityPage() {
		
		return "error/no_authority_page";
	}
	
	@RequestMapping("/session_exist_page.do")
	public String sessionExistPage() {
		
		return "error/session_exist_page";
	}
	
	@RequestMapping("/error400_page.do")
	public String error400Page() {
		
		return "error/error400_page";
	}
	
	@RequestMapping("/error404_page.do")
	public String error404Page() {
		
		return "error/error404_page";
	}
	
	@RequestMapping("/error500_page.do")
	public String error500Page() {
		
		return "error/error500_page";
	}
	
	@RequestMapping("/common_error_page.do")
	public String commonErrorPage() {
		
		return "error/common_error_page";
	}
}