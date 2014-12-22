package com.store.rest.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.store.dto.ReportUsageDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.utils.HttpServletUtil;
import com.store.utils.JSONConverter;

@Controller
public class UserRestService extends RestService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserRestService.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/loginservice", method = RequestMethod.POST)
	public void verifyAccessinRedis(HttpServletRequest request,
			HttpServletResponse response) {

		String postBody = null;
		StatusResult result = null;
		try {
			// parse incoming data
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			VerifyVpnAccessDTO dto = mapper.readValue(postBody,
					VerifyVpnAccessDTO.class);

			String ip = request.getRemoteAddr();
			if (ip != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("incoming ip:" + ip);
				}
			}

			// todo: validate incoming data format

			// verify user access
			dto.setIncomingIp(ip);
			result = userService.handleStartUseVpnService(dto);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

	@RequestMapping(value = "/reportUsage", method = RequestMethod.POST)
	public void reportUsagetoRedis(HttpServletRequest request,
			HttpServletResponse response) {

		String postBody = null;
		StatusResult result = null;
		try {
			// parse incoming data
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			ReportUsageDTO dto = mapper.readValue(postBody,
					ReportUsageDTO.class);
			dto.setVpnServerIp(request.getRemoteAddr());

			// todo: validate input formats

			// handle reports
			result = userService.handleReportVpnUsageService(dto);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}
}
