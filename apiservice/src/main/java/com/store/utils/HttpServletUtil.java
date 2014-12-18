package com.store.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpServletUtil {

	public final static String METHOD_POST = "POST";
	public final static String METHOD_GET = "GET";
	private static final Log logging = LogFactory.getLog(HttpServletUtil.class);

	public static boolean isPost(HttpServletRequest request) {
		if (METHOD_POST.equals(request.getMethod())) {
			return true;
		}
		return false;
	}

	public static void populateWithJSON(HttpServletResponse response, String jsonStr) {
		if (null != jsonStr) {
			PrintWriter out = null;
			try {
				// NOTE: proper JSON content type
				response.setContentType("application/json;charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				out = response.getWriter();
				out.write(jsonStr);
			} catch (IOException e) {    
				logging.error(e, e);
			} finally {
				if (null != out) {
					out.close();
				}
			}
		}
	}

	public static void populateWithXMl(HttpServletResponse response, String xmlStr) {
		if (null != xmlStr) {
			PrintWriter out = null;
			try {
				response.setContentType("application/xml");
				out = response.getWriter();
				out.print(xmlStr);
			} catch (IOException e) {
				logging.error(e, e);
			} finally {
				if (null != out) {
					out.close();
				}
			}
		}

	}

	public static String getApplicationRoot(HttpServletRequest request) {
		return request.getRealPath("/");
	}

	
	public static String getStatusFromHttpRequest(HttpServletRequest request) {
		String status = request.getParameter("status");
		return status;
	}
	
}
