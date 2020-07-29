package com.explorer.routemap.home.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.explorer.routemap.home.service.HomeServiceImpl;
import com.explorer.routemap.member.service.MemberServiceImpl;

@Controller
@RequestMapping("/*")
public class HomeController {
	
	@Autowired
	HomeServiceImpl homeService;
	@Autowired
	MemberServiceImpl memberService;
	
	@RequestMapping("/")
	public String homePage(Model model) {
		
		return mainPage(model);
	}
	
	@RequestMapping("/main_page.do")
	public String mainPage(Model model) {
		
		// 장소 - 좋아요 가장 높은 글 3건 선택
		List<Map<String,Object>> locationboardDataList = homeService.getMaxLikeLocationboardContent();
		
		if(locationboardDataList != null) {
			
			model.addAttribute("locationboardDataList", locationboardDataList);
			
		}
			
		// 루트 - 좋아요 가장 높은 글 3건 선택
		List<Map<String,Object>> routeboardDataList = homeService.getMaxLikeRouteboardContent();
		
		if(routeboardDataList != null) {
			
			model.addAttribute("routeboardDataList", routeboardDataList);
			
		}
		
		return "home/main_page";
	}
}