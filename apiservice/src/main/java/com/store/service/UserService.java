package com.store.service;

import com.store.dto.AddorUpdateUserDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.dto.ReportUsageDTO;
import com.store.result.StatusResult;

public interface UserService {

	public StatusResult handleStartUseVpnService(VerifyVpnAccessDTO dto);
	public StatusResult handleReportVpnUsageService(ReportUsageDTO dto);
	public StatusResult handleAddUserService(AddorUpdateUserDTO dto);
	public StatusResult handleUpdateUserService(AddorUpdateUserDTO dto);
}
