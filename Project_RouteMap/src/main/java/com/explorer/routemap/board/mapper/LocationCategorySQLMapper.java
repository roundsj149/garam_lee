package com.explorer.routemap.board.mapper;

import com.explorer.routemap.board.vo.LocationCategoryVo;

public interface LocationCategorySQLMapper {

	// 장소 카테고리 
	public LocationCategoryVo selectByNo(int location_category_no);
	
}
