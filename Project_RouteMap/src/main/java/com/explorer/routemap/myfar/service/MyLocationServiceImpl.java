package com.explorer.routemap.myfar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.myfar.mapper.*;

@Service
public class MyLocationServiceImpl {

	@Autowired
	MyLocationSQLMapper myLocationSQLMapper;

	// 내가 쓴 글 - 전체
	public List<LocationboardVo> getMyLocationBoardList(int member_no, int currPage) {

		List<LocationboardVo> list = myLocationSQLMapper.selectByMemberNo(member_no, currPage);

		return list;

	}
	
	// 내가 쓴 글 - 여행지
	public List<LocationboardVo> getMySightBoardList(int member_no, int location_category_no, int currPage) {

		List<LocationboardVo> list = myLocationSQLMapper.selectByMemberNoAndCategoryNo(member_no, location_category_no, currPage);

		return list;
	}
}
