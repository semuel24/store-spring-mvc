package com.store.result;

public class CreateUserResult extends StatusResult{
	
	private String sessionkey;
	
	public CreateUserResult(String _status) {
		super(_status);
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

//	public String getMessage() {
//		//translate error code to error message
//		return StatusResult.convertErrorCode2Message(this.getStatus());
//	}

}
