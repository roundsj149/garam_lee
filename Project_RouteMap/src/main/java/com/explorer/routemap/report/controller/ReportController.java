package com.explorer.routemap.report.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.report.service.ReportServiceImpl;

@Controller
@RequestMapping("/report/*")
public class ReportController {

	@Autowired
	ReportServiceImpl reportService;

	// 사용자 신고 페이지
	@RequestMapping("report_user_page.do")
	public String ReportUser() {

		return "report/report_user_page";
	}

	// 사용자 신고 처리
	@RequestMapping("report_user_process.do")
	public String reportUserProcess(@RequestParam(required = true) int member_no,
			@RequestParam(required = true) int report_reason, String uri, Model model, HttpSession session) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		reportService.reportUser(memberVo.getMember_no(), member_no, report_reason);

		model.addAttribute("uri", uri);

		return "report/report_user_complete";
	}

	// 장소 게시물 신고 페이지
	@RequestMapping("report_location_page.do")
	public String ReportLocationPage() {

		return "report/report_location_page";
	}

	// 장소 게시물 신고 처리
	@RequestMapping("report_location_process.do")
	public String reportLocationProcess(@RequestParam(required = true) int locationboard_no,
			@RequestParam(required = true) int report_reason, String uri, Model model, HttpSession session) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		reportService.reportLocation(memberVo.getMember_no(), locationboard_no, report_reason);

		model.addAttribute("uri", uri);

		return "report/report_location_complete";
	}

	// 루트 게시물 신고 페이지
	@RequestMapping("report_route_page.do")
	public String ReportRoutePage() {

		return "report/report_route_page";
	}

	// 루트 게시물 신고 처리
	@RequestMapping("report_route_process.do")
	public String reportRouteProcess(@RequestParam(required = true) int routeboard_no,
			@RequestParam(required = true) int report_reason, String uri, Model model, HttpSession session) {

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		reportService.reportRoute(memberVo.getMember_no(), routeboard_no, report_reason);

		model.addAttribute("uri", uri);

		return "report/report_route_complete";
	}
}
