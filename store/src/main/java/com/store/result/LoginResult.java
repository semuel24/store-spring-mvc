package com.store.result;

public class LoginResult extends StatusResult {

	private String sessionkey;
	
	public LoginResult(String _status) {
		super(_status);
		// TODO Auto-generated constructor stub
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

}
