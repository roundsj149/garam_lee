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

import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.member.vo.*;
import com.explorer.routemap.myfar.service.*;

@Controller
@RequestMapping("/myfar/*")
public class MyFavoriteRouteController {

   @Autowired
   MyFavoriteRouteServiceImpl myFavoriteRouteService;

   // 나의 관심 루트 페이지
   @RequestMapping("/fr_my_favorite_route_page.do")
   public String myRoutePage(HttpSession session, Model model) {

      return "myfar/fr_my_route_page";
   }

   // 나의 관심 루트
   @RequestMapping("/fr_my_favorite_route_process.do")
   @ResponseBody
   public List<RouteboardVo> myRouteProcess(HttpSession session, Model model,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      List<RouteboardVo> list = new ArrayList<RouteboardVo>();

      MemberVo memberVo = (MemberVo) session.getAttribute("sessionUser");
      if (memberVo != null) {
         list = myFavoriteRouteService.getMyRouteBoardList(memberVo.getMember_no(), currPage);
      }

      return list;
   }

}