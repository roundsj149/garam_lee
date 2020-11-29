package com.dayside.vacation.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 비밀번호 Hash: SHA-256
 * @author April
 *
 */
public class HashGeneration {
	
	public String hashGeneration(String pw, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(salt);

		byte[] hash = md.digest(pw.getBytes("UTF-8"));
		StringBuffer hexString = new StringBuffer();
		
		for (int i = 0; i < hash.length; i++) {

			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			
			hexString.append(hex);
		}

		return hexString.toString();
	}
}
