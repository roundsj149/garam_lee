    package com.explorer.routemap.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.explorer.routemap.board.mapper.FreeboardCommentSQLMapper;
import com.explorer.routemap.board.mapper.FreeboardSQLMapper;
import com.explorer.routemap.board.vo.FreeboardCommentVo;
import com.explorer.routemap.board.vo.FreeboardVo;
import com.explorer.routemap.member.mapper.MemberSQLMapper;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.uploadfile.mapper.FreeboardUploadFileSQLMapper;
import com.explorer.routemap.uploadfile.vo.FreeboardUploadFileVo;

@Service
public class FreeboardServiceImpl {

	@Autowired
	FreeboardSQLMapper freeboardSQLMapper;
	@Autowired
	MemberSQLMapper memberSQLMapper;
	@Autowired
	FreeboardUploadFileSQLMapper freeboardUploadFileSQLMapper;
	@Autowired
	FreeboardCommentSQLMapper freeboardCommentSQLMapper;

//글쓰기
	public void writing(FreeboardVo freeboardVo, List<FreeboardUploadFileVo> fbFileVoList) {

		int freeboard_no = freeboardSQLMapper.createFreeboardKey();

		freeboardVo.setFreeboard_no(freeboard_no);

		freeboardSQLMapper.insert(freeboardVo);
		
		for (FreeboardUploadFileVo fbFileVo : fbFileVoList) {

			fbFileVo.setFreeboard_no(freeboard_no);
			freeboardUploadFileSQLMapper.upFiles(fbFileVo);
		}
	}

	// 파일 업로드하기
	public void upfiles(MultipartFile file, int freeboard_no) {

		String rootName = file.getOriginalFilename();
		String shelter = "C:/upload/freeboard_file_upload/";

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String nowShelter = sdf.format(now);

		String saveShelterName = shelter + nowShelter;

		File saveShelter = new File(saveShelterName);

		if (!saveShelter.exists()) {
			saveShelter.mkdirs();
		}

		Calendar calendar = Calendar.getInstance();
		String fileName = "";
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += rootName.substring(rootName.lastIndexOf("."));

		String freeboard_file_link_path = nowShelter + "/" + fileName;
		String freeboard_file_real_path = saveShelterName + "/" + fileName;

		FreeboardUploadFileVo freeboardUploadFileVo = new FreeboardUploadFileVo();

		freeboardUploadFileVo.setFreeboard_no(freeboard_no);
		freeboardUploadFileVo.setFile_link_path(freeboard_file_link_path);
		freeboardUploadFileVo.setFile_real_path(freeboard_file_real_path);

		freeboardUploadFileSQLMapper.upFiles(freeboardUploadFileVo);

		try {
			file.transferTo(new File(freeboard_file_real_path));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 제목검색, 전체글 표시

	public List<Map<String, Object>> getFreeboardList( String word, int currPage ) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<FreeboardVo> freeboardList = null;

		if (word == null) {
			freeboardList = freeboardSQLMapper.selectAllBoardList(currPage);

		} else {
			freeboardList = freeboardSQLMapper.selectByTitle(word, currPage);
		}
		for (FreeboardVo freeboardVo : freeboardList) {
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(freeboardVo.getMember_no());

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("memberVo", memberVo);
			map.put("freeboardVo", freeboardVo); 
			list.add(map);
		}

		return list;
	}

	
	// 글읽기
	public Map<String, Object> seeContents(int freeboard_no) {

		Map<String, Object> map = new HashMap<String, Object>();

		freeboardSQLMapper.freeboardReadcount(freeboard_no);

		FreeboardVo freeboardVo = freeboardSQLMapper.selectByFreeboardNo(freeboard_no);

		MemberVo memberVo = memberSQLMapper.selectByMemberNo(freeboardVo.getMember_no());

		List<FreeboardUploadFileVo> fbFileVoList = freeboardUploadFileSQLMapper.selectByFreeboardNo(freeboard_no);
		//List<FreeboardCommentVo> fbCommentVoList =
		//freeboardCommentSQLMapper.selectByFreeboardNo(freeboard_no);

		map.put("memberVo", memberVo);
		map.put("freeboardVo", freeboardVo);
		map.put("fileVoList", fbFileVoList);
		//map.put("freeboardCommentVoList", fbCommentVoList);

		return map;

	}

	// 글지우기
	public void eraseContents(int freeboard_no) {
		freeboardSQLMapper.deleteContent(freeboard_no);
	}

	// 글 수정
	public void updateContents(FreeboardVo freeboardVo) {
		freeboardSQLMapper.update(freeboardVo);

	}

	// 답변달기
	public void writeComment(int freeboard_no, FreeboardCommentVo freeboardCommentVo) {
		freeboardCommentVo.setFreeboard_no(freeboard_no);
		int freeboard_reply_no = freeboardCommentSQLMapper.createFreeboardCommentKey();
		freeboardCommentVo.setFreeboard_reply(freeboard_reply_no);
		freeboardCommentSQLMapper.reple(freeboardCommentVo);
	}

	// 답변 보기&달기
	public List<Map<String, Object>> getFreeboardCommentList(int freeboard_no) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<FreeboardCommentVo> freeboardcommentVoList = freeboardCommentSQLMapper.selectByFreeboardNo(freeboard_no);

		for (FreeboardCommentVo freeboardCommentVo : freeboardcommentVoList) {

			MemberVo memberVo = memberSQLMapper.selectByMemberNo(freeboardCommentVo.getMember_no());

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("memberVo", memberVo);
			map.put("freeboardCommentVo", freeboardCommentVo);

			list.add(map);

		}

		return list;

	}

	public int getFreeboardDataCount(String word) {
		if(word == null) {
			return freeboardSQLMapper.selectAllCount();
		}else {
			return freeboardSQLMapper.selectByTitleCount(word);
		}
		
	}

}

    
