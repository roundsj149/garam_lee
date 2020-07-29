package com.explorer.routemap.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.explorer.routemap.board.mapper.*;
import com.explorer.routemap.board.vo.*;
import com.explorer.routemap.member.mapper.*;
import com.explorer.routemap.member.vo.*;
import com.explorer.routemap.uploadfile.mapper.*;
import com.explorer.routemap.uploadfile.vo.*;

@Service
public class LocationboardServiceImpl {

	@Autowired
	LocationboardSQLMapper locationboardSQLMapper;
	@Autowired
	LocationboardUploadFileSQLMapper locationboardUploadFileSQLMapper;
	@Autowired
	MemberSQLMapper memberSQLMapper;
	@Autowired
	ProvinceCategorySQLMapper provinceCategorySQLMapper;
	@Autowired
	LocationCategorySQLMapper locationCategorySQLMapper;
	@Autowired
	TypeCategorySQLMapper typeCategorySQLMapper;
	@Autowired
	MoodCategorySQLMapper moodCategorySQLMapper;
	@Autowired
	LocationboardReplySQLMapper locationboardReplySQLMapper;
	@Autowired
	LocationboardLikeSQLMapper locationboardLikeSQLMapper;
	@Autowired
	LinkSQLMapper linkSQLMapper;
	@Autowired
	LocationboardMyfavoriteSQLMapper locationboardMyfavoriteSQLMapper;
	@Autowired
	LevelCategorySQLMapper levelCategorySQLMapper;

	// 글쓰기
	public void writeContent(LocationboardVo locationboardVo, String addressCut) {

		int locationboard_no = locationboardSQLMapper.createLocationboardKey();

		locationboardVo.setLocationboard_no(locationboard_no);

		if (addressCut.equals("충남") || addressCut.equals("충북")) {
			addressCut = "충청";
		} else if (addressCut.equals("경남") || addressCut.equals("경북")) {
			addressCut = "경상";
		} else if (addressCut.equals("전남") || addressCut.equals("전북")) {
			addressCut = "전라";
		}

		int province_category_no = 15;

		if (addressCut.equals("정보없음")) {
			province_category_no = 15;
		} else {
			province_category_no = provinceCategorySQLMapper.selectByLocationCategoryName(addressCut);
		}

		locationboardVo.setProvince_category_no(province_category_no);
		locationboardSQLMapper.insert(locationboardVo);

		// 게시글 쓰고 경험치 획득
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
		memberSQLMapper.getExpByPost(memberVo);

	}

	// 파일올리기
	public void uploadfiles(MultipartFile file, int locationboard_no) {

		String locationboard_originalname = file.getOriginalFilename();
		String folder = "/var/storage/routemap/upload/locationboard_file_upload/";
		
		Date today = new Date();

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String todayFolder = df.format(today);

		String saveFolderName = folder + todayFolder;

		File saveFolder = new File(saveFolderName);

		if (!saveFolder.exists()) {
			saveFolder.mkdirs();
		}

		Calendar calendar = Calendar.getInstance();
		String fileRandomName = "";
		fileRandomName += calendar.get(Calendar.YEAR);
		fileRandomName += calendar.get(Calendar.MONTH);
		fileRandomName += calendar.get(Calendar.DATE);
		fileRandomName += calendar.get(Calendar.HOUR);
		fileRandomName += calendar.get(Calendar.MINUTE);
		fileRandomName += calendar.get(Calendar.SECOND);
		fileRandomName += calendar.get(Calendar.MILLISECOND);
		fileRandomName += locationboard_originalname.substring(locationboard_originalname.lastIndexOf("."));

		String locationboard_file_link_path = todayFolder + "/" + fileRandomName;
		String locationboard_file_real_path = saveFolderName + "/" + fileRandomName; // 파일 실제 저장 위치

		LocationboardUploadFileVo locationboardUploadFileVo = new LocationboardUploadFileVo();

		locationboardUploadFileVo.setLocationboard_no(locationboard_no);
		locationboardUploadFileVo.setLocationboard_originalname(locationboard_originalname);
		locationboardUploadFileVo.setLocationboard_file_link_path(locationboard_file_link_path);
		locationboardUploadFileVo.setLocationboard_file_real_path(locationboard_file_real_path);

		locationboardUploadFileSQLMapper.insertFiles(locationboardUploadFileVo);

		try {

			file.transferTo(new File(locationboard_file_real_path));

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	// 글수정
	public void updateContent(LocationboardVo locationboardVo, String addressCut) {

		if (addressCut.equals("충남") || addressCut.equals("충북")) {
			addressCut = "충청";
		} else if (addressCut.equals("경남") || addressCut.equals("경북")) {
			addressCut = "경상";
		} else if (addressCut.equals("전남") || addressCut.equals("전북")) {
			addressCut = "전라";
		}

		int province_category_no = 15;

		if (addressCut.equals("정보없음")) {
			province_category_no = 15;
		} else {
			province_category_no = provinceCategorySQLMapper.selectByLocationCategoryName(addressCut);
		}

		locationboardVo.setProvince_category_no(province_category_no);
		locationboardSQLMapper.update(locationboardVo);

	}

	// 파일 개별 삭제
	public void deleteFile(String file_no) {

		int locationboard_file_no = Integer.parseInt(file_no);

		LocationboardUploadFileVo locationboardUploadFileVo = locationboardUploadFileSQLMapper
				.selectByFileNo(locationboard_file_no);

		String path = locationboardUploadFileVo.getLocationboard_file_real_path();

		File file = new File(path);

		if (file.exists() == true) {

			file.delete();

			locationboardUploadFileSQLMapper.deleteByFileNo(locationboard_file_no);

		}
	}

	// 홈쪽에서 날아오는 글리스트
	public List<Map<String, Object>> getAllBoardList(int currPage, String search_word, int member_no) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<LocationboardVo> boardList = new ArrayList<LocationboardVo>();

		if (search_word != null) { // 제목 검색

			boardList = locationboardSQLMapper.selectByTitle(search_word, currPage);

		} else { // 전체

			boardList = locationboardSQLMapper.selectAllBoardList(currPage);
		}

		for (LocationboardVo locationboardVo : boardList) {

			MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
			LocationboardUploadFileVo locationboardUploadFileVo = locationboardUploadFileSQLMapper
					.selectOneByLocationboardNo(locationboardVo.getLocationboard_no());

			Map<String, Object> map = new HashMap<String, Object>();

			if (member_no == 0) {

				map.put("memberVo", memberVo);
				map.put("locationboardVo", locationboardVo);
				map.put("locationboardUploadFileVo", locationboardUploadFileVo);

				list.add(map);

			} else {

				LocationboardMyfavoriteVo myFarVo = locationboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
						locationboardVo.getLocationboard_no());

				map.put("memberVo", memberVo);
				map.put("locationboardVo", locationboardVo);
				map.put("locationboardUploadFileVo", locationboardUploadFileVo);
				map.put("myFarVo", myFarVo);

				list.add(map);
			}
		}
		return list;

	}

	/*
	 * // 전체 글 리스트 public List<Map<String, Object>> getAllBoardList(int currPage,
	 * int[] province_category_no, int select_search_no, String search_word, int[]
	 * select_cost, int[] select_type, int member_no) {
	 * 
	 * List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	 * List<LocationboardVo> boardList = new ArrayList<LocationboardVo>();
	 * 
	 * if (province_category_no[0] == 0 && province_category_no.length == 1) { if
	 * (search_word != null) { // 제목 검색 boardList =
	 * locationboardSQLMapper.selectByTitle(search_word, currPage); } else { // 전체
	 * 검색 boardList = locationboardSQLMapper.selectAllBoardList(currPage); }
	 * 
	 * } else if (select_search_no == 0) { // 지역 검색 boardList =
	 * locationboardSQLMapper.selectByProvinceCategoryNo(currPage,
	 * province_category_no); } else { // 지역 + 검색선택번호 + 검색어 ? 모든 조건 검색 boardList =
	 * locationboardSQLMapper.selectBySearch(currPage, province_category_no,
	 * select_search_no, search_word, select_cost, select_type); }
	 * 
	 * // 전체 글 리스트 출력 for (LocationboardVo locationboardVo : boardList) {
	 * 
	 * MemberVo memberVo =
	 * memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
	 * 
	 * Map<String, Object> map = new HashMap<String, Object>();
	 * 
	 * if (member_no == 0) {
	 * 
	 * map.put("memberVo", memberVo); map.put("locationboardVo", locationboardVo);
	 * 
	 * list.add(map);
	 * 
	 * } else {
	 * 
	 * LocationboardMyfavoriteVo myFarVo =
	 * locationboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
	 * locationboardVo.getLocationboard_no());
	 * 
	 * map.put("memberVo", memberVo); map.put("locationboardVo", locationboardVo);
	 * // 주연 map.put("myFarVo", myFarVo);
	 * 
	 * list.add(map); } } return list; }
	 */

