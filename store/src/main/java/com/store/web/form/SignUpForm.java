package com.store.web.form;

public class SignUpForm {

	private String email;
	private String confirmedemail;
	private String password;
	private String confirmedpassword;
	private String invitationcode;
	private String[] agree;

	public String getEmail() {
		return email;
	}

	public String getInvitationcode() {
		return invitationcode;
	}

	public void setInvitationcode(String invitationcode) {
		this.invitationcode = invitationcode;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmedemail() {
		return confirmedemail;
	}

	public void setConfirmedemail(String confirmedemail) {
		this.confirmedemail = confirmedemail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedpassword() {
		return confirmedpassword;
	}

	public void setConfirmedpassword(String confirmedpassword) {
		this.confirmedpassword = confirmedpassword;
	}

	public String[] getAgree() {
		return agree;
	}

	public void setAgree(String[] agree) {
		this.agree = agree;
	}
}
