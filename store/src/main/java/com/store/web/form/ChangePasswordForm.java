package com.store.web.form;

public class ChangePasswordForm {

	private String email;
	private String oldpass;
	private String newpass;
	private String newpassconfirm;

	public String getOldpass() {
		return oldpass;
	}

	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
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
