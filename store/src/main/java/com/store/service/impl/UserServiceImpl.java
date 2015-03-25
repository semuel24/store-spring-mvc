package com.store.service.impl;

import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.calling.api.AddorUpdateUserDTO;
import com.store.calling.api.ApiService;
import com.store.dao.ProductDAO;
import com.store.dao.SubscriptionStatusDAO;
import com.store.dao.UserDAO;
import com.store.dto.LoginServiceDTO;
import com.store.entity.Product;
import com.store.entity.User;
import com.store.result.CreateUserResult;
import com.store.result.HandleForgotPasswordResult;
import com.store.result.LoginResult;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.utils.SHA256Generator;
import com.store.web.form.LoginForm;
import com.store.web.form.SignUpForm;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SubscriptionStatusDAO subscriptionStatusDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	@Qualifier("apiService")
	private ApiService apiService;

	/**
	 * 1. create user in DB, 
	 * 2. bind default free-trial product to user and usage to 0 
	 * 3. put user data to redis through API service
	 */
	@Transactional(readOnly = false)
	public CreateUserResult createUser(SignUpForm form) {

		CreateUserResult result = new CreateUserResult(Constants.SUCCESS);

		// step1
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
		user.setStatus(Constants.ENABLED);
		userDAO.create(user);

		// step2
		Product freeProduct = productDAO
				.findProductByKey(Constants.PRODUCT.FREETRIAL.getProductKey());
		if (freeProduct == null) {
			throw new RuntimeException(
					"missing free trial product in createUser");
		}
		subscriptionStatusDAO.save(user, freeProduct);

		String sessionkey = UUID.randomUUID().toString();
		result.setSessionkey(sessionkey);

		// step3
		AddorUpdateUserDTO dto = new AddorUpdateUserDTO();
		dto.setEmail(form.getEmail());
		dto.setPassword(form.getPassword());
		dto.setPeriod(Constants.PERIOD.MONTHLY.getPeriod());
		dto.setProductKey(Constants.PRODUCT.FREETRIAL.getProductKey());
		dto.setSalt(salt);
		dto.setServiceStartTimestamp(System.currentTimeMillis());
		dto.setStatus(true);
		StatusResult callingResult = apiService.addUser(dto);
		if (callingResult == null
				|| !callingResult.getStatus().equalsIgnoreCase(
						Constants.SUCCESS)) {
			if (callingResult == null) {
				throw new RuntimeException(
						"In createUser callingResult is null.");
			} else {
				throw new RuntimeException("In createUser callingResult is "
						+ callingResult.getStatus());
			}
		}

		return result;
	}

	@Transactional(readOnly = false)
	// @Override
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

		result.setNewpassword(newPlainTextPassword);
		user.setPassword(hashedPassword);
		userDAO.update(user);
		
		//update to apiservice
		AddorUpdateUserDTO dto = new AddorUpdateUserDTO();
		dto.setEmail(email);
		dto.setSalt(user.getSalt());
		apiService.updateUser(dto);

		return result;
	}

	@Transactional(readOnly = true)
	// @Override
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

	@Transactional(readOnly = true)
	// @Override
	public StatusResult handleLoginService(LoginServiceDTO dto) {
		LoginResult result = new LoginResult(Constants.SUCCESS);

		User user = userDAO.findByEmail(dto.getEmail());
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		String hashedPassword;
		try {
			hashedPassword = SHA256Generator.hash(dto.getPassword()
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

		return result;
	}
	
	@Transactional(readOnly = true)
	public Boolean emailTaken(String email) {
		User user = userDAO.findByEmail(email);
		return user == null ? false:true; 
	}

}
