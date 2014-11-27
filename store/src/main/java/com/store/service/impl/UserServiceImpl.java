package com.store.service.impl;

import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.dao.UserDAO;
import com.store.entity.User;
import com.store.result.CreateUserResult;
import com.store.result.HandleForgotPasswordResult;
import com.store.result.LoginResult;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.utils.SHA256Generator;
import com.store.web.form.LoginForm;
import com.store.web.form.SignUpForm;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	// @Autowired
	// private SessionDAO sessionDAO;

	@Transactional(readOnly = false)
	@Override
	public CreateUserResult createUser(SignUpForm form) {

		CreateUserResult result = new CreateUserResult(Constants.SUCCESS);

		User user = new User();
		String salt = UUID.randomUUID().toString();
		String hashedPassword;
		try {
			hashedPassword = SHA256Generator.hash(form.getPassword() + salt);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}

		user.setEmail(form.getEmail());
		user.setPassword(hashedPassword);
		user.setSalt(salt);
		user.setUsername(form.getUsername());
		user.setStatus(Constants.ENABLED);
		userDAO.create(user);

		// Session session = new Session();
		// String sessionkey = UUID.randomUUID().toString();
		// session.setSessionkey(sessionkey);
		// session.setTimeout(System.currentTimeMillis()
		// + Constants.DEFAULT_SESSION_TIMEOUT);
		// session.setUser(user);
		// sessionDAO.create(session);

		String sessionkey = UUID.randomUUID().toString();
		result.setSessionkey(sessionkey);

		return result;
	}

	@Transactional(readOnly = false)
	@Override
	public HandleForgotPasswordResult handleForgotPassword(String email) {
		HandleForgotPasswordResult result = new HandleForgotPasswordResult(
				Constants.SUCCESS);

		User user = userDAO.findByEmail(email);
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		Random rd = new Random();
		String newPlainTextPassword = String.valueOf(rd.nextLong());

		String hashedPassword;
		try {
			hashedPassword = SHA256Generator.hash(newPlainTextPassword
					+ user.getSalt());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}

		result.setNewpassword(hashedPassword);
		user.setPassword(hashedPassword);
		userDAO.update(user);

		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public LoginResult handleLogin(LoginForm loginForm) {
		LoginResult result = new LoginResult(Constants.SUCCESS);

		User user = userDAO.findByEmail(loginForm.getEmail());
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		String hashedPassword;
		try {
			hashedPassword = SHA256Generator.hash(loginForm.getPassword()
					+ user.getSalt());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}

		if (hashedPassword == null
				|| !hashedPassword.equalsIgnoreCase(user.getPassword())) {
			result.setStatus(Constants.LOGIN_FAILURE);
			return result;
		}

		String sessionkey = UUID.randomUUID().toString();
		result.setSessionkey(sessionkey);
		return result;
	}
}
