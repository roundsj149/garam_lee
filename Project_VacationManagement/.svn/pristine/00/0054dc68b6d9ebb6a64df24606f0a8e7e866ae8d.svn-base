package com.dayside.vacation.common.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.dayside.vacation.common.constants.VacationConstants;

public class MailSenderThread {

	public void sendEmail(HttpServletRequest request, String authKey, String to) throws Exception {

		request.setCharacterEncoding("utf-8");
		String port = VacationConstants.MAILPLUG_PORT;
		String link = "http://localhost:8181/member/certification_process?auth_key=" + authKey;
		Properties prop = new Properties(); // 정보를 담을 객체

		prop.put("mail.smtp.host", "smtp.mailplug.co.kr");
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.debug", "true");
		prop.put("mail.smtp.socketFactory.port", port);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.fallback", "false");

		try {
			Authenticator auth = new SMTPAuthenticatior();
			Session session = Session.getInstance(prop, auth);

			session.setDebug(true);
			MimeMessage msg = new MimeMessage(session); // 메일 내용을 담을 객체

			msg.setSubject("[WEB 발송] 데이사이드 근태 관리 사이트 회원가입을 축하드립니다."); // 제목

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("데이사이드 근태 관리 사이트 회원 가입을 축하드립니다.<br>");
			buffer.append("회원 가입 완료를 위해 아래 링크를 클릭해 주세요.<br>");
			buffer.append("<a href='" + link + "'>");
			buffer.append("링크를 클릭해주세요.</a>");

			Address fromAddr = new InternetAddress("grlee@dayside.co.kr");
			msg.setFrom(fromAddr);

			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr); // 받는 사람

			msg.setContent(buffer.toString(), "text/html;charset=UTF-8"); // 내용
			Transport.send(msg); // 전송

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public class SMTPAuthenticatior extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			//TODO: 메일 비번 안보이게 넣는 방법 알아야 함
			return new PasswordAuthentication("grlee@dayside.co.kr", "메일 비번");
		}
	}

}
