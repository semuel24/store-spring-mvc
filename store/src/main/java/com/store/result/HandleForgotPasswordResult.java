package com.store.result;

public class HandleForgotPasswordResult extends StatusResult {

	private String newpassword;
	
	public HandleForgotPasswordResult(String _status) {
		super(_status);
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

}
