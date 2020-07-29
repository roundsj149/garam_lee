package com.explorer.routemap.myfar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.myfar.mapper.*;

@Service
public class MyRouteServiceImpl {

	@Autowired
	MyRouteSQLMapper myRouteSQLMapper;

	// 내가 쓴 글 - 전체
	public List<RouteboardVo> getMyRouteBoardList(int member_no, int currPage) {

		List<RouteboardVo> list = myRouteSQLMapper.selectByMemberNo(member_no, currPage);

		return list;

	}
	
	// 나의 루트 새로 만들기
	public void makeNewRoute(int member_no, String routeboard_title) {
		
		myRouteSQLMapper.insertMyRoute(member_no, routeboard_title);
		
	}
}
