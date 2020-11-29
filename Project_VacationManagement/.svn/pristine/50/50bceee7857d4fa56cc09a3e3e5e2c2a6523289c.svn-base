package com.dayside.vacation.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

/**
 * requestBody에 있는 데이터 가져오기. 
 * wrapper를 사용하지 않으면 데이터를 한 번 가져오고 못가져오게 되어(잘라내기처럼 됨)
 * filter를 거쳐 controller로 들어갈 경우 데이터를 전달할 수 없음
 * @author April
 *
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

	private byte[] bodyData;
	
	public HttpRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		InputStream is = super.getInputStream();
		bodyData = IOUtils.toByteArray(is);
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bis = new ByteArrayInputStream(bodyData);
		return new ServletImpl(bis);
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	public String getRequestBody() {
		return bodyData != null ? new String(bodyData) : null;
	}
	
	class ServletImpl extends ServletInputStream {

		private InputStream is;

		public ServletImpl(InputStream bis) {
			is = bis;
		}

		@Override
		public int read() throws IOException {
			return is.read();
		}

		@Override
		public int read(byte[] b) throws IOException {
			return is.read(b);
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletInputStream#isFinished()
		 */
		@Override
		public boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletInputStream#isReady()
		 */
		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletInputStream#setReadListener(javax.servlet.ReadListener)
		 */
		@Override
		public void setReadListener(ReadListener readListener) {
			// TODO Auto-generated method stub
			
		}

	}
}

