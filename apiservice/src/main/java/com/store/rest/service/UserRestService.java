package com.store.rest.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.store.dto.LoginServiceDTO;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.HttpServletUtil;
import com.store.utils.JSONConverter;

@Controller
public class UserRestService {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/loginservice",method = RequestMethod.POST)
	public void loginService(HttpServletRequest request,
			HttpServletResponse response) {
		
		String postBody = null;
		try {
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			LoginServiceDTO dto = mapper.readValue(postBody, LoginServiceDTO.class);
			
			StatusResult result = userService.handleLoginService(dto);
			HttpServletUtil.populateWithJSON(response, JSONConverter.getJson(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	private String getBody(HttpServletRequest request) throws IOException {
		InputStream is = new BufferedInputStream(request.getInputStream());
		int contentLength = request.getContentLength();
		byte[] data = new byte[contentLength];

		int offset = 0;
		while(offset < contentLength) {
		     final int readNow = is.read(data, offset, contentLength - offset);
		     if (readNow == -1) break;   // Unexpected EOF?
		     offset += readNow;
		}
		return new String(data,"UTF-8");
	}
	
}
