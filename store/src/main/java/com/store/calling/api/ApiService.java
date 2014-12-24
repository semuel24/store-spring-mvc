package com.store.calling.api;

import com.store.result.StatusResult;

public interface ApiService {

	public StatusResult AddAServer(AddVpnServerDTO dto);
	public StatusResult AddUser(AddorUpdateUserDTO dto);
}
