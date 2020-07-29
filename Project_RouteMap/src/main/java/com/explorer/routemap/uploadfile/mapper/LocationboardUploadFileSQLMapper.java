package com.explorer.routemap.uploadfile.mapper;

import java.util.*;

import com.explorer.routemap.uploadfile.vo.*;

public interface LocationboardUploadFileSQLMapper {
	
	// 장소 파일 업로드
	   public void insertFiles(LocationboardUploadFileVo locationboardUploadFileVo);
	   
	// 장소 파일 읽기
	public List<LocationboardUploadFileVo> selectByLocationboardNo(int locationboard_no);
	
	// 장소 파일 1개 읽기
	public LocationboardUploadFileVo selectOneByLocationboardNo(int locationboard_no);
	
	// 장소 게시물 삭제 시 파일 삭제
	public void deleteByLocationboardNo(int locationboard_no);	
	
	// 장소 파일 개별 삭제
	public void deleteByFileNo(int locationboard_file_no);
	
	// 장소 파일 개별 읽기
	public LocationboardUploadFileVo selectByFileNo(int selectByFileNo);
}
