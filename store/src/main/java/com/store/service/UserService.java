package com.store.service;

import com.store.dto.LoginServiceDTO;
import com.store.result.CreateUserResult;
import com.store.result.HandleForgotPasswordResult;
import com.store.result.LoginResult;
import com.store.result.StatusResult;
import com.store.web.form.ChangePasswordForm;
import com.store.web.form.ChangePasswordWithCodeForm;
import com.store.web.form.ContactForm;
import com.store.web.form.LoginForm;
import com.store.web.form.SignUpForm;

public interface UserService {

	public CreateUserResult createUser(SignUpForm form);

	public HandleForgotPasswordResult handleForgotPassword(String email);

	public StatusResult handleChangePassword(
			ChangePasswordForm changePasswordForm);

	public StatusResult handleChangePasswordWithCode(
			ChangePasswordWithCodeForm changePasswordWithCodeForm);

	public LoginResult handleLogin(LoginForm loginForm);

	public StatusResult handleLoginService(LoginServiceDTO dto);

	public Boolean emailTaken(String email);

	public void handleLeaveMessage(ContactForm contactForm);

	// public User lookupUser();
}
