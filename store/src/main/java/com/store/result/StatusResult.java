package com.store.result;

import com.store.utils.Constants;

public class StatusResult {
	
	private String status;
	
	public StatusResult(String _status) {
		this.status = _status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public static String convertErrorCode2Message(String errorCode) {
		
		if(Constants.SUCCESS.equalsIgnoreCase(errorCode)) {
			return "success";
		}
		
		if(Constants.GENERAL_FAILURE.equalsIgnoreCase(errorCode)) {
			return "general failure";
		}
		
		return "unknown";
	}
	
}