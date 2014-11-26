package com.store.service;

import com.store.result.CreateUserResult;
import com.store.web.form.SignUpForm;

public interface UserService {

	public CreateUserResult createUser(SignUpForm user);
}
