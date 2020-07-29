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

import com.explorer.routemap.board.service.LocationboardServiceImpl;
import com.explorer.routemap.board.vo.LocationboardVo;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.myfar.service.MyLocationServiceImpl;

@Controller
@RequestMapping("/myfar/*")
public class MyLocationController {

	@Autowired
	MyLocationServiceImpl myLocationService;
	@Autowired
	LocationboardServiceImpl locationboardService;

	// 나의 여행지/맛집(내가 쓴 여행지/맛집 글)
	@RequestMapping("/fr_my_location_page.do")
	public String myLocationPage() {


		return "myfar/fr_my_location_page";
	}

	// 나의 여행지/맛집(내가 쓴 여행지/맛집 글) - 전체
	@RequestMapping("/fr_my_location_process.do")
	@ResponseBody
	public List<LocationboardVo> myLocationProcess(HttpSession session, Model model,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		List<LocationboardVo> list = new ArrayList<LocationboardVo>();

		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		if (memberVo != null) {
			list = myLocationService.getMyLocationBoardList(memberVo.getMember_no(), currPage);
		}

		return list;
	}

	// 나의 여행지/맛집(내가 쓴 여행지 글) - 여행지 or 맛집
	@RequestMapping("/fr_my_sight_food_process.do")
	@ResponseBody
	public List<LocationboardVo> mySightFoodProcess(int location_category_no, HttpSession session, Model model,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		List<LocationboardVo> list = new ArrayList<LocationboardVo>();
		MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
		if (memberVo != null) {
			list = myLocationService.getMySightBoardList(memberVo.getMember_no(), location_category_no, currPage);
		}

		return list;
	}
	
	// 나의 여행지/맛집(내가 쓴 여행지/맛집 글) 삭제
	// 장소 게시판 - 글 삭제 처리
	@RequestMapping("/fr_my_locationboard_delete_process.do")
	public String deleteContentProcess(int locationboard_no) {

		locationboardService.deleteContent(locationboard_no);

		return "redirect:/myfar/fr_my_location_page.do";
	}

}
