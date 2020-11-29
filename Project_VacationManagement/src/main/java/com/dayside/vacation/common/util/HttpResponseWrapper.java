package com.dayside.vacation.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseWrapper extends HttpServletResponseWrapper {

	private static final Logger logger = LoggerFactory.getLogger(HttpResponseWrapper.class);
	
	private CustomServletOutputStream out = null;

	/**
	 * <pre>
	 * Constructor of HttpResponseWrapper class
	 * </pre>
	 *
	 * @param response
	 */
	public HttpResponseWrapper(HttpServletResponse response) {
		super(response);
		try {
			out = new CustomServletOutputStream(response.getOutputStream());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(out);
	}

	public String getResponseBody() {
		return out.getCopy();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}

	class CustomServletOutputStream extends ServletOutputStream {
		private ByteBuffer buff = null;
		private OutputStream os;

		public CustomServletOutputStream(OutputStream os) {
			this.os = os;
			buff = ByteBuffer.allocate(1024 * 1024);
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			
		}
		
		public String getCopy() {
			ByteBuffer tmpBuff = ByteBuffer.allocate(buff.position());

			for (int i = 0; i < buff.position(); i++) {
				tmpBuff.put(buff.get(i));
			}
			
			return new String(tmpBuff.array());
		}

		@Override
		public void write(int b) throws IOException {
			buff.put((byte)b);
			os.write(b);
		}
	}
}
