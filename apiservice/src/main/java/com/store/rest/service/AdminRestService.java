package com.store.rest.service;

import java.util.Map;
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
import com.store.redis.client.VpnServerInfo;
import com.store.result.ServerListResult;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.service.VpnServerService;
import com.store.utils.Constants;
import com.store.utils.HttpServletUtil;
import com.store.utils.JSONConverter;

/**
 * This controller only accepts requests from the web site.
 */
@Controller
public class AdminRestService extends RestService {

	private static final Logger logger = LoggerFactory
			.getLogger(AdminRestService.class);
	
	private static String adminApiKey = "2e078028-3196-4361-a027-d9f19835cc7a";

	@Autowired
	private VpnServerService vpnServerService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/admin/vpnserver", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/admin/vpnserver/{ip}/{productKey}", method = RequestMethod.DELETE)
	public void deleteVpnServer(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("ip") String ip,
			@PathVariable("productKey") String productKey) {

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

	@RequestMapping(value = "/admin/vpnservers", method = RequestMethod.GET)
	public void getVpnServers(HttpServletRequest request,
			HttpServletResponse response) {

		ServerListResult result = null;
		try {
			// todo: validate incoming data format

			Map<String, Map<String, VpnServerInfo>> servers = vpnServerService
					.handleFindAllServers();
			result = new ServerListResult(Constants.SUCCESS);
			result.setServers(servers);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new ServerListResult(Constants.GENERAL_FAILURE);
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

	@RequestMapping(value = "/admin/user", method = RequestMethod.POST)
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
			userService.handleAddUserService(dto);
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

	@RequestMapping(value = "/admin/user", method = RequestMethod.PUT)
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
			userService.handleUpdateUserService(dto);
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

	@RequestMapping(value = "/admin/user/{productKey}/{ip}", method = RequestMethod.DELETE)
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("productKey") String productKey,
			@PathVariable("email") String email) {

		StatusResult result = null;
		try {
			// todo: validate incoming data format

			// handle deletion
			userService.handleDeleteUserService(productKey, email);
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
}
