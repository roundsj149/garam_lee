package com.explorer.routemap.uploadfile.mapper;

import java.util.List;

import com.explorer.routemap.uploadfile.vo.PopchatUploadFileVo;

public interface PopchatUploadFileSQLMapper {
	
	// 채팅창 파일 업로드
	public void insertFiles(PopchatUploadFileVo popchatUploadFileVo);
	
	// 채팅창 파일 불러오기 by 채팅창 번호
	public List<PopchatUploadFileVo> selectByPopchatNo(int popchat_no);
	
}
