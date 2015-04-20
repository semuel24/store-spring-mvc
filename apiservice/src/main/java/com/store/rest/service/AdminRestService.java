package com.store.rest.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Autowired
	@Qualifier("adminApiKey")
	private String adminApiKey;

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
			//auth
			if(!HttpServletUtil.ValidateApiKey(request, adminApiKey)) {
				result = new StatusResult(Constants.INVALID_APIKEY);
			}
			
			if(result == null) {
				// parse incoming data
				postBody = getBody(request);
				ObjectMapper mapper = new ObjectMapper();
				AddVpnServerDTO dto = mapper.readValue(postBody,
						AddVpnServerDTO.class);
	
				if (logger.isInfoEnabled()) {
					logger.info("### AdminRestService.addVpnServer started to handle dto: "
							+ dto.toString());
				}
				// todo: validate incoming data format
	
				vpnServerService.handleAddServer(dto);
				result = new StatusResult(Constants.SUCCESS);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}

		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.addVpnServer finished handling result: "
					+ result.getStatus());
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

	@RequestMapping(value = "/admin/vpnserver/{ip}/{productKey}", method = RequestMethod.DELETE)
	public void deleteVpnServer(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("ip") String ip,
			@PathVariable("productKey") String productKey) {

		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.deleteVpnServer started to handle productKey: "
					+ productKey + " | ip:" + ip);
		}
		StatusResult result = null;
		try {
			//auth
			if(!HttpServletUtil.ValidateApiKey(request, adminApiKey)) {
				result = new StatusResult(Constants.INVALID_APIKEY);
			}
			
			if(result == null) {
				// todo: validate incoming data format
	
				vpnServerService.handleDeleteServer(productKey, ip);
				result = new StatusResult(Constants.SUCCESS);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.deleteVpnServer finished handling result: "
					+ result.getStatus());
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));

	}

	@RequestMapping(value = "/admin/vpnservers", method = RequestMethod.GET)
	public void getVpnServers(HttpServletRequest request,
			HttpServletResponse response) {

		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.getVpnServers started to handle");
		}
		ServerListResult result = null;
		try {
			//auth
			if(!HttpServletUtil.ValidateApiKey(request, adminApiKey)) {
				result = new ServerListResult(Constants.INVALID_APIKEY);
			}
			
			if(result == null) {
				// todo: validate incoming data format
	
				Map<String, Map<String, VpnServerInfo>> servers = vpnServerService
						.handleFindAllServers();
				result = new ServerListResult(Constants.SUCCESS);
				result.setServers(servers);
			}
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
			//auth
			if(!HttpServletUtil.ValidateApiKey(request, adminApiKey)) {
				result = new StatusResult(Constants.INVALID_APIKEY);
			}
			
			if(result == null) {
				// parse incoming data
				postBody = getBody(request);
				ObjectMapper mapper = new ObjectMapper();
				AddorUpdateUserDTO dto = mapper.readValue(postBody,
						AddorUpdateUserDTO.class);
				if (logger.isInfoEnabled()) {
					logger.info("### AdminRestService.addUser started to handle dto: "
							+ dto.toString());
				}
				// todo: validate incoming data format
	
				// verify user access
				userService.handleAddUserService(dto);
				result = new StatusResult(Constants.SUCCESS);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.addUser finished handling result: "
					+ result.getStatus());
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
			//auth
			if(!HttpServletUtil.ValidateApiKey(request, adminApiKey)) {
				result = new StatusResult(Constants.INVALID_APIKEY);
			}
			
			if(result == null) {
				// parse incoming data
				postBody = getBody(request);
				ObjectMapper mapper = new ObjectMapper();
				AddorUpdateUserDTO dto = mapper.readValue(postBody,
						AddorUpdateUserDTO.class);
				if (logger.isInfoEnabled()) {
					logger.info("### AdminRestService.updateUser started to handle dto: "
							+ dto.toString());
				}
				// todo: validate incoming data format
	
				// verify user access
				userService.handleUpdateUserService(dto);
				result = new StatusResult(Constants.SUCCESS);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.updateUser finished handling result: "
					+ result.getStatus());
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));
	}

	@RequestMapping(value = "/admin/user/{productKey}/{email}", method = RequestMethod.DELETE)
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("productKey") String productKey,
			@PathVariable("email") String email) {

		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.deleteUser started to handle productKey: "
					+ productKey + " | email:" + email);
		}
		StatusResult result = null;
		try {
			//auth
			if(!HttpServletUtil.ValidateApiKey(request, adminApiKey)) {
				result = new StatusResult(Constants.INVALID_APIKEY);
			}
			
			if(result == null) {
				// todo: validate incoming data format
	
				// handle deletion
				userService.handleDeleteUserService(productKey, email);
				result = new StatusResult(Constants.SUCCESS);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			result = new StatusResult(Constants.GENERAL_FAILURE);
		}
		if (logger.isInfoEnabled()) {
			logger.info("### AdminRestService.deleteUser finished handling result: "
					+ result.getStatus());
		}
		HttpServletUtil.populateWithJSON(response,
				JSONConverter.getJson(result));

	}
}
