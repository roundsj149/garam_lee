package com.explorer.routemap.board.mapper;

import com.explorer.routemap.board.vo.ProvinceCategoryVo;

public interface ProvinceCategorySQLMapper {

	// 지역 (시 / 도 구분)
		public ProvinceCategoryVo selectByNo(int province_category_no);

	// 지역 이름으로 지역번호 가져오기
	public int selectByLocationCategoryName(String location_category_name);
}
