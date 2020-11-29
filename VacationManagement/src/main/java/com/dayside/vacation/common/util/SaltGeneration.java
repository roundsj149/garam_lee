package com.dayside.vacation.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * hash에 첨가할 salt값 생성
 * @author April
 *
 */
public class SaltGeneration {
	
	public byte[] saltGeneration() throws NoSuchAlgorithmException {
		
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[32];
		// bytes 바이트 배열을 난수로 채우기
		
		random.nextBytes(bytes);
		
		return bytes;
		
	}
}