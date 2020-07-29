package com.explorer.routemap.myfar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.board.mapper.RouteboardSQLMapper;
import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.myfar.mapper.*;

@Service
public class MyFavoriteRouteServiceImpl {

   @Autowired
   MyFavoriteRouteSQLMapper myFavoriteRouteSQLMapper;
   @Autowired
   RouteboardSQLMapper routeboardSQLMapper;

   // 나의 관심 루트 목록
   public List<RouteboardVo> getMyRouteBoardList(int member_no, int currPage) {

      List<RouteboardVo> list = myFavoriteRouteSQLMapper.selectByMemberNo(member_no, currPage);
      List<RouteboardVo> dataList = new ArrayList<RouteboardVo>();

      for (RouteboardVo data : list) {

         int routeboard_no = data.getRouteboard_no();
         RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);
         dataList.add(routeboardVo);

      }

      return dataList;

   }
   
}