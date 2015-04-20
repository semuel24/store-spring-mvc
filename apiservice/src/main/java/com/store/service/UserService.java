package com.store.service;

import com.store.dto.AddorUpdateUserDTO;
import com.store.dto.BatchRequestAccessDTO;
import com.store.dto.DeviceControlDTO;
import com.store.dto.VerifyVpnAccessDTO;
import com.store.dto.ReportUsageDTO;
import com.store.exception.DBException;
import com.store.result.BatchRequestAccessResult;
import com.store.result.StatusResult;

public interface UserService {

	public StatusResult handleStartUseVpnService(VerifyVpnAccessDTO dto) throws DBException;
	public StatusResult handleReportVpnUsageService(ReportUsageDTO dto) throws DBException;
	public void handleAddUserService(AddorUpdateUserDTO dto);
	public void handleUpdateUserService(AddorUpdateUserDTO dto) throws DBException;
	public void handleDeleteUserService(String productKey, String email);
	public BatchRequestAccessResult handleBatchRequestAccessService(BatchRequestAccessDTO dto);
	public StatusResult handleControlDeviceService(DeviceControlDTO dto) throws DBException;
}
