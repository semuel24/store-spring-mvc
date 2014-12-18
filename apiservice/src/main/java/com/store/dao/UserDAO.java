package com.store.dao;

import com.store.entity.User;

public interface UserDAO extends StoreDAO<User> {

	User findById(Long id);
	User findByUsername(String username);
	User findByEmail(String email);
	User findByUsernameOrEmail(String username, String email);
}
