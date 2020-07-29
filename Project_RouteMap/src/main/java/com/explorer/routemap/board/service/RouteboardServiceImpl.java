package com.explorer.routemap.board.service;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.board.mapper.*;
import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.member.mapper.*;
import com.explorer.routemap.member.vo.*;
import com.explorer.routemap.uploadfile.mapper.LocationboardUploadFileSQLMapper;
import com.explorer.routemap.uploadfile.vo.*;

@Service
public class RouteboardServiceImpl {

	@Autowired
	RouteboardSQLMapper routeboardSQLMapper;
	@Autowired
	LinkSQLMapper linkSQLMapper;
	@Autowired
	LocationboardSQLMapper locationboardSQLMapper;
	@Autowired
	MemberSQLMapper memberSQLMapper;
	@Autowired
	LocationCategorySQLMapper locationCategorySQLMapper;
	@Autowired
	MoodCategorySQLMapper moodCategorySQLMapper;
	@Autowired
	ProvinceCategorySQLMapper provinceCategorySQLMapper;
	@Autowired
	TypeCategorySQLMapper typeCategorySQLMapper;
	@Autowired
	RouteboardReplySQLMapper routeboardReplySQLMapper;
	@Autowired
	LocationboardUploadFileSQLMapper locationboardUploadFileSQLMapper;
	@Autowired
	RouteboardLikeSQLMapper routeboardLikeSQLMapper;
	@Autowired
	LocationboardLikeSQLMapper locationboardLikeSQLMapper;
	@Autowired
	LocationboardMyfavoriteSQLMapper locationboardMyfavoriteSQLMapper;
	@Autowired
	LevelCategorySQLMapper levelCategorySQLMapper;
	@Autowired
	RouteboardMyfavoriteSQLMapper routeboardMyfavoriteSQLMapper;

//	// 전체 글 리스트
//	public List<Map<String, Object>> getBoardList(int currPage, String search_word) {
//
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//		List<RouteboardVo> routeboardVoList = routeboardSQLMapper.selectByTitle(currPage, search_word);
//
//		for (RouteboardVo routeboardVo : routeboardVoList) {
//
//			Map<String, Object> map = new HashMap<String, Object>();
//
//			MemberVo memberVo = new MemberVo();
//			memberVo.setMember_no(routeboardVo.getMember_no());
//
//			map.put("routeboardVo", routeboardVo);
//			map.put("memberVo", memberVo);
//
//			list.add(map);
//		}
//
//		return list;
//	}

	// 루트공유 - 글 읽기(루트)
	public Map<String, Object> readRouteContent(int routeboard_no, int member_no, HttpServletResponse response,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

/////////////////////////////////// 조회수 증가방지 구현중/////////////////////////////////////////////
		int countCheck = 0;
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("routeboard_no" + routeboard_no)) {
					countCheck = 0;
					break;
				} else {
					Cookie cookie = new Cookie("routeboard_no" + routeboard_no, String.valueOf(routeboard_no));
					cookie.setMaxAge(60 * 60 * 24);
					cookie.setPath("/");
					response.addCookie(cookie);

					countCheck += 1;
				}
			}
		}
		if (countCheck > 0) {
			routeboardSQLMapper.updateReadcount(routeboard_no);
		}

		RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);

		MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
		LevelCategoryVo levelCategoryVo = levelCategorySQLMapper.selectByExp(memberVo.getMember_levelexp());
		// 루트관심등록
		RouteboardMyfavoriteVo myFarRouteVo = routeboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
				routeboard_no);

		map.put("myFarRouteVo", myFarRouteVo);
		map.put("routeboardVo", routeboardVo);
		map.put("levelCategoryVo", levelCategoryVo);
		map.put("memberVo", memberVo);

		return map;
	}

	// 루트공유 - 글 읽기(링크, 장소)
	public List<Map<String, Object>> readLocationContent(int routeboard_no, int member_no) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<LinkVo> linkVoList = linkSQLMapper.selectLinkAndLocationByRouteboardNo(routeboard_no);

		if (linkVoList != null) {

			for (LinkVo linkVo : linkVoList) {

				Map<String, Object> map = new HashMap<String, Object>();

				LocationboardVo locationboardVo = locationboardSQLMapper
						.selectByLocationboardNo(linkVo.getLocationboard_no());
				System.out.println("dd" + locationboardVo);
				LocationCategoryVo locationCategoryVo = locationCategorySQLMapper
						.selectByNo(locationboardVo.getLocation_category_no());
				MoodCategoryVo moodCategoryVo = moodCategorySQLMapper.selectByNo(locationboardVo.getMood_category_no());
				ProvinceCategoryVo provinceCategoryVo = provinceCategorySQLMapper
						.selectByNo(locationboardVo.getProvince_category_no());
				TypeCategoryVo typeCategoryVo = typeCategorySQLMapper.selectByNo(locationboardVo.getType_category_no());
				MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
				LevelCategoryVo levelCategoryVo = levelCategorySQLMapper.selectByExp(memberVo.getMember_levelexp());
				// 루트 안의 장소 관심등록
				LocationboardMyfavoriteVo myFarVo = locationboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
						linkVo.getLocationboard_no());

				List<LocationboardUploadFileVo> locationboardUploadFileVoList = locationboardUploadFileSQLMapper
						.selectByLocationboardNo(locationboardVo.getLocationboard_no());
				int locationboardCount = locationboardLikeSQLMapper.likeCount(linkVo.getLocationboard_no());

				locationboardVo
						.setLocationboard_content(locationboardVo.getLocationboard_content().replace("\r\n", "<br>"));

				map.put("locationboardVo", locationboardVo);
				map.put("locationCategoryVo", locationCategoryVo);
				map.put("moodCategoryVo", moodCategoryVo);
				map.put("provinceCategoryVo", provinceCategoryVo);
				map.put("typeCategoryVo", typeCategoryVo);
				map.put("linkVo", linkVo);
				map.put("locationboardVo", locationboardVo);
				map.put("memberVo", memberVo);
				map.put("levelCategoryVo", levelCategoryVo);
				map.put("locationboardUploadFileVoList", locationboardUploadFileVoList);
				map.put("locationboardCount", locationboardCount);
				map.put("myFarVo", myFarVo);

				list.add(map);
			}
		}

		return list;
	}

	// 루트 공유 - 글 삭제
	public void deleteContent(int routeboard_no) {

		// 게시글 삭제하고 경험치 감소
		RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
		memberSQLMapper.loseExpByPost(memberVo);

		routeboardReplySQLMapper.deleteAll(routeboard_no);
		linkSQLMapper.deleteRouteboard(routeboard_no);
		routeboardLikeSQLMapper.deleteByRouteboardNo(routeboard_no);
		routeboardMyfavoriteSQLMapper.deleteMyfavorite(routeboard_no);
		routeboardSQLMapper.delete(routeboard_no);
	}

	// 루트 공유 - 글 수정
	public RouteboardVo updateContent(MemberVo memberVo, RouteboardVo routeboardVo) {

		routeboardSQLMapper.update(routeboardVo);
		// 루트 글쓰기 후 쓴 글 가져오기(비용, 소요시간 카테고리를 넣어주기 위함)
		RouteboardVo returnRouteboardVo = routeboardSQLMapper
				.selectRouteByRouteboardNo(routeboardVo.getRouteboard_no());

		return returnRouteboardVo;
	}

	// 루트 공유 - 링크 리셋 : 루트 글 업데이트를 위하여
	public void resetLink(RouteboardVo routeboardVo) {

		linkSQLMapper.deleteLinkForUpdate(routeboardVo.getRouteboard_no());

	}

	// 루트 공유 - 댓글 쓰기
	public void writeReply(int member_no, int routeboard_no, String routeboard_reply_content) {

		routeboardReplySQLMapper.insert(member_no, routeboard_no, routeboard_reply_content);

		// 댓글 쓰고 경험치 획득
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(member_no);
		memberSQLMapper.getExpByReply(memberVo);
	}

	// 루트 공유 - 댓글 목록
	public List<Map<String, Object>> getRepleList(int routeboard_no, int member_no) {

		List<RouteboardReplyVo> routeboardReplyVoList = routeboardReplySQLMapper.selectByRouteboardNo(routeboard_no);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (RouteboardReplyVo routeboardReplyVo : routeboardReplyVoList) {

			Map<String, Object> map = new HashMap<String, Object>();

			MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardReplyVo.getMember_no());

			map.put("routeboardReplyVo", routeboardReplyVo);
			map.put("memberVo", memberVo);

			if (memberVo.getMember_no() == member_no) {
				map.put("sessionMemberNo", member_no);
			}

			list.add(map);
		}

		return list;
	}

	// 루트 공유 - 댓글 삭제
	public void deleteReply(int routeboard_reply_no) {

		// 댓글 삭제 경험치 감소
		RouteboardReplyVo routeboardReplyVo = routeboardReplySQLMapper.selectByReplyNo(routeboard_reply_no);
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardReplyVo.getMember_no());
		memberSQLMapper.loseExpByReply(memberVo);

		routeboardReplySQLMapper.delete(routeboard_reply_no);
	}

	// 루트 공유 - 댓글 수정
	public void updateReply(String routeboard_reply_content, int routeboard_reply_no) {

		routeboardReplySQLMapper.update(routeboard_reply_content, routeboard_reply_no);

	}

	// 좋아요 클릭
	public int likeCheck(int routeboard_no, int member_no) {

		int check_member_no = 0;

		RouteboardLikeVo routeboardLikeVo = routeboardLikeSQLMapper.selectByRouteboardNoAndMemberNo(member_no,
				routeboard_no);

		if (routeboardLikeVo != null) {

			// 경험치 감소 - 좋아요 취소: 글쓴이가 감소
			RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
			memberSQLMapper.loseExpByLike(memberVo);

			check_member_no = routeboardLikeVo.getMember_no();
			routeboardLikeSQLMapper.deleteMemberNo(member_no, routeboard_no);
			return check_member_no; // 좋아요 이미 함 -> 빈 하트로 바뀌어야 함

		} else {

			// 경험치 획득 - 좋아요 : 글쓴이가 획득
			RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
			memberSQLMapper.getExpByLike(memberVo);

			routeboardLikeSQLMapper.insertMemberNoAndRouteNo(member_no, routeboard_no);
			return check_member_no; // 좋아요한 적 없음 -> 빨간 하트로 바뀌어야 함
		}
	}

	// 좋아요 출력
	public List<RouteboardLikeVo> likeAllCheck(int member_no) {

		List<RouteboardLikeVo> list = routeboardLikeSQLMapper.selectAllLike(member_no);

		return list;
	}

	// 게시물 1건에 대한 좋아요 수
	public int likeCount(int routeboard_no) {

		int like_count = routeboardLikeSQLMapper.likeCount(routeboard_no);

		return like_count;
	}

	// 루트 공유 - 글 쓰기
	public RouteboardVo writeContent(RouteboardVo routeboardVo) {

		int routeboard_no = routeboardSQLMapper.createRouteboardKey();
		routeboardVo.setRouteboard_no(routeboard_no);
		routeboardSQLMapper.insert(routeboardVo);
		// 루트 글쓰기 후 쓴 글 가져오기(비용, 소요시간 카테고리를 넣어주기 위함)
		RouteboardVo returnRouteboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);

		// 게시글 쓰고 경험치 획득
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
		memberSQLMapper.getExpByPost(memberVo);

		return returnRouteboardVo;
	}

	// 루트 공유 - 글쓰기 - 비용, 소요시간, 카테고리값 넣기
	public void rewriteContent(RouteboardVo routeboardVo) {
		System.out.println("시간 카테고리" + routeboardVo.getTime_category_no());
		routeboardSQLMapper.updateCostAndTime(routeboardVo);

	}

	// 루트 공유 - 링크 연결 : 장소와 루트를 링크
	public void linkLocation(LinkVo linkVo, LocationboardVo locationboardVo, RouteboardVo routeboardVo) {

		int link_no = linkSQLMapper.createLinkKey();
		linkVo.setLink_no(link_no);

		linkSQLMapper.insert(linkVo, locationboardVo, routeboardVo);

	}

	// 관심 장소 게시글 목록 불러오기
	public List<Map<String, Object>> getMyfavoriteLocationboardList(int member_no) {

		List<LocationboardMyfavoriteVo> LocationboardMyfavoriteVoList = locationboardMyfavoriteSQLMapper
				.selectByMemberNo(member_no);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (LocationboardMyfavoriteVo locationboardMyfavoriteVo : LocationboardMyfavoriteVoList) {

			Map<String, Object> map = new HashMap<String, Object>();

			LocationboardVo locationboardVo = locationboardSQLMapper
					.selectByLocationboardNo(locationboardMyfavoriteVo.getLocationboard_no());

			MemberVo memberVo = new MemberVo();
			memberVo.setMember_no(locationboardVo.getMember_no());

			map.put("memberVo", memberVo);
			map.put("locationboardVo", locationboardVo);

			list.add(map);

		}

		return list;
	}

	// getProvinceBoardList
	public List<Map<String, Object>> getSpecificBoardList(int member_no, int[] province_checkbox_no,
			int[] cost_checkbox_no, int[] time_checkbox_no, int align_no, String searchWord, int currPage) {

		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		StringBuffer sql4 = new StringBuffer();

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<Map<String, Object>> routeboardList = new ArrayList<Map<String, Object>>();

		sql1.append(
				"SELECT * FROM (SELECT ROWNUM rnum, t1.* FROM (SELECT  distinct(rb.routeboard_no), rb.routeboard_title, rb.routeboard_content, rb.routeboard_writedate, rb.routeboard_readcount FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no");
		sql2.append(
				"SELECT * FROM (SELECT ROWNUM rnum, t1.* FROM (SELECT distinct(rb.routeboard_no), rb.routeboard_title, rb.routeboard_content, rb.routeboard_writedate, rb.routeboard_readcount FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no");
		sql3.append(
				"SELECT count(distinct(rb.routeboard_no)) FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no");
		sql4.append(
				"SELECT count(distinct(rb.routeboard_no)) FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no");

		// 좋아요순으로 정렬된 routeboard_no
		int[] like_routeboard_no = null;

		// 필터 적용 안된 검색
		if (province_checkbox_no[0] == 0 && cost_checkbox_no[0] == 0 && time_checkbox_no[0] == 0 && align_no == 1) {
			if (!(searchWord.equals(""))) {
				sql1.append(" AND rb.routeboard_title LIKE '%" + searchWord + "%'");
				sql3.append(" AND rb.routeboard_title LIKE '%" + searchWord + "%'");
			}
			sql1.append(" ORDER BY rb.routeboard_no DESC) t1) t2 WHERE t2.rnum >= (" + currPage
					+ "-1)*12+1 AND t2.rnum <= (" + currPage + "-1)*12+12");
			sql3.append(" ORDER BY rb.routeboard_no DESC");
			System.out.println(" 전체쿼리" + sql1.toString());
			map.put("SELECT_ROUTEBOARD", sql1.toString());
			map2.put("COUNT_ROUTEBOARD", sql3.toString());
		} else { // 필터 적용된 검색

			// 검색어
			if (!(searchWord.equals(""))) {
				sql2.append(" AND rb.routeboard_title LIKE '%" + searchWord + "%'");
				sql4.append(" AND rb.routeboard_title LIKE '%" + searchWord + "%'");
			}

			// 지역
			if (province_checkbox_no[0] != 0) {

				sql2.append(" AND lb.province_category_no IN (");
				sql4.append(" AND lb.province_category_no IN (");
				for (int i = 0; i < province_checkbox_no.length; i++) {

					if (i == province_checkbox_no.length - 1) {
						sql2.append(province_checkbox_no[i] + ")");
						sql4.append(province_checkbox_no[i] + ")");
					} else {
						sql2.append(province_checkbox_no[i] + ",");
						sql4.append(province_checkbox_no[i] + ",");
					}
				}
			}

			// 비용
			if (cost_checkbox_no[0] != 0) {
				sql2.append(" AND rb.cost_category_no IN (");
				sql4.append(" AND rb.cost_category_no IN (");
				for (int i = 0; i < cost_checkbox_no.length; i++) {
					if (i == cost_checkbox_no.length - 1) {
						sql2.append(cost_checkbox_no[i] + ")");
						sql4.append(cost_checkbox_no[i] + ")");
					} else {
						sql2.append(cost_checkbox_no[i] + ",");
						sql4.append(cost_checkbox_no[i] + ",");

					}
				}
			}

			// 소요시간
			if (time_checkbox_no[0] != 0) {
				sql2.append(" AND rb.time_category_no IN (");
				sql4.append(" AND rb.time_category_no IN (");
				for (int i = 0; i < time_checkbox_no.length; i++) {
					if (i == time_checkbox_no.length - 1) {
						sql2.append(time_checkbox_no[i] + ")");
						sql4.append(time_checkbox_no[i] + ")");
					} else {
						sql2.append(time_checkbox_no[i] + ",");
						sql4.append(time_checkbox_no[i] + ",");
					}
				}
			}

			// 정렬
			if (align_no == 1) {
				sql2.append(" ORDER BY rb.routeboard_no DESC) t1) t2 WHERE t2.rnum >= (" + currPage
						+ "-1)*12+1 AND t2.rnum <= (" + currPage + "-1)*12+12");
				sql4.append(" ORDER BY rb.routeboard_no DESC");
			}
			if (align_no == 2) {
				sql2.append(" ORDER BY rb.routeboard_readcount DESC) t1) t2 WHERE t2.rnum >= (" + currPage
						+ "-1)*12+1 AND t2.rnum <= (" + currPage + "-1)*12+12");
				sql4.append(" ORDER BY rb.routeboard_readcount DESC");
			} else if (align_no == 3) {

				like_routeboard_no = routeboardSQLMapper.likeAlign();

				// sql2.append(") t1) t2 WHERE t2.rnum >= (" + currPage + "-1)*12+1 AND t2.rnum
				// <= (" + currPage+ "-1)*12+12");
				sql2.append(") t1) t2");
			}

			System.out.println("최종 쿼리" + sql2.toString());
			System.out.println(sql4.toString());
			map.put("SELECT_ROUTEBOARD", sql2.toString());
			map2.put("COUNT_ROUTEBOARD", sql4.toString());
		}

		// 필터 적용 된 검색

		int count = 0;

		try {
			count = routeboardSQLMapper.countAllWithCondition(map2);
		} catch (Exception e) {
			count = 0;
			e.printStackTrace();
		}
		List<RouteboardVo> list = routeboardSQLMapper.selectAllWithCondition(map);

		// 좋아요순
		if (align_no == 3 && like_routeboard_no != null) {

			// 1) 좋아요 된 글 먼저 넣기
			for (int like_board_no : like_routeboard_no) {
				// System.out.println("like_board_no"+like_board_no);
				for (RouteboardVo routeboardVo : list) {

					Map<String, Object> boardMap = new HashMap<String, Object>();

					if (routeboardVo.getRouteboard_no() == like_board_no) {
						// System.out.println("같음board_no" + routeboardVo.getRouteboard_no());
						if (member_no == 0) {

							boardMap.put("routeboardVo", routeboardVo);
							boardMap.put("count", count);

						} else {

							RouteboardMyfavoriteVo myFarVo = routeboardMyfavoriteSQLMapper
									.selectMyfavoriteAllCheck(member_no, routeboardVo.getRouteboard_no());
							boardMap.put("myFarVo", myFarVo);
							boardMap.put("routeboardVo", routeboardVo);
							boardMap.put("count", count);
						}

						routeboardList.add(boardMap);
					}
				}
			}

			// 2) 좋아요 없는 글들
			for (RouteboardVo routeboardVo : list) {
				int likeCount = 0;
				for (int like_board_no : like_routeboard_no) {

					if (routeboardVo.getRouteboard_no() == like_board_no) {
						likeCount++;
					}

				}
				if (likeCount == 0) {
					Map<String, Object> boardMap = new HashMap<String, Object>();

					// System.out.println("다름board_no" + routeboardVo.getRouteboard_no());
					if (member_no == 0) {

						boardMap.put("routeboardVo", routeboardVo);
						boardMap.put("count", count);

					} else {

						RouteboardMyfavoriteVo myFarVo = routeboardMyfavoriteSQLMapper
								.selectMyfavoriteAllCheck(member_no, routeboardVo.getRouteboard_no());
						boardMap.put("myFarVo", myFarVo);
						boardMap.put("routeboardVo", routeboardVo);
						boardMap.put("count", count);
					}

					routeboardList.add(boardMap);
				}

			}

		} else {
			for (RouteboardVo routeboardVo : list) {

				Map<String, Object> boardMap = new HashMap<String, Object>();

				if (member_no == 0) {

					boardMap.put("routeboardVo", routeboardVo);
					boardMap.put("count", count);
					// System.out.println("카운트else if부분: "+count);
				} else {

					RouteboardMyfavoriteVo myFarVo = routeboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
							routeboardVo.getRouteboard_no());
					boardMap.put("myFarVo", myFarVo);
					boardMap.put("routeboardVo", routeboardVo);
					boardMap.put("count", count);
				}
				routeboardList.add(boardMap);
			}
		}
		return routeboardList;
	}

	// 루트 관심등록 클릭
	public Map<String, Object> favoriteCheck(int member_no, int routeboard_no) {

		Map<String, Object> map = new HashMap<String, Object>();

		RouteboardMyfavoriteVo farRouteVo = routeboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
				routeboard_no);
		if (farRouteVo == null) {

			routeboardMyfavoriteSQLMapper.insertMyfavoriteCheck(member_no, routeboard_no);

			// 경험치 획득 - 관심 등록 : 글쓴이가 획득
			RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
			memberSQLMapper.getExpByFavorite(memberVo);
			map.put("returnCode", "0001");

		} else {

			// 경험치 감소 - 관심 등록 취소 : 글쓴이가 감소
			RouteboardVo routeboardVo = routeboardSQLMapper.selectRouteByRouteboardNo(routeboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(routeboardVo.getMember_no());
			memberSQLMapper.loseExpByFavorite(memberVo);
			
			routeboardMyfavoriteSQLMapper.deleteMyfavoriteCheck(member_no, routeboard_no);

			map.put("returnCode", "0002");
		}

		return map;
	}

	// 게시글 번호로 글쓴이 뽑아오기
	public int getMemberNo(int routeboard_no) {

		int member_no = routeboardSQLMapper.selectMemberNoByRouteboardNo(routeboard_no);

		return member_no;

	}

	// 게시글 댓글 번호로 댓글 작성자 뽑아오기
	public int getReplyMemberNo(int routeboard_reply_no) {

		int member_no = routeboardSQLMapper.selectMemberNoByRouteboardReplyNo(routeboard_reply_no);

		return member_no;

	}

}
