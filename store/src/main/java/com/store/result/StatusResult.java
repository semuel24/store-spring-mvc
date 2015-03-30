package com.store.result;

import com.store.utils.Constants;
import com.store.utils.MessageConstants;

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

		if (Constants.SUCCESS.equalsIgnoreCase(errorCode)) {
			return MessageConstants.OPERATION_SUCCESS;
		}

		if (Constants.INVALID_CHANGE_PASSWORD_CODE.equalsIgnoreCase(errorCode)) {
			return MessageConstants.CHANGE_PASSWORD_CODE_INVALID;
		}

		if (Constants.GENERAL_FAILURE.equalsIgnoreCase(errorCode)) {
			return MessageConstants.GENERAL_ERROR;
		}

		return MessageConstants.GENERAL_ERROR;
	}

	public String getMessage() {
		// translate error code to error message
		return StatusResult.convertErrorCode2Message(this.getStatus());
	}

}
