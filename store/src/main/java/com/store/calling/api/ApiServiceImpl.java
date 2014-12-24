package com.store.calling.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import com.store.result.StatusResult;
import com.store.utils.JSONConverter;

public class ApiServiceImpl implements ApiService {

	private WebTarget target;
	
	public WebTarget getTarget() {
		return target;
	}

	public void setTarget(WebTarget target) {
		this.target = target;
	}

	public StatusResult AddAServer(AddVpnServerDTO dto) {
		throw new UnsupportedOperationException("ApiServiceImpl AddAServer");
	}

	public StatusResult AddUser(AddorUpdateUserDTO dto) {
		if (target == null) {
			throw new RuntimeException("target is null in ApiServiceImpl.AddUser");
		}

		String s = target
				.path("/apiservice/admin/user").request()
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE),
						String.class);

		StatusResult sr = JSONConverter.getObject(s, StatusResult.class);
		return sr;
	}
}
