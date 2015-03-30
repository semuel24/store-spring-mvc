package com.store.result;

public class HandleForgotPasswordResult extends StatusResult {

	private String changePasswordCode;

	public HandleForgotPasswordResult(String _status) {
		super(_status);
	}

	public String getChangePasswordCode() {
		return changePasswordCode;
	}

	public void setChangePasswordCode(String newpassword) {
		this.changePasswordCode = newpassword;
	}
}
