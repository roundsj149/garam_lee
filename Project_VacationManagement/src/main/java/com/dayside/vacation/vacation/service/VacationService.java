package com.dayside.vacation.vacation.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dayside.vacation.common.util.ApplyNoGeneration;
import com.dayside.vacation.common.util.DateUtil;
import com.dayside.vacation.main.mapper.MainMapper;
import com.dayside.vacation.member.mapper.MemberMapper;
import com.dayside.vacation.vacation.mapper.VacationMapper;
import com.dayside.vacation.vacation.model.Vacation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VacationService {

	@Autowired
	VacationMapper vacationMapper;
	@Autowired
	MainMapper mainMapper;
	@Autowired
	MemberMapper memberMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(VacationService.class);
	
	// 휴가 신청 목록
	public List<Map<String, Object>> getVacationInfo(String employeeNo, String searchWord, String selectYear, int currPage) {

		List<Map<String, Object>> list = vacationMapper.selectAllVacationInfo(employeeNo, searchWord, selectYear, currPage);
		
		return list;
	}

	// 휴가 신청
	public void requestVacation(Vacation vacation) {

		// 휴가 신청 번호 생성
		int seq = vacationMapper.getVacationApplySeq();
		// 현재 시간 날짜 포맷 설정
		String currentTime = DateUtil.dateToStringNoHypen(new Date(System.currentTimeMillis()));
		// 시퀀스가 001 002 003...의 3자리 숫자가 되도록 왼쪽에 0을 붙여주고 현재시간-sequence로 applyNo 만듦
		// ex. 20200930150635-002
		String applyNo = currentTime + "-" + StringUtils.leftPad(String.valueOf(seq), 3, "0");
		ApplyNoGeneration applyNoGeneration = new ApplyNoGeneration();
		// ex. 20200930150635-0025
		String vacationApplyNo = applyNoGeneration.genApplyNo(applyNo);
		vacation.setVacationApplyNo(vacationApplyNo);

		vacationMapper.insertVacationInfo(vacation);

	}

	// 휴가 취소
	public void cancelVacation(String vacationApplyNo) {
		vacationMapper.deleteVacationInfo(vacationApplyNo);
	}

	// 휴가 수정 선택
	public Map<String, Object> getOneVacationInfo(String vacationApplyNo) {
		return vacationMapper.selectOneVacationInfo(vacationApplyNo);
	}

	// 휴가 상태
	public Map<String, Object> getVacationStatus(String applyNo) {
		return vacationMapper.selectVacationStatus(applyNo);
	}

	// 휴가 수정
	public void updateVacation(Vacation vacation) {
		vacationMapper.updateVacationInfo(vacation);
	}
	
	// 전체 휴가 수 
	public int getAllVacationCount(String employeeNo, String searchWord, String selectYear) {
		return vacationMapper.countAllVacation(employeeNo, searchWord, selectYear);
	}
	
	// 승인된 휴가 일수
	public float getApprovalVacationDays(String employeeNo, String searchWord, String selectYear) {
		
		List<Map<String, Object>> list = vacationMapper.selectApprovalVacationDays(employeeNo, searchWord, selectYear);
		float cnt = 0;
		
		for(Map<String, Object> map : list) {
			String sYear = DateUtil.dateToStringYear((Date)map.get("startDt"));
			String eYear = DateUtil.dateToStringYear((Date)map.get("endDt"));
			if(sYear.equals(eYear) || sYear.equals(selectYear)) {
				cnt += ((BigDecimal)map.get("beforeCountDays")).floatValue();
			} else if(eYear.equals(selectYear)) {
				cnt += ((BigDecimal)map.get("afterCountDays")).floatValue();
			}
		}
		return cnt;
	}
}