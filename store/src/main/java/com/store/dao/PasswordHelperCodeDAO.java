package com.store.dao;

import com.store.entity.PasswordHelperCode;

public interface PasswordHelperCodeDAO extends StoreDAO<PasswordHelperCode> {

	public PasswordHelperCode lookup(String email, String code);
}
