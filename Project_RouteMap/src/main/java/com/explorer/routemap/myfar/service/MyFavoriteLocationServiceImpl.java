package com.explorer.routemap.myfar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.board.mapper.LocationboardSQLMapper;
import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.myfar.mapper.*;

@Service
public class MyFavoriteLocationServiceImpl {

   @Autowired
   MyFavoriteLocationSQLMapper myFavoriteLocationSQLMapper;
   @Autowired
   LocationboardSQLMapper locationboardSQLMapper;

   // 나의 관심 장소 목록 - 전체
   public List<LocationboardVo> getMyLocationBoardList(int member_no, int currPage) {

      List<LocationboardVo> list = myFavoriteLocationSQLMapper.selectByMemberNo(member_no, currPage);
      List<LocationboardVo> dataList = new ArrayList<LocationboardVo>();

      for (LocationboardVo data : list) {

         int locationboard_no = data.getLocationboard_no();
         LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
         dataList.add(locationboardVo);

      }

      return dataList;

   }

   // 나의 관심 장소 목록 - 여행지 or 맛집
   public List<LocationboardVo> getMySightBoardList(int member_no, int location_category_no, int currPage) {

      List<LocationboardVo> list = myFavoriteLocationSQLMapper.selectByMemberNo(member_no, currPage);
      List<LocationboardVo> dataList = new ArrayList<LocationboardVo>();

      for (LocationboardVo data : list) {

         int locationboard_no = data.getLocationboard_no();
         LocationboardVo locationboardVo = myFavoriteLocationSQLMapper
               .selectByLocationboardNoAndCategoryNo(locationboard_no, location_category_no, currPage);
         dataList.add(locationboardVo);

      }

      return dataList;

   }

}