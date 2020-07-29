package com.explorer.routemap.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.NumbersAnswerProducer;

public class CaptchaUtil {
	
	public CaptchaUtil() {
		
	}
	
	// 캡차 이미지 생성
	public void captchaImg(HttpServletRequest request, HttpServletResponse response) {
		Captcha captcha = new Captcha.Builder(200, 60)
				.addText(new NumbersAnswerProducer(6))
				.addNoise().addNoise().addNoise()
				.addBackground(new GradiatedBackgroundProducer())
				.addBorder()
				.build();
				
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Max-Age", 0);
		response.setContentType("image/png");
		
		CaptchaServletUtil.writeImage(response, captcha.getImage());
		request.getSession().setAttribute("captcha", captcha.getAnswer());
	}
}
