	// 글 읽기
	public Map<String, Object> readContent(int locationboard_no, HttpServletResponse response, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

/////////////////////////////////// 조회수 증가방지 구현중/////////////////////////////////////////////

		int countCheck = 0;
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("locationboard_no" + locationboard_no)) {
					countCheck = 0;
					break;
				} else {
					Cookie cookie = new Cookie("locationboard_no" + locationboard_no, String.valueOf(locationboard_no));
					cookie.setMaxAge(60 * 60 * 24);
					cookie.setPath("/");
					response.addCookie(cookie);

					countCheck += 1;
				}
			}
		}
		
		if(countCheck > 0) {
			locationboardSQLMapper.updateReadcount(locationboard_no);
		}
///////////////////////////////////////////////////////////////////////////////////////////////

		LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
		ProvinceCategoryVo provinceCategoryVo = provinceCategorySQLMapper
				.selectByNo(locationboardVo.getProvince_category_no());
		LevelCategoryVo levelCategoryVo = levelCategorySQLMapper.selectByExp(memberVo.getMember_levelexp());
		LocationCategoryVo locationCategoryVo = locationCategorySQLMapper
				.selectByNo(locationboardVo.getLocation_category_no());
		TypeCategoryVo typeCategoryVo = typeCategorySQLMapper.selectByNo(locationboardVo.getType_category_no());
		MoodCategoryVo moodCategoryVo = moodCategorySQLMapper.selectByNo(locationboardVo.getMood_category_no());
		List<LocationboardUploadFileVo> locationboardUploadFileVoList = locationboardUploadFileSQLMapper
				.selectByLocationboardNo(locationboardVo.getLocationboard_no());
		List<LocationboardReplyVo> locationboardReplyVoList = locationboardReplySQLMapper
				.selectByLocationboardNo(locationboard_no);

		map.put("locationboardVo", locationboardVo);
		map.put("memberVo", memberVo);
		map.put("provinceCategoryVo", provinceCategoryVo);
		map.put("levelCategoryVo", levelCategoryVo);
		map.put("locationCategoryVo", locationCategoryVo);
		map.put("typeCategoryVo", typeCategoryVo);
		map.put("moodCategoryVo", moodCategoryVo);
		map.put("locationboardUploadFileVoList", locationboardUploadFileVoList);
		map.put("locationboardReplyVoList", locationboardReplyVoList);

		return map;
	}

	// 글 삭제
	public void deleteContent(int locationboard_no) {

		// 게시글 삭제하고 경험치 감소
		LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
		memberSQLMapper.loseExpByPost(memberVo);

		// 글 삭제 시 파일 삭제
		List<LocationboardUploadFileVo> fileList = locationboardUploadFileSQLMapper
				.selectByLocationboardNo(locationboard_no);

		for (LocationboardUploadFileVo locationboardUploadFileVo : fileList) {

			String path = locationboardUploadFileVo.getLocationboard_file_real_path();

			File file = new File(path);

			if (file.exists() == true) {

				file.delete();

			}
		}

		// 글 삭제 시 댓글, 파일, 링크, 좋아요 삭제
		locationboardReplySQLMapper.deleteByLocationboardNo(locationboard_no);
		locationboardSQLMapper.deleteByLocationboardNo(locationboard_no);
		locationboardUploadFileSQLMapper.deleteByLocationboardNo(locationboard_no);
		try {
			locationboardMyfavoriteSQLMapper.deleteMyfavorite(locationboard_no);
			linkSQLMapper.deleteLocationboard(locationboard_no);
		} catch (Exception e) {
			e.printStackTrace();
		}

		locationboardLikeSQLMapper.deleteByLocationboardNo(locationboard_no);
	}

	// 장소 댓글 쓰기
	public void writeReply(int locationboard_no, LocationboardReplyVo locationboardReplyVo) {

		// 댓글 쓰고 경험치 획득
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardReplyVo.getMember_no());
		memberSQLMapper.getExpByReply(memberVo);

		locationboardReplyVo.setLocationboard_no(locationboard_no);
		int locatinoboard_reply_no = locationboardReplySQLMapper.createLocationboardReplyKey();
		locationboardReplyVo.setLocatinoboard_reply_no(locatinoboard_reply_no);
		locationboardReplySQLMapper.insert(locationboardReplyVo);
	}

	// 장소 댓글 목록
	public List<Map<String, Object>> getRepleList(int locationboard_no, int member_no) {

		List<LocationboardReplyVo> locationboardReplyVoList = locationboardReplySQLMapper
				.selectByLocationboardNo(locationboard_no);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (LocationboardReplyVo locationboardReplyVo : locationboardReplyVoList) {

			MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardReplyVo.getMember_no());

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("memberVo", memberVo);
			map.put("locationboardReplyVo", locationboardReplyVo);

			if (memberVo.getMember_no() == member_no) {
				map.put("sessionMemberNo", member_no);
			}

			list.add(map);

		}

		return list;
	}

	// 장소 댓글 삭제
	public void deleteReply(int locatinoboard_reply_no) {

		// 댓글 삭제 경험치 감소
		LocationboardReplyVo locationboardReplyVo = locationboardReplySQLMapper.selectByReplyNo(locatinoboard_reply_no);
		MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardReplyVo.getMember_no());
		memberSQLMapper.loseExpByReply(memberVo);

		locationboardReplySQLMapper.delete(locatinoboard_reply_no);

	}

	// 장소 댓글 수정
	public void updateReply(LocationboardReplyVo locationboardReplyVo) {

		locationboardReplySQLMapper.update(locationboardReplyVo);

	}

	// 전체 게시글 수
	public int getLocationboardDataCount(int[] province_category_no, int select_search_no, String search_word) {

		if (province_category_no.length == 0 || select_search_no == 0 || search_word == null) {

			return locationboardSQLMapper.selectByLocationboardAllCount();
		} else {

			return locationboardSQLMapper.selectByLocationboardSearchCount(province_category_no, select_search_no,
					search_word);

		}

		/*
		 * if(province_category_no.length != 0 || select_search_no != 0 || search_word
		 * != null) { return
		 * locationboardSQLMapper.selectByLocationboardSearchCount(province_category_no,
		 * select_search_no, search_word); }else if (province_category_no.length == 0 ||
		 * select_search_no == 0 || search_word == null) { return
		 * locationboardSQLMapper.selectByLocationboardAllCount(); }
		 */
	}

	// 좋아요 클릭
	public int likeCheck(int locationboard_no, int member_no) {

		int check_member_no = 0;

		LocationboardLikeVo locationboardLikeVo = locationboardLikeSQLMapper
				.selectByLocationboardNoAndMemberNo(member_no, locationboard_no);
		if (locationboardLikeVo != null) {

			// 경험치 감소 - 좋아요 취소: 글쓴이가 감소
			LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
			memberSQLMapper.loseExpByLike(memberVo);

			check_member_no = locationboardLikeVo.getMember_no();
			locationboardLikeSQLMapper.deleteMemberNo(member_no, locationboard_no);
			return check_member_no; // 좋아요 이미 함 -> 빈 하트로 바뀌어야 함

		} else {

			// 경험치 획득 - 좋아요 : 글쓴이가 획득
			LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
			memberSQLMapper.getExpByLike(memberVo);

			locationboardLikeSQLMapper.insertMemberNoAndLocationNo(member_no, locationboard_no);
			return check_member_no; // 좋아요한 적 없음 -> 빨간 하트로 바뀌어야 함

		}
	}

	// 좋아요 출력
	public List<LocationboardLikeVo> likeAllCheck(int member_no) {

		List<LocationboardLikeVo> list = locationboardLikeSQLMapper.selectAllLike(member_no);

		return list;
	}

	// 게시물 1건에 대한 좋아요 수
	public int likeCount(int locationboard_no) {

		int like_count = locationboardLikeSQLMapper.likeCount(locationboard_no);

		return like_count;
	}

	// 관심 등록, 취소
	public Map<String, Object> favoriteCheck(int member_no, int locationboard_no) {

		Map<String, Object> map = new HashMap<String, Object>();

		LocationboardMyfavoriteVo farVo = locationboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
				locationboard_no);
		if (farVo == null) {
			locationboardMyfavoriteSQLMapper.insertMyfavoriteCheck(member_no, locationboard_no);

			// 경험치 획득 - 관심 등록 : 글쓴이가 획득
			LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
			memberSQLMapper.getExpByFavorite(memberVo);

			map.put("returnCode", "0001");

		} else {

			// 경험치 감소 - 관심 등록 취소 : 글쓴이가 감소
			LocationboardVo locationboardVo = locationboardSQLMapper.selectByLocationboardNo(locationboard_no);
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(locationboardVo.getMember_no());
			memberSQLMapper.loseExpByFavorite(memberVo);

			locationboardMyfavoriteSQLMapper.deleteMyfavoriteCheck(member_no, locationboard_no);

			map.put("returnCode", "0002");
		}

		return map;
	}

	// getProvinceBoardList
	public List<Map<String, Object>> getSpecificBoardList(int member_no, int[] province_checkbox_no, int category_no,
			int[] cost_checkbox_no, int[] type_checkbox_no, int[] time_checkbox_no, int[] mood_checkbox_no,
			int align_no, String searchWord, int currPage) {

		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		StringBuffer sql4 = new StringBuffer();

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<Map<String, Object>> locationboardList = new ArrayList<Map<String, Object>>();

		sql1.append("SELECT * FROM (SELECT ROWNUM rnum, t1.* FROM (SELECT * FROM RM_locationboard");
		sql2.append("SELECT * FROM (SELECT ROWNUM rnum, t1.* FROM (SELECT * FROM RM_locationboard WHERE 1=1");
		sql3.append("SELECT count(*) FROM RM_locationboard");
		sql4.append("SELECT count(*) FROM RM_locationboard WHERE 1=1");

		// 좋아요순으로 정렬된 locationboard_no
		int[] like_locationboard_no = null;

		// 필터 적용 안된 검색
		if (province_checkbox_no[0] == 0 && category_no == 0 && cost_checkbox_no[0] == 0 && type_checkbox_no[0] == 0
				&& time_checkbox_no[0] == 0 && mood_checkbox_no[0] == 0 && align_no == 1) {
			if (!(searchWord.equals(""))) {
				sql1.append(" WHERE locationboard_title LIKE '%" + searchWord + "%'");
				sql3.append(" WHERE locationboard_title LIKE '%" + searchWord + "%'");

			}
			sql1.append(" ORDER BY locationboard_no DESC) t1) t2 WHERE t2.rnum >= (" + currPage
					+ "-1)*12+1 AND t2.rnum <= (" + currPage + "-1)*12+12");
			sql3.append(" ORDER BY locationboard_no DESC");
			System.out.println(" 전체쿼리" + sql1.toString());
			map.put("SELECT_LOCATIONBOARD", sql1.toString());
			map2.put("COUNT_LOCATIONBOARD", sql3.toString());
		} else { // 필터 적용된 검색

			// 검색어
			if (!(searchWord.equals(""))) {
				sql2.append("AND locationboard_title LIKE '%" + searchWord + "%'");
				sql4.append("AND locationboard_title LIKE '%" + searchWord + "%'");
			}

			// 지역
			if (province_checkbox_no[0] != 0) {

				sql2.append(" AND province_category_no IN (");
				sql4.append(" AND province_category_no IN (");
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

			// 여행지 or 맛집
			if (category_no == 1) {
				sql2.append(" AND location_category_no = 1");
				sql4.append(" AND location_category_no = 1");

			} else if (category_no == 2) {
				sql2.append(" AND location_category_no = 2");
				sql4.append(" AND location_category_no = 2");
			}

			// 비용
			if (cost_checkbox_no[0] != 0) {
				sql2.append(" AND cost_category_no IN (");
				sql4.append(" AND cost_category_no IN (");
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

			// 유형
			if (type_checkbox_no[0] != 0) {
				sql2.append(" AND type_category_no IN (");
				sql4.append(" AND type_category_no IN (");
				for (int i = 0; i < type_checkbox_no.length; i++) {
					if (i == type_checkbox_no.length - 1) {
						sql2.append(type_checkbox_no[i] + ")");
						sql4.append(type_checkbox_no[i] + ")");
					} else {
						sql2.append(type_checkbox_no[i] + ",");
						sql4.append(type_checkbox_no[i] + ",");
					}
				}
			}

			// 소요시간
			if (time_checkbox_no[0] != 0) {
				sql2.append(" AND time_category_no IN (");
				sql4.append(" AND time_category_no IN (");
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

			// 스타일
			if (mood_checkbox_no[0] != 0) {
				sql2.append(" AND mood_category_no IN (");
				sql4.append(" AND mood_category_no IN (");
				for (int i = 0; i < mood_checkbox_no.length; i++) {
					if (i == mood_checkbox_no.length - 1) {
						sql2.append(mood_checkbox_no[i] + ")");
						sql4.append(mood_checkbox_no[i] + ")");
					} else {
						sql2.append(mood_checkbox_no[i] + ",");
						sql4.append(mood_checkbox_no[i] + ",");
					}
				}
			}

			// 정렬
			if (align_no == 1) {
				sql2.append(" ORDER BY locationboard_no DESC) t1) t2 WHERE t2.rnum >= (" + currPage
						+ "-1)*12+1 AND t2.rnum <= (" + currPage + "-1)*12+12");
				sql4.append(" ORDER BY locationboard_no DESC");
			}
			if (align_no == 2) {
				sql2.append(" ORDER BY locationboard_readcount DESC) t1) t2 WHERE t2.rnum >= (" + currPage
						+ "-1)*12+1 AND t2.rnum <= (" + currPage + "-1)*12+12");
				sql4.append(" ORDER BY locationboard_readcount DESC");
			} else if (align_no == 3) {

				like_locationboard_no = locationboardSQLMapper.likeAlign();

				// sql2.append(") t1) t2 WHERE t2.rnum >= (" + currPage + "-1)*12+1 AND t2.rnum
				// <= (" + currPage+ "-1)*12+12");
				sql2.append(") t1) t2");
			}

			System.out.println("최종 쿼리" + sql2.toString());
			System.out.println(sql4.toString());
			map.put("SELECT_LOCATIONBOARD", sql2.toString());
			map2.put("COUNT_LOCATIONBOARD", sql4.toString());
		}

		// 필터 적용 된 검색

		int count = 0;

		try {
			count = locationboardSQLMapper.countAllWithCondition(map2);
		} catch (Exception e) {
			count = 0;
			e.printStackTrace();
		}
		List<LocationboardVo> list = locationboardSQLMapper.selectAllWithCondition(map);

		// 좋아요순
		if (align_no == 3 && like_locationboard_no != null) {

			// 1) 좋아요 된 글 먼저 넣기
			for (int like_board_no : like_locationboard_no) {
				// System.out.println("like_board_no"+like_board_no);
				for (LocationboardVo locationboardVo : list) {

					LocationboardUploadFileVo locationboardUploadFileVo = locationboardUploadFileSQLMapper
							.selectOneByLocationboardNo(locationboardVo.getLocationboard_no());

					Map<String, Object> boardMap = new HashMap<String, Object>();

					if (locationboardVo.getLocationboard_no() == like_board_no) {
						// System.out.println("같음board_no" + locationboardVo.getLocationboard_no());
						if (member_no == 0) {

							boardMap.put("locationboardVo", locationboardVo);
							boardMap.put("count", count);
							boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo);

						} else {

							LocationboardMyfavoriteVo myFarVo = locationboardMyfavoriteSQLMapper
									.selectMyfavoriteAllCheck(member_no, locationboardVo.getLocationboard_no());
							boardMap.put("myFarVo", myFarVo);
							boardMap.put("locationboardVo", locationboardVo);
							boardMap.put("count", count);
							boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo);
						}

						locationboardList.add(boardMap);
					}
				}
			}

			// 2) 좋아요 없는 글들
			for (LocationboardVo locationboardVo : list) {

				LocationboardUploadFileVo locationboardUploadFileVo = locationboardUploadFileSQLMapper
						.selectOneByLocationboardNo(locationboardVo.getLocationboard_no());

				int likeCount = 0;
				for (int like_board_no : like_locationboard_no) {

					if (locationboardVo.getLocationboard_no() == like_board_no) {
						likeCount++;
					}

				}
				if (likeCount == 0) {
					Map<String, Object> boardMap = new HashMap<String, Object>();

					// System.out.println("다름board_no" + locationboardVo.getLocationboard_no());
					if (member_no == 0) {

						boardMap.put("locationboardVo", locationboardVo);
						boardMap.put("count", count);
						boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo);

					} else {

						LocationboardMyfavoriteVo myFarVo = locationboardMyfavoriteSQLMapper
								.selectMyfavoriteAllCheck(member_no, locationboardVo.getLocationboard_no());
						boardMap.put("myFarVo", myFarVo);
						boardMap.put("locationboardVo", locationboardVo);
						boardMap.put("count", count);
						boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo);
					}

					locationboardList.add(boardMap);
				}

			}

		} else {
			for (LocationboardVo locationboardVo : list) {

				LocationboardUploadFileVo locationboardUploadFileVo = locationboardUploadFileSQLMapper
						.selectOneByLocationboardNo(locationboardVo.getLocationboard_no());

				Map<String, Object> boardMap = new HashMap<String, Object>();

				if (member_no == 0) {

					boardMap.put("locationboardVo", locationboardVo);
					boardMap.put("count", count);
					if (locationboardUploadFileVo != null) {
						boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo);
					}
//					else {
//						LocationboardUploadFileVo locationboardUploadFileVo2 = locationboardUploadFileSQLMapper
//								.selectOneByLocationboardNo(7);
//						boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo2);
//					}
					// System.out.println("카운트else if부분: "+count);
				} else {

					LocationboardMyfavoriteVo myFarVo = locationboardMyfavoriteSQLMapper
							.selectMyfavoriteAllCheck(member_no, locationboardVo.getLocationboard_no());
					boardMap.put("myFarVo", myFarVo);
					boardMap.put("locationboardVo", locationboardVo);
					boardMap.put("count", count);
					if (locationboardUploadFileVo != null) {
						boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo);
					}
//					else {
//						LocationboardUploadFileVo locationboardUploadFileVo2 = locationboardUploadFileSQLMapper
//								.selectOneByLocationboardNo(7);
//						boardMap.put("locationboardUploadFileVo", locationboardUploadFileVo2);
//					}
				}
				locationboardList.add(boardMap);
			}
		}
		return locationboardList;
	}

	// 관심조회
	public LocationboardMyfavoriteVo getfavoirte(int member_no, int locationboard_no) {

		LocationboardMyfavoriteVo farVo = locationboardMyfavoriteSQLMapper.selectMyfavoriteAllCheck(member_no,
				locationboard_no);

		return farVo;
	}
	
	// 게시글 번호로 글쓴이 뽑아오기
	public int getMemberNo(int locationboard_no) {
		
		int member_no = locationboardSQLMapper.selectMemberNoByLocationboardNo(locationboard_no);
		
		return member_no;
		
	}
	
	// 게시글 댓글 번호로 댓글 작성자 뽑아오기
	public int getReplyMemberNo(int locatinoboard_reply_no) {
		
		int member_no = locationboardSQLMapper.selectMemberNoByLocationboardReplyNo(locatinoboard_reply_no);
		
		return member_no;
		
	}
	
//	// 좋아요 내가 하는건지 확인
//	public int[] getLikeMemberNo(int locationboard_no, int member_no) {
//		
//		int member_no = locationboardSQLMapper.selectLikeMemberNoByLocationboardNo(locationboard_no, member_no);
//		
//		return member_no;
//		
//	}
}
