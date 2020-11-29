package com.dayside.vacation.vacation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dayside.vacation.vacation.model.Vacation;

public interface VacationMapper {
	
	// 휴가 신청 목록
	public List<Map<String, Object>> selectAllVacationInfo(@Param("value")String value, 
			@Param("searchWord")String searchWord, @Param("selectYear")String selectYear, @Param("currPage")int currPage);
	// 휴가 신청 번호를 위한 시퀀스
	public int getVacationApplySeq();
	// 휴가 신청
	public void insertVacationInfo(Vacation vacation);
	// 휴가 취소
	public void deleteVacationInfo(String vacationApplyNo);
	// 휴가 수정 선택
	public Map<String, Object> selectOneVacationInfo(String vacationApplyNo);
	// 휴가 상태
	public Map<String, Object> selectVacationStatus(String vacationApplyNo);
	// 휴가 수정
	public void updateVacationInfo(Vacation vacation);
	// 전체 휴가 수
	public int countAllVacation(@Param("value")String value, @Param("searchWord")String searchWord, @Param("selectYear")String selectYear);
	// 승인된 휴가
	public List<Map<String, Object>> selectApprovalVacationDays(@Param("value")String value, @Param("searchWord")String searchWord, @Param("selectYear")String selectYear);
}
