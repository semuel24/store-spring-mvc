package com.store.web.form;

public class ChangePasswordWithCodeForm {

	private String email;
	private String code;
	private String newpass;
	private String newpassconfirm;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	public String getNewpassconfirm() {
		return newpassconfirm;
	}

	public void setNewpassconfirm(String newpassconfirm) {
		this.newpassconfirm = newpassconfirm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
