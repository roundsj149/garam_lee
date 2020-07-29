package com.explorer.routemap.popchat.service;

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

import com.explorer.routemap.member.mapper.LevelCategorySQLMapper;
import com.explorer.routemap.member.mapper.MemberSQLMapper;
import com.explorer.routemap.member.vo.LevelCategoryVo;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.popchat.mapper.PopchatSQLMapper;
import com.explorer.routemap.popchat.vo.PopchatVo;
import com.explorer.routemap.uploadfile.mapper.PopchatUploadFileSQLMapper;
import com.explorer.routemap.uploadfile.vo.LocationboardUploadFileVo;
import com.explorer.routemap.uploadfile.vo.PopchatUploadFileVo;

@Service
public class PopchatServiceImpl {

	@Autowired
	PopchatSQLMapper popchatSQLMapper;
	@Autowired
	MemberSQLMapper memberSQLMapper;
	@Autowired
	LevelCategorySQLMapper levelCategorySQLMapper;
	@Autowired
	PopchatUploadFileSQLMapper popchatUploadFileSQLMapper;

	// 채팅창 - 채팅 쓰기
	public void writeContet(PopchatVo popchatVo) {

		int popchat_no = popchatSQLMapper.createChatNumber();
		popchatVo.setPopchat_no(popchat_no);
		popchatSQLMapper.insert(popchatVo);

	}

	// 파일올리기
	public void uploadfiles(MultipartFile file, int popchat_no) {

		String popchat_file_originalname = file.getOriginalFilename();
		String folder = "/var/storage/routemap/upload/popchat_file_upload/";
		

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
		fileRandomName += popchat_file_originalname.substring(popchat_file_originalname.lastIndexOf("."));

		String popchat_file_link_path = todayFolder + "/" + fileRandomName;
		String popchat_file_real_path = saveFolderName + "/" + fileRandomName; // 파일 실제 저장 위치

		PopchatUploadFileVo popchatUploadFileVo = new PopchatUploadFileVo();

		popchatUploadFileVo.setPopchat_no(popchat_no);
		popchatUploadFileVo.setPopchat_file_originalname(popchat_file_originalname);
		popchatUploadFileVo.setPopchat_file_link_path(popchat_file_link_path);
		popchatUploadFileVo.setPopchat_file_real_path(popchat_file_real_path);

		popchatUploadFileSQLMapper.insertFiles(popchatUploadFileVo);

		try {

			file.transferTo(new File(popchat_file_real_path));

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	// 채팅창 - 채팅 목록
	public List<Map<String, Object>> getChatList() {

		// 채팅 목록 가져오기
		List<PopchatVo> popchatVoList = popchatSQLMapper.selectAllChatList();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (PopchatVo popchatVo : popchatVoList) {

			Map<String, Object> map = new HashMap<String, Object>();
			MemberVo memberVo = memberSQLMapper.selectByMemberNo(popchatVo.getMember_no());
			LevelCategoryVo levelCategoryVo = levelCategorySQLMapper.selectByExp(memberVo.getMember_levelexp());
			List<PopchatUploadFileVo> popchatUploadFileVoList = popchatUploadFileSQLMapper.selectByPopchatNo(popchatVo.getPopchat_no());

			map.put("memberVo", memberVo);
			map.put("levelCategoryVo", levelCategoryVo);
			map.put("popchatVo", popchatVo);
			map.put("popchatUploadFileVoList", popchatUploadFileVoList);

			list.add(map);

		}

		return list;

	}

}
