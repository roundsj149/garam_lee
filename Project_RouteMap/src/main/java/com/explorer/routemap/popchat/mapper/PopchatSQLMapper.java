package com.explorer.routemap.popchat.mapper;

import java.util.List;

import com.explorer.routemap.popchat.vo.PopchatVo;

public interface PopchatSQLMapper {
	
	// 채팅 번호 생성
	public int createChatNumber();
	
	// 채팅창 - 채팅 쓰기
	public void insert(PopchatVo popchatVo);

	// 채팅창 - 채팅 목록
	public List<PopchatVo> selectAllChatList();
	
}
