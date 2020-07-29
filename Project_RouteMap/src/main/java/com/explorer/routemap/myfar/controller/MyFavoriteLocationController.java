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
import com.explorer.routemap.myfar.service.MyFavoriteLocationServiceImpl;

@Controller
@RequestMapping("/myfar/*")
public class MyFavoriteLocationController {

   @Autowired
   MyFavoriteLocationServiceImpl myFavoriteLocationService;
   @Autowired
   LocationboardServiceImpl locationboardService;

   // 메인 페이지(나의 관심)
   @RequestMapping("/fr_my_favorite_page.do")
   public String myFavoritePage() {

      return "myfar/fr_my_favorite_page";
   }

   // 나의 관심 장소 - 전체
   @RequestMapping("/fr_my_favorite_location_process.do")
   @ResponseBody
   public List<LocationboardVo> myLocationProcess(HttpSession session, Model model,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      List<LocationboardVo> list = new ArrayList<LocationboardVo>();

      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      if (memberVo != null) {

         list = myFavoriteLocationService.getMyLocationBoardList(memberVo.getMember_no(), currPage);

      }

      return list;
   }

   // 나의 관심 장소 - 여행지 or 맛집
   @RequestMapping("/fr_my_favorite_sight_food_process.do")
   @ResponseBody
   public List<LocationboardVo> mySightFoodProcess(int location_category_no, HttpSession session, Model model,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      List<LocationboardVo> list = new ArrayList<LocationboardVo>();

      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      if (memberVo != null) {

         list = myFavoriteLocationService.getMySightBoardList(memberVo.getMember_no(), location_category_no,
               currPage);

      }

      return list;

   }

}