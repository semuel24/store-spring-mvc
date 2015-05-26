package com.store.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.calling.api.AddorUpdateUserDTO;
import com.store.calling.api.ApiService;
import com.store.dao.MessageDAO;
import com.store.dao.PasswordHelperCodeDAO;
import com.store.dao.ProductDAO;
import com.store.dao.SubscriptionStatusDAO;
import com.store.dao.UserDAO;
import com.store.dto.LoginServiceDTO;
import com.store.entity.Message;
import com.store.entity.PasswordHelperCode;
import com.store.entity.Product;
import com.store.entity.SubscriptionStatus;
import com.store.entity.User;
import com.store.result.CreateUserResult;
import com.store.result.HandleForgotPasswordResult;
import com.store.result.LoginResult;
import com.store.result.StatusResult;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.utils.SHA256Generator;
import com.store.utils.StoreCharsetUtil;
import com.store.web.form.ChangePasswordForm;
import com.store.web.form.ChangePasswordWithCodeForm;
import com.store.web.form.ContactForm;
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
	private MessageDAO messageDAO;
	
	@Autowired
	private PasswordHelperCodeDAO passwordHelperCodeDAO;

	@Autowired
	@Qualifier("apiService")
	private ApiService apiService;

	/**
	 * 1. create user in DB, 2. bind default free-trial product to user and
	 * usage to 0 3. put user data to redis through API service
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
		apiService.addUser(dto);
		
		return result;
	}
	
	@Transactional(readOnly = false)
	// @Override
	public HandleForgotPasswordResult handleForgotPassword(String email) {
		HandleForgotPasswordResult result = new HandleForgotPasswordResult(
				Constants.SUCCESS);

		//some validation
		User user = userDAO.findByEmail(email);
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		//generate change password helper code
		String code = GenerateChangePasswordCode(10);
		result.setChangePasswordCode(code);
		
		//save to temp password helper table
		PasswordHelperCode passwordHelperCode = new PasswordHelperCode();
		passwordHelperCode.setUserId(user.getId());
		passwordHelperCode.setCreateTime(new Date());
		passwordHelperCode.setCode(code);
		passwordHelperCodeDAO.create(passwordHelperCode);
		
		return result;
	}
	
	@Transactional(readOnly = false)
	public StatusResult handleChangePassword(
			ChangePasswordForm changePasswordForm) {

		StatusResult result = new StatusResult(Constants.SUCCESS);

		// validate email
		User user = userDAO.findByEmail(changePasswordForm.getEmail());
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		// validate old password
		String hashedOldPassword;
		try {
			hashedOldPassword = SHA256Generator.hash(changePasswordForm
					.getOldpass() + user.getSalt());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}
		if (!hashedOldPassword.equalsIgnoreCase(user.getPassword())) {
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}

		// change to use new password
		String hashedNewPassword;
		try {
			hashedNewPassword = SHA256Generator.hash(changePasswordForm
					.getNewpass() + user.getSalt());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}

		user.setPassword(hashedNewPassword);
		userDAO.update(user);

		// update to apiservice
		AddorUpdateUserDTO dto = new AddorUpdateUserDTO();
		dto.setEmail(changePasswordForm.getEmail());
		dto.setSalt(user.getSalt());
		dto.setPassword(changePasswordForm.getNewpass());
		//one user might bind to multiple products
		List<String> products = subscriptionStatusDAO
				.findByUserId(user.getId());
		if (products == null || products.size() == 0) {
			throw new RuntimeException("Empty product list");
		}
		for (String product : products) {
			dto.setProductKey(product);
			apiService.updateUser(dto);
		}

		return result;
	}

	@Transactional(readOnly = false)
	public StatusResult handleChangePasswordWithCode(
			ChangePasswordWithCodeForm changePasswordWithCodeForm) {

		StatusResult result = new StatusResult(Constants.SUCCESS);
		
		User user = userDAO.findByEmail(changePasswordWithCodeForm.getEmail());
		if (user == null) {
			result.setStatus(Constants.EMAIL_INCORRECT);
			return result;
		}

		// validate email + password code
		PasswordHelperCode passwordHelperCode = passwordHelperCodeDAO.lookup(
				changePasswordWithCodeForm.getEmail(),
				changePasswordWithCodeForm.getCode());
		if (passwordHelperCode == null) {
			result.setStatus(Constants.INVALID_CHANGE_PASSWORD_CODE);
			return result;
		}

		// change to use new password
		String hashedNewPassword;
		try {
			hashedNewPassword = SHA256Generator.hash(changePasswordWithCodeForm
					.getNewpass() + user.getSalt());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			result.setStatus(Constants.GENERAL_FAILURE);
			return result;
		}

		user.setPassword(hashedNewPassword);
		userDAO.update(user);

		// update to apiservice
		AddorUpdateUserDTO dto = new AddorUpdateUserDTO();
		dto.setEmail(changePasswordWithCodeForm.getEmail());
		dto.setSalt(user.getSalt());
		dto.setPassword(changePasswordWithCodeForm.getNewpass());
		
		//one user might bind to multiple products
		List<String> products = subscriptionStatusDAO
				.findByUserId(user.getId());
		if (products == null || products.size() == 0) {
			throw new RuntimeException("Empty product list");
		}
		for (String product : products) {
			dto.setProductKey(product);
			apiService.updateUser(dto);
		}

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
		return user == null ? false : true;
	}

	@Transactional
	public void handleLeaveMessage(ContactForm form) {
		Message msg = new Message();
		msg.setCreatetime(new Date());
		msg.setName(StoreCharsetUtil.EncodeString(form.getName()));
		msg.setEmail(form.getEmail());
		msg.setQq(form.getQq());
		msg.setFixed(false);
		msg.setMessage(StoreCharsetUtil.EncodeString(form.getContent()));
		messageDAO.create(msg);
	}
	
	private static String GenerateChangePasswordCode(int codeLength) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < codeLength; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	public static void main(String[] args) {
//		ContactForm form = new ContactForm();
//		form.setName("sam");
//
//		UserServiceImpl service = new UserServiceImpl();
//		service.handleLeaveMessage(form);
		
		for(int i=0; i<10; i++) {
			System.out.println(GenerateChangePasswordCode(10));
		}

	}

}
