package com.explorer.routemap.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class RMMailSenderThread extends Thread {

   private JavaMailSender javaMailSender;
   private String to;
   private String authKey;

   public RMMailSenderThread(JavaMailSender javaMailSender, String to, String authKey) {
      this.javaMailSender = javaMailSender;
      this.to = to;
      this.authKey = authKey;
   }

   public void run() {
      // 메일 발송 시작
      try {

         MimeMessage message = null;
         MimeMessageHelper messageHelper = null;
         message = javaMailSender.createMimeMessage();
         messageHelper = new MimeMessageHelper(message, true, "UTF-8");
         messageHelper.setSubject("[WEB 발송] RouteMap 회원가입을 축하드립니다.");

         String link = "http://3.34.137.15:8080/member/certification_process.do?auth_key=" + authKey;
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append("RouteMap 회원 가입을 축하드립니다.<br>");
         stringBuilder.append("회원 가입 완료를 위해 아래 링크를 클릭해 주세요.<br>");
         stringBuilder.append("<a href='" + link + "'>");
         stringBuilder.append("링크를 클릭해주세요.");
         stringBuilder.append("</a>");
         String text = stringBuilder.toString();

         messageHelper.setText(text, true);
         messageHelper.setFrom("MonRad", "RouteMap");
         messageHelper.setTo(to); // 회원 가입자 아이디
         javaMailSender.send(message);

      } catch (Exception e) {
         e.printStackTrace();
      }
      // 메일 발송 시작 끝
   }

}