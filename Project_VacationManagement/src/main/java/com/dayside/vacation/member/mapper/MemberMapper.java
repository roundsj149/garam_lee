package com.dayside.vacation.member.mapper;

import com.dayside.vacation.member.model.Member;

public interface MemberMapper {
	
	// 전체 회원 조회
	public Member selectAllMember();
	// 회원가입
	public void insertMemberInfo(Member member);
	// 아이디 확인 및 salt값 가져와서 비밀번호 일치 여부 확인
	public Member selectId(String emailId);
	//public Member selectOneMember(@Param("emailId")String emailId, @Param("pw")String pw);
	// 사번 중복 확인
	public String selectEmployeeNo(String employeeNo);
	// 아이디 중복 확인
	public String selectEmailId(String emailId);
	// 소속팀명 조회
	public String[] selectAllTeam();
}
