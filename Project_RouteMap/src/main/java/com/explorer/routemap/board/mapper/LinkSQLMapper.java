package com.explorer.routemap.board.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.board.vo.*;

public interface LinkSQLMapper {

	// 루트공유 글읽기 - 글 안의 장소글 가져오기
	public List<LinkVo> selectLinkAndLocationByRouteboardNo(int routeboard_no);

	// 루트공유 글삭제 - 링크 같이 삭제
	public void deleteRouteboard(int routeboard_no);

	// 장소 글삭제 - 링크 같이 삭제
	public void deleteLocationboard(int locationboard_no);

	// 링크 번호 생성
	public int createLinkKey();

	// 링크 연결 - 장소와 루트를 링크
	public void insert(@Param("linkVo") LinkVo linkVo, @Param("locationboardVo") LocationboardVo locationboardVo,
			@Param("routeboardVo") RouteboardVo routeboardVo);

	// 루트 글 업데이트를 위한 링크 리셋
	public void deleteLinkForUpdate(int routeboard_no);
}
