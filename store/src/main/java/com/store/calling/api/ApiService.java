package com.store.calling.api;

import com.store.result.StatusResult;

public interface ApiService {

	public StatusResult addAServer(AddVpnServerDTO dto);
	public StatusResult addUser(AddorUpdateUserDTO dto);
	public StatusResult updateUser(AddorUpdateUserDTO dto);
}
