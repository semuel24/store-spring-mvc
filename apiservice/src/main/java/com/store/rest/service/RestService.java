package com.store.rest.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

public class RestService {

	protected String getBody(HttpServletRequest request) throws IOException {
		InputStream is = new BufferedInputStream(request.getInputStream());
		int contentLength = request.getContentLength();
		byte[] data = new byte[contentLength];

		int offset = 0;
		while (offset < contentLength) {
			final int readNow = is.read(data, offset, contentLength - offset);
			if (readNow == -1)
				break; // Unexpected EOF?
			offset += readNow;
		}
		return new String(data, "UTF-8");
	}
}
