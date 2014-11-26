package com.store.dao;

import com.store.entity.Session;

public interface SessionDAO extends StoreDAO<Session>{

	Session findBySessionKey(String sessionkey);
	int invalidateSession(String sessionkey);
}
