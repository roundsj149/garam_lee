    package com.explorer.routemap.board.mapper;

import java.util.List;

import com.explorer.routemap.board.vo.FreeboardCommentVo;

public interface FreeboardCommentSQLMapper {

	public int createFreeboardCommentKey();	
	
	public void reple(FreeboardCommentVo freeboardCommentVo);

	
	
	public List<FreeboardCommentVo> selectByFreeboardNo(int freebord_no);
}

    
