package com.dayside.vacation.overtime.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dayside.vacation.common.util.ApplyNoGeneration;
import com.dayside.vacation.common.util.DateUtil;
import com.dayside.vacation.overtime.mapper.OvertimeMapper;
import com.dayside.vacation.overtime.model.Overtime;

@Service
public class OvertimeService {

	private static final Logger logger = LoggerFactory.getLogger(OvertimeService.class);

	@Autowired
	OvertimeMapper overtimeMapper;

	// 초과근무 신청 목록
	public List<Map<String, Object>> getOvertimeInfo(String employeeNo, String searchWord, String selectYear, int currPage) {
		List<Map<String, Object>> list = overtimeMapper.selectAllOvertimeInfo(employeeNo, searchWord, selectYear, currPage);
		
		return list;
	}

	// 초과근무 신청
	public void requestOvertime(Overtime overtime) {
		// 초과근무 신청 번호 생성
		int seq = overtimeMapper.getOvertimeApplySeq();
		// 현재 시간 날짜 포맷 설정
		String currentTime = DateUtil.dateToStringNoHypen(new Date(System.currentTimeMillis()));
		// 시퀀스가 001 002 003...의 3자리 숫자가 되도록 왼쪽에 0을 붙여주고 현재시간-sequence로 applyNo 만듦
		// ex. 20200930150635-002
		String applyNo = currentTime + "-" + StringUtils.leftPad(String.valueOf(seq), 3, "0");
		ApplyNoGeneration applyNoGeneration = new ApplyNoGeneration();
		// ex. 20200930150635-0025
		String overtimeApplyNo = applyNoGeneration.genApplyNo(applyNo);
		overtime.setOvertimeApplyNo(overtimeApplyNo);

		overtimeMapper.insertOvertimeInfo(overtime);
	}
	
	// 전체 초과근무 수 
	public int getAllOvertimeCount(String employeeNo, String searchWord, String selectYear) {
		return overtimeMapper.countAllOvertime(employeeNo, searchWord, selectYear);
	}
}
