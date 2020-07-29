package com.explorer.routemap.uploadfile.mapper;

import java.util.List;

import com.explorer.routemap.uploadfile.vo.FreeboardUploadFileVo;

public interface FreeboardUploadFileSQLMapper {

	//파일올리기
	public void upFiles(FreeboardUploadFileVo freeboardUploadFileVo);
	
	//자유게시판 글보기
	public List<FreeboardUploadFileVo> selectByFreeboardNo(int FreeboardNo);
	
}
