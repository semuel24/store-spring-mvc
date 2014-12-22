package com.store.rest.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.dto.AddVpnServerDTO;
import com.store.dto.AddorUpdateUserDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.service.VpnServerService;
import com.store.utils.Constants;
import com.store.utils.HttpServletUtil;
import com.store.utils.JSONConverter;

/**
 * This controller only accepts requests from tubevpn website.
 */
@Controller
public class AdminRestService extends RestService {

	private static final Logger logger = LoggerFactory
			.getLogger(AdminRestService.class);

	@Autowired
	private VpnServerService vpnServerService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/addVpnServer", method = RequestMethod.PUT)
	public void addVpnServer(HttpServletRequest request,
			HttpServletResponse response) {

		String postBody = null;
		StatusResult result = null;
		try {
			// parse incoming data
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			AddVpnServerDTO dto = mapper.readValue(postBody,
					AddVpnServerDTO.class);

			// todo: validate incoming data format

			vpnServerService.handleAddServer(dto);
			result = new StatusResult(Constants.SUCCESS);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

	@RequestMapping(value = "/deleteVpnServer/{productKey}/{ip}", method = RequestMethod.DELETE)
	public void deleteVpnServer(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("productKey") String productKey,
			@PathVariable("ip") String ip) {
		
		StatusResult result = null;
		try {
			// todo: validate incoming data format

			vpnServerService.handleDeleteServer(productKey, ip);
			result = new StatusResult(Constants.SUCCESS);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
		
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response) {
		String postBody = null;
		StatusResult result = null;
		try {
			// parse incoming data
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			AddorUpdateUserDTO dto = mapper.readValue(postBody,
					AddorUpdateUserDTO.class);

			// todo: validate incoming data format

			// verify user access
			result = userService.handleAddUserService(dto);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
	public void updateUser(HttpServletRequest request,
			HttpServletResponse response) {

		String postBody = null;
		StatusResult result = null;
		try {
			// parse incoming data
			postBody = getBody(request);
			ObjectMapper mapper = new ObjectMapper();
			AddorUpdateUserDTO dto = mapper.readValue(postBody,
					AddorUpdateUserDTO.class);

			// todo: validate incoming data format

			// verify user access
			result = userService.handleUpdateUserService(dto);
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
