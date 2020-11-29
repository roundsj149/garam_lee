package com.dayside.vacation.member.model;

import lombok.Data;

/**
 * 비밀번호 변경 요청 객체
 * @author April
 *
 */
@Data
public class UpdatePw {
	private String currPw;
	private String newPw;
	private String newPwCheck;
}
