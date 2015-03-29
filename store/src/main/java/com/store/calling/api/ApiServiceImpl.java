package com.store.calling.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import com.store.result.StatusResult;
import com.store.utils.Constants;
import com.store.utils.JSONConverter;

public class ApiServiceImpl implements ApiService {

	private final Boolean BServiceOn = false;
	
	private WebTarget target;
	
	public WebTarget getTarget() {
		return target;
	}

	public void setTarget(WebTarget target) {
		this.target = target;
	}

	public StatusResult addAServer(AddVpnServerDTO dto) {
		if(!BServiceOn) {
			return new StatusResult(Constants.SUCCESS);
		}
		
		throw new UnsupportedOperationException("ApiServiceImpl AddAServer");
	}

	public StatusResult addUser(AddorUpdateUserDTO dto) {
		
		if(!BServiceOn) {
			return new StatusResult(Constants.SUCCESS);
		}
		
		if (target == null) {
			throw new RuntimeException("target is null in ApiServiceImpl.AddUser");
		}

		String s = target
				.path("/apiservice/admin/user").request()
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.header(Constants.APIKEY, Constants.AdminApiKey)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE),
						String.class);

		StatusResult sr = JSONConverter.getObject(s, StatusResult.class);
		return sr;
	}
	
	public StatusResult updateUser(AddorUpdateUserDTO dto) {
		
		if(!BServiceOn) {
			return new StatusResult(Constants.SUCCESS);
		}
		
		if (target == null) {
			throw new RuntimeException("target is null in ApiServiceImpl.updateUserPass");
		}

		String s = target
				.path("/apiservice/admin/user").request()
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.header(Constants.APIKEY, Constants.AdminApiKey)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE),
						String.class);

		StatusResult sr = JSONConverter.getObject(s, StatusResult.class);
		return sr;
	}
}
