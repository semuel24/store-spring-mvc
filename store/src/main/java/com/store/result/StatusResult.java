package com.store.result;

import com.store.utils.Constants;
import com.store.utils.EmailUtil;

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
			return "操作成功";
		}
		
		if(Constants.GENERAL_FAILURE.equalsIgnoreCase(errorCode)) {
			return "你的操作发生错误，请与管理员联系 " + EmailUtil.SOURCE_EMAIL;
		}
		
		return "发生未知错误，请与管理员联系 " + EmailUtil.SOURCE_EMAIL;
	}
	
	public String getMessage() {
		//translate error code to error message
		return StatusResult.convertErrorCode2Message(this.getStatus());
	}
	
}
