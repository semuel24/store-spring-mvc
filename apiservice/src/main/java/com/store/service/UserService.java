package com.store.service;

import com.store.dto.AddorUpdateUserDTO;
import com.store.dto.BatchRequestAccessDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.dto.ReportUsageDTO;
import com.store.result.BatchRequestAccessResult;
import com.store.result.StatusResult;

public interface UserService {

	public StatusResult handleStartUseVpnService(VerifyVpnAccessDTO dto);
	public StatusResult handleReportVpnUsageService(ReportUsageDTO dto);
	public void handleAddUserService(AddorUpdateUserDTO dto);
	public void handleUpdateUserService(AddorUpdateUserDTO dto);
	public void handleDeleteUserService(String productKey, String email);
	public BatchRequestAccessResult handleBatchRequestAccessService(BatchRequestAccessDTO dto);
}
