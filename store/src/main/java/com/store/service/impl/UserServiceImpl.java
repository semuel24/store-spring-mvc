package com.store.service.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.dao.UserDAO;
import com.store.entity.User;
import com.store.result.CreateUserResult;
import com.store.service.UserService;
import com.store.utils.Constants;
import com.store.utils.SHA256Generator;
import com.store.web.form.SignUpForm;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Transactional
	@Override
	public CreateUserResult createUser(SignUpForm form) {

		CreateUserResult result = new CreateUserResult(Constants.SUCCESS);

		try {
			User user = new User();
			String salt = UUID.randomUUID().toString();
			String hashedPassword = SHA256Generator.hash(form.getPassword()
					+ salt);

			user.setEmail(form.getEmail());
			user.setPassword(hashedPassword);
			user.setSalt(salt);
			user.setUsername(form.getUsername());
			user.setStatus(Constants.ENABLED);
			userDAO.create(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result.setStatus(Constants.GENERAL_FAILURE);
		}

		return result;
	}
}
