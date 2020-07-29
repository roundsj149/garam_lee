package com.explorer.routemap.util;

import java.security.MessageDigest;

public class RMMessageDigest {
   
   public static String digest(String value) {

      String hashCode = null;

      // 비밀번호 해싱 암호화 시작
      try {

         StringBuilder stringBuilder = new StringBuilder();

         MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

         messageDigest.reset();
         messageDigest.update((value + "#*DK*").getBytes());

         byte[] chars = messageDigest.digest();

         for (int i = 0; i < chars.length; i++) {

            String temp = Integer.toHexString(chars[i] & 0xff);

            // 문자열 연산을 반복문으로 다량으로하면 속도가 늦어진다. 해결법 : StringBuilder
            // 0 ~ FF 까지
            if (temp.length() == 1) {
               stringBuilder.append("0");
            }
            stringBuilder.append(temp);

         }

         hashCode = stringBuilder.toString();

      } catch (Exception e) {
         e.printStackTrace();
      }

      // 비밀번호 해싱 암호화 끝

      return hashCode;
   }
}