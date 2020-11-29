package com.dayside.vacation.overtime.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.dayside.vacation.overtime.model.Overtime;

public interface OvertimeMapper {

	// 초과근무 신청 목록
	public List<Map<String, Object>> selectAllOvertimeInfo(@Param("value") String value, 
			@Param("searchWord") String searchWord, @Param("selectYear") String selectYear, @Param("currPage") int currPage);
	// 초과근무 신청 번호를 위한 시퀀스
	public int getOvertimeApplySeq();
	// 초과근무 신청
	public void insertOvertimeInfo(Overtime overtime);
	// 전체 초과근무 수
	public int countAllOvertime(@Param("value")String value, @Param("searchWord")String searchWord, @Param("selectYear")String selectYear);
}
