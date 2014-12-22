package com.store.service;

import com.store.dto.AddVpnServerDTO;

public interface VpnServerService {

	public void handleAddServer(AddVpnServerDTO dto);
	public void handleDeleteServer(String productKey, String ip);
}
