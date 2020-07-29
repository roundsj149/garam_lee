package com.explorer.routemap.home.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.board.mapper.*;
import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.member.mapper.MemberSQLMapper;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.uploadfile.mapper.LocationboardUploadFileSQLMapper;
import com.explorer.routemap.uploadfile.vo.LocationboardUploadFileVo;

@Service
public class HomeServiceImpl {

	@Autowired
	LocationboardSQLMapper locationboardSQLMapper;
	@Autowired
	RouteboardSQLMapper routeboardSQLMapper;
	@Autowired
	MemberSQLMapper memberSQLMapper;
	@Autowired
	LocationboardUploadFileSQLMapper locationboardUploadFileSQLMapper;

	// 장소 - 좋아요 가장 높은 글 3건 선택
	public List<Map<String, Object>> getMaxLikeLocationboardContent() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			int[] board_no = locationboardSQLMapper.selectMaxLikeLocationboardByNo();

			if (board_no != null) {

				for (int locationboard_no : board_no) {

					Map<String, Object> map = new HashMap<String, Object>();
					LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
					LocationboardUploadFileVo locationboardUploadFileVo = locationboardUploadFileSQLMapper.selectOneByLocationboardNo(locationboard_no);
					MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
					map.put("locationboardVo", locationboardVo);
					map.put("memberVo", memberVo);
					map.put("locationboardUploadFileVo", locationboardUploadFileVo);
					list.add(map);
				}
				return list;
			}

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
		return list;
	}

	// 루트 - 좋아요 가장 높은 글 3건 선택
	public List<Map<String, Object>> getMaxLikeRouteboardContent() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			int[] board_no = routeboardSQLMapper.selectMaxLikeRouteboardByNo();

			if (board_no != null) {

				for (int routeboard_no : board_no) {

					Map<String, Object> map = new HashMap<String, Object>();
					RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);
					MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
					map.put("routeboardVo", routeboardVo);
					map.put("memberVo", memberVo);
					list.add(map);
				}
				return list;
			}

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
		return list;
	}
}