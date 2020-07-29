package com.explorer.routemap.member.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.explorer.routemap.admin.vo.AdminVo;
import com.explorer.routemap.member.vo.*;

public interface MemberSQLMapper {

	// src/main/resources > com.explorer.routemap.member.mapper >
	// MemberSQLMapper.xml
	// 회원 가입 키 생성
	public int createMemberKey();

	// 회원 가입
	public int insert(MemberVo memberVo);

	// 회원 로그인(아이디, 패스워드 확인)
	public MemberVo selectByIdAndPw(@Param("member_id") String member_id, @Param("member_pw") String member_pw);

	// 관리자 로그인(아이디, 패스워드 확인)
	public AdminVo selectAdminByIdAndPw(@Param("admin_id") String member_id, @Param("admin_pw") String member_pw);

	// 게시글 작성자 확인
	public MemberVo selectByMemberNo(int member_no);

	// 경험치 획득 - 게시글 쓰기
	public void getExpByPost(MemberVo memberVo);

	// 경험치 획득 - 댓글 쓰기
	public void getExpByReply(MemberVo memberVo);

	// 경험치 획득 - 좋아요 : 글쓴이가 획득
	public void getExpByLike(MemberVo memberVo);

	// 경험치 획득 - 관심 등록 : 글쓴이가 획득
	public void getExpByFavorite(MemberVo memberVo);

	// 경험치 감소 - 게시글 삭제
	public void loseExpByPost(MemberVo memberVo);

	// 경험치 감소 - 댓글 삭제
	public void loseExpByReply(MemberVo memberVo);

	// 경험치 감소 - 좋아요 취소 : 글쓴이가 감소
	public void loseExpByLike(MemberVo memberVo);

	// 경험치 감소 - 관심 등록 취소 : 글쓴이가 감소
	public void loseExpByFavorite(MemberVo memberVo);

	// 로그인
	public MemberVo login(MemberVo dto);

	// 자동로그인 체크한 경우에 사용자 테이블에 세션과 유효시간을 저장하기 위한 메서드
	public void keepLogin(@Param("member_id") String member_id, @Param("sessionId") String sessionId,
			@Param("next") Date next);

	// 이전에 로그인한 적이 있는지, 즉 유효시간이 넘지 않은 세션을 가지고 있는지 체크한다.
	public MemberVo checkUserWithSessionKey(String sessionId);

	// 아이디 중복확인
	public String selectMemberId(String member_id);

	// 닉네임 중복확인
	public String selectMemberNick(String member_nickname);

	// 비밀번호 변경
	public void updatePw(@Param("member_id") String member_id, @Param("member_pw") String member_pw);

	// 닉네임 변경
	public void updateNickname(@Param("member_id") String member_id, @Param("member_nickname") String member_nickname);

	// 생년월일 변경
	public void updateBirthday(@Param("member_id") String member_id, @Param("member_birth") String member_birth);

	// 휴대전화 변경
	public void updatePhone(@Param("member_id") String member_id, @Param("member_phone") String member_phone);

	// 회원 탈퇴
	public void updateMember(MemberVo memberVo);

	// 회원 번호로 회원 정보 가져오기
	public MemberVo selectMemberByMemberNo(int member_no);

	// 쪽지 받는 사람 아이디의 회원번호를 받기 위한 메서드
	public int selectByMemberId(String member_id);

	// 아이디만 맞고 비밀번호 틀릴 경우
	public MemberVo getMemberId(@Param("member_id") String member_id, @Param("member_pw") String member_pw);

	// 로그인 실패 회수 1회 증가
	public void updateMemberLoginTrial(String member_id);

	// 로그인 실패 회수 초기화(0으로)
	public void resetMemberLoginTrial(String member_id);

	// 관리자 아이디 중복확인
	public String selectAdminId(String admin_id);

	// 관리자 닉네임 중복확인
	public String selectAdminNick(String admin_nickname);

	// 관리자 회원 가입 키 생성
	public int createAdminKey();

	// 관리자 회원 가입
	public void insertAdmin(AdminVo adminVo);

	// 관리자 아이디만 맞고 비밀번호 틀릴 경우
	public AdminVo getAdminId(@Param("admin_id") String admin_id, @Param("admin_pw") String admin_pw);

	// 관리자 로그인 실패 횟수 1 증가
	public void updateAdminLoginTrial(String admin_id);

	// 관리자 로그인 실패 횟수 초기화
	public void resetAdminLoginTrial(String admin_id);
}