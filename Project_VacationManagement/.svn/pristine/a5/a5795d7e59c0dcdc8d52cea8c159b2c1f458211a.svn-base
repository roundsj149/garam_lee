package com.dayside.vacation.member.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dayside.vacation.member.mapper.MemberMapper;
import com.dayside.vacation.member.model.Member;
import com.dayside.vacation.util.HashGeneration;
import com.dayside.vacation.util.SaltGeneration;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper memberMapper;
	
	// 회원 정보 가져오기
	public Member getMemberList() {
		
		return memberMapper.selectAllMember();
		
	}
	
	// 회원 가입
	public void joinMember(Member member) {
		
		// 비밀번호 salt + hash
		String hashedPw=null;
		byte[] saltByte=null;
		String salt = null;
		
		SaltGeneration saltGeneration = new SaltGeneration();
		HashGeneration hash = new HashGeneration();
		
		try {
			saltByte = saltGeneration.saltGeneration();
			hashedPw = hash.hashGeneration(member.getPw(), saltByte);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		salt = new String(Base64.getEncoder().encode(saltByte));
		member.setPw(hashedPw);
		member.setHashSalt(salt.toString());
		
		memberMapper.insertMemberInfo(member);
	}
	
	// 로그인
	public Member getOneMember(String emailId, String pw) {
		
		// 일치하는 아이디 있는지 먼저 확인 -> 해당 salt 가져와서 입력받은 pw로 해시한 후 비밀번호 같은지 확인해야 하므로!
		Member member = memberMapper.selectId(emailId);
		// 일치하는 아이디 없음 - 로그인 실패
		if(member == null) {
			
			return null;
		// 일치하는 아이디 있음
		} else {
			String hashedPw=null;
			String salt = member.getHashSalt();
			HashGeneration hash = new HashGeneration();
			
			try {
				hashedPw = hash.hashGeneration(pw, Base64.getDecoder().decode(salt));
			} catch (NoSuchAlgorithmException e) {
				
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
			
			// 비밀번호 맞음 - 로그인 절반 성공(상태 확인 안했음 - controller에서!)
			if(hashedPw.equals(member.getPw())) {
				
				return member;
			} else {
			// 비밀번호 틀림 - 로그인 실패
				return null;
			}
		}
	}
	
	// 사번 중복확인
	public String checkEmployeeNo(String employeeNo) {
		
		return memberMapper.selectEmployeeNo(employeeNo);
	}
	
	// 아이디 중복 확인
	public String checkEmailId(String emailId) {
		
		return memberMapper.selectEmailId(emailId);
	}
	
	// 소속팀명 가져오기
	public String[] getAllTeam() {
		
		return memberMapper.selectAllTeam();
	}
}




















