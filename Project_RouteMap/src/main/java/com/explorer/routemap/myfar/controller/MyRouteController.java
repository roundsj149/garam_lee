package com.explorer.routemap.myfar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.explorer.routemap.board.service.*;
import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.member.vo.*;
import com.explorer.routemap.myfar.service.*;

@Controller
@RequestMapping("/myfar/*")
public class MyRouteController {

	@Autowired
	MyRouteServiceImpl myRouteService;
	@Autowired
	RouteboardServiceImpl routeboardService;

	// 나의 루트(내가 쓴 루트)
	@RequestMapping("/fr_my_route_page.do")
	public String myRoutePage(HttpSession session, Model model) {

		return "myfar/fr_my_route_page";
	}

	// 나의 루트(내가 쓴 루트)
	@RequestMapping("/fr_my_route_process.do")
	@ResponseBody
	public List<RouteboardVo> myRouteProcess(HttpSession session, Model model,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		List<RouteboardVo> list = new ArrayList<RouteboardVo>();

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		if (memberVo != null) {
			list = myRouteService.getMyRouteBoardList(memberVo.getMember_no(), currPage);
		}

		return list;
	}

	// 나의 루트(내가 쓴 루트 글) 삭제
	// 루트 게시판 - 글 삭제 처리
	@RequestMapping("/fr_my_routeboard_delete_process.do")
	public String deleteContentProcess(int routeboard_no) {

		routeboardService.deleteContent(routeboard_no);

		return "redirect:/myfar/fr_my_route_page.do";
	}

	// 나의 루트 새로 만들기
	@RequestMapping("/fr_make_my_new_routeboard_process.do")
	public String makeMyNewRoute(HttpSession session, String routeboard_title) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");

		myRouteService.makeNewRoute(memberVo.getMember_no(), routeboard_title);

		return "redirect:/myfar/fr_my_route_page.do";
	}
}
