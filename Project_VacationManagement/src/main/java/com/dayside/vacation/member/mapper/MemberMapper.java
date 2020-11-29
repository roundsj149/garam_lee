package com.dayside.vacation.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dayside.vacation.member.model.Member;

public interface MemberMapper {
	
	// 전체 회원 조회
	public List<Member> selectAllMember();
	// 회원가입
	public void insertMemberInfo(Member member);
	// 아이디 확인 및 salt값 가져와서 비밀번호 일치 여부 확인
	public Map<String, Object> selectId(Member member);
	// 사번 중복 확인
	public String selectEmployeeNo(String employeeNo);
	// 아이디 중복 확인
	public String selectEmailId(String emailId);
	// 소속팀명 조회
	public List<String> selectAllTeam();
	// 사번,이름 조회
	public Map<String, Object> selectNoName(String employeeNo);
	// 관리자 여부 확인
	public String selectAdminYN(String employeeNo);
	// 이름으로 사번 조회
	public String[] selectEmployeeNoByFullname(String searchWord);
	// 비밀번호, salt 가져오기
	public Map<String, Object> selectPwSalt(String employeeNo);
	// 비밀번호 변경
	public void updatePw(@Param("pw")String pw, @Param("employeeNo")String employeeNo);
	// 신청번호 가져오기
	public int getVacationApplyNo();
}
