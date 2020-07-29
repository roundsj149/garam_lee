package com.explorer.routemap.member.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explorer.routemap.admin.vo.AdminVo;
import com.explorer.routemap.member.mapper.MemberAuthSQLMapper;
import com.explorer.routemap.member.mapper.MemberSQLMapper;
import com.explorer.routemap.member.vo.MemberAuthVo;
import com.explorer.routemap.member.vo.MemberVo;
import com.explorer.routemap.util.RMMessageDigest;

@Service
public class MemberServiceImpl {
	@Autowired
	MemberSQLMapper memberSQLMapper;
	@Autowired
	MemberAuthSQLMapper memberAuthSQLMapper;

	// 회원가입
	public void joinMember(MemberVo memberVo, MemberAuthVo memberAuthVo) {

		int createMemberKey = memberSQLMapper.createMemberKey();
		memberVo.setMember_no(createMemberKey);
		String hashCode = RMMessageDigest.digest(memberVo.getMember_pw());
		memberVo.setMember_pw(hashCode);
		memberSQLMapper.insert(memberVo);

		int createAuthNo = memberAuthSQLMapper.createAuthNo();
		memberAuthVo.setAuth_no(createAuthNo);
		memberAuthVo.setMember_no(createMemberKey);
		memberAuthSQLMapper.insert(memberAuthVo);

	}

	// 메일 인증
	public void certification(String auth_key) {

		memberAuthSQLMapper.update(auth_key);
	}

	// 회원 번호로 회원 정보 가져오기
	public MemberVo myInfoData(int member_no) {
		
		MemberVo memberVo= memberSQLMapper.selectMemberByMemberNo(member_no);
		
		return memberVo;
	}
	
	
	// 회원 로그인
	public MemberVo login(String member_id, String member_pw) {

		// 비밀번호 암호화
		String hashCode = RMMessageDigest.digest(member_pw);

		return memberSQLMapper.selectByIdAndPw(member_id, hashCode);
	}
	
	// 관리자  로그인
	public AdminVo adminLogin(String admin_id, String admin_pw) {

		// 비밀번호 암호화
		String hashCode = RMMessageDigest.digest(admin_pw);

		return memberSQLMapper.selectAdminByIdAndPw(admin_id, hashCode);
	}

	// 자동로그인 체크한 경우에 사용자 테이블에 세션과 유효시간을 저장하기 위한 메서드
	public void keepLogin(String member_id, String sessionId, Date next) {

		// 아래가 수행되면서, 사용자 테이블에 세션id와 유효시간이 저장됨
		memberSQLMapper.keepLogin(member_id, sessionId, next);

	}

	// 이전에 로그인한 적이 있는지, 즉 유효시간이 넘지 않은 세션을 가지고 있는지 체크한다.
	public MemberVo checkUserWithSessionKey(String sessionId) {
		// 유효시간이 남아있고(>now()) 전달받은 세션 id와 일치하는 사용자 정보를 꺼낸다.
		return memberSQLMapper.checkUserWithSessionKey(sessionId);

	}

	// 아이디 중복확인
	public String checkId(String member_id) {

		String get_id = memberSQLMapper.selectMemberId(member_id);

		return get_id;
	}

	// 닉네임 중복확인
	public String checkNick(String member_nickname) {

		String get_nick = memberSQLMapper.selectMemberNick(member_nickname);

		return get_nick;
	}

	// 비밀번호 변경
	public void changePw(String member_id, String member_pw) {

		memberSQLMapper.updatePw(member_id, member_pw);
	}

	// 닉네임 변경
	public void changeNickname(String member_id, String member_nickname) {

		memberSQLMapper.updateNickname(member_id, member_nickname);
	}

	// 생년월일 변경
	public void changeBirthday(String member_id, String member_birth) {

		memberSQLMapper.updateBirthday(member_id, member_birth);
	}

	// 휴대전화 변경
	public void changePhone(String member_id, String member_phone) {

		memberSQLMapper.updatePhone(member_id, member_phone);
	}

	// 회원 탈퇴
	public void withdrawal(MemberVo memberVo) {

		memberSQLMapper.updateMember(memberVo);
	}
	
	// 아이디만 맞고 비밀번호 틀릴 경우
	public MemberVo loginTrial(String member_id, String member_pw) {
		
		// 비밀번호 암호화
		String hashCode = RMMessageDigest.digest(member_pw);
		
		MemberVo memberVo = memberSQLMapper.getMemberId(member_id, hashCode);
		
		return memberVo;
		
	}
	
	// 로그인 실패 회수 1 증가
	public void loginTrialCount(String member_id) {
		
		memberSQLMapper.updateMemberLoginTrial(member_id);
		
	}
	
	// 로그인 실패 회수 초기화(0으로)
	public void resetTrialCount(String member_id) {

		memberSQLMapper.resetMemberLoginTrial(member_id);
	}
	
	//관리자 아이디 중복확인
		public String checkAdminId(String admin_id) {
			
			String get_id = memberSQLMapper.selectAdminId(admin_id);
			
			return get_id;
		}
		//관리자 닉네임 중복확인
		public String checkAdminNick(String admin_nickname) {
			
			String get_nick = memberSQLMapper.selectAdminNick(admin_nickname);
			
			return get_nick;
		}
		//관리자 회원가입
		public void joinAdmin(AdminVo adminVo) {
			int createAdminKey = memberSQLMapper.createAdminKey();
			adminVo.setAdmin_no(createAdminKey);
			String hashCode = RMMessageDigest.digest(adminVo.getAdmin_pw());
			adminVo.setAdmin_pw(hashCode);
			memberSQLMapper.insertAdmin(adminVo);
			
		}

		// 관리자 아이디만 맞고 비밀번호 틀릴 경우
		public AdminVo loginAdminTrial(String admin_id, String admin_pw) {
			
			//비밀번호 암호화
			String hashCode = RMMessageDigest.digest(admin_pw);
			AdminVo adminVo = memberSQLMapper.getAdminId(admin_id, hashCode);
			
			return adminVo;
		}
		// 관리자 로그인 실패 횟수 1 증가
		public void loginAdminTrialCount(String admin_id) {
			
			memberSQLMapper.updateAdminLoginTrial(admin_id);
		}
		// 관리자 로그인 실패 횟수 초기화
		public void resetAdminTrialCount(String admin_id) {
			
			memberSQLMapper.resetAdminLoginTrial(admin_id);
		}
}
