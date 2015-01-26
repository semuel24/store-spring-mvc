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

import com.store.dto.BatchRequestAccessDTO;
import com.store.dto.ReportUsageDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.result.BatchRequestAccessResult;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.utils.HttpServletUtil;
import com.store.utils.JSONConverter;

/**
 * This controller accepts requests from vpn servers.
 *
 */
@Controller
public class UserRestService extends RestService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserRestService.class);

	private static String userApiKey = "74b49f01-7009-4e98-80ef-e85004128e7d";

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

			if (logger.isInfoEnabled()) {
				logger.info("### UserRestService.verifyAccessinRedis started to handle dto: "
						+ dto.toString());
			}
			if (dto.getIncomingIp() == null
					|| "".equalsIgnoreCase(dto.getIncomingIp())) {
				dto.setIncomingIp(request.getRemoteAddr());
				if (logger.isInfoEnabled()) {
					logger.info("verifyAccessinRedis - caught remote ip:"
							+ dto.getIncomingIp());
				}
			}

			// todo: validate incoming data format

			// verify user access
			result = userService.handleStartUseVpnService(dto);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### UserRestService.verifyAccessinRedis finished handling result: "
					+ result.getStatus());
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}
	
	@RequestMapping(value = "/deviceControlService", method = RequestMethod.POST)
	public void verifyDeviceinRedis(HttpServletRequest request,
			HttpServletResponse response) {

		String postBody = null;
		StatusResult result = null;
		try {
			// parse incoming data
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			VerifyVpnAccessDTO dto = mapper.readValue(postBody,
					VerifyVpnAccessDTO.class);

			if (logger.isInfoEnabled()) {
				logger.info("### UserRestService.verifyDeviceinRedis started to handle dto: "
						+ dto.toString());
			}
			if (dto.getIncomingIp() == null
					|| "".equalsIgnoreCase(dto.getIncomingIp())) {
				dto.setIncomingIp(request.getRemoteAddr());
				if (logger.isInfoEnabled()) {
					logger.info("verifyDeviceinRedis - caught remote ip:"
							+ dto.getIncomingIp());
				}
			}

			// todo: validate incoming data format

			// verify user access
			result = userService.handleControlDeviceService(dto);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### UserRestService.verifyDeviceinRedis finished handling result: "
					+ result.getStatus());
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

			if (logger.isInfoEnabled()) {
				logger.info("### UserRestService.reportUsagetoRedis started to handle dto: "
						+ dto.toString());
			}
			if (dto.getVpnServerIp() == null
					|| "".equalsIgnoreCase(dto.getVpnServerIp())) {
				dto.setVpnServerIp(request.getRemoteAddr());
				if (logger.isErrorEnabled()) {
					logger.error("reportUsagetoRedis - caught remote ip:"
							+ dto.getVpnServerIp());
				}
			}

			// todo: validate input formats

			// handle reports
			result = userService.handleReportVpnUsageService(dto);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### UserRestService.reportUsagetoRedis finished handling result: "
					+ result.getStatus());
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

	@RequestMapping(value = "/batchRequestAccess", method = RequestMethod.POST)
	public void batchRequestAccessInRedis(HttpServletRequest request,
			HttpServletResponse response) {

		String postBody = null;
		BatchRequestAccessResult result = null;
		try {
			// parse incoming data
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			BatchRequestAccessDTO dto = mapper.readValue(postBody,
					BatchRequestAccessDTO.class);

			if (logger.isInfoEnabled()) {
				logger.info("### UserRestService.batchRequestAccessInRedis started to handle dto: "
						+ dto.toString());
			}
			if (dto.getVpnServerIp() == null
					|| "".equalsIgnoreCase(dto.getVpnServerIp())) {
				dto.setVpnServerIp(request.getRemoteAddr());
				if (logger.isErrorEnabled()) {
					logger.error("batchRequestAccessInRedis - caught remote ip:"
							+ dto.getVpnServerIp());
				}
			}

			// todo: validate input formats

			// handle reports
			result = userService.handleBatchRequestAccessService(dto);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new BatchRequestAccessResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### UserRestService.batchRequestAccessInRedis finished handling result: "
					+ result.getStatus());
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

}
