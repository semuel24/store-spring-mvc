package com.store.service.impl;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.dao.SessionDAO;
import com.store.dao.UserDAO;
import com.store.entity.Session;
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

	@Autowired
	private SessionDAO sessionDAO;

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

		Session session = new Session();
		String sessionkey = UUID.randomUUID().toString();
		session.setSessionkey(sessionkey);
		session.setTimeout(System.currentTimeMillis()
				+ Constants.DEFAULT_SESSION_TIMEOUT);
		session.setUser(user);
		sessionDAO.create(session);

		result.setSessionkey(sessionkey);

		return result;
	}
}
