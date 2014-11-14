package com.store.dao;

import com.store.entity.User;

public interface UserDAO {

	User findById(Long id);
}
