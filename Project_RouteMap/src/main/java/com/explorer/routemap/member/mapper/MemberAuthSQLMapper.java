package com.explorer.routemap.member.mapper;

import com.explorer.routemap.member.vo.MemberAuthVo;

public interface MemberAuthSQLMapper {
   
   // 회원 인증 번호 생성
   public int createAuthNo();
   
   // 회원 인증 생성
   public void insert(MemberAuthVo memberAuthVo);
   
   // 메일 인증
   public void update(String auth_key);
   
}