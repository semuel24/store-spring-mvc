package com.store.web.form;

public class SignUpForm {

//	private String name;
	private String email;
	private String confirmedemail;
	private String password;
	private String confirmedpassword;
	private String ivitationcode;
	private String[] agree;
	
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
	public String getEmail() {
		return email;
	}

	public String getIvitationcode() {
		return ivitationcode;
	}

	public void setIvitationcode(String ivitationcode) {
		this.ivitationcode = ivitationcode;
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
