package com.store.db.dao;

import com.store.entity.ApiBlockUser;

public interface BlockUserDAO extends StoreDAO<ApiBlockUser>{

	public void addBlockUser(String email, String productKey,
			Long blockUntilTimestamp);

	/**
	 * return true - given user is blocked
	 * 		  false - given user is not on the block list
	 */
	public Boolean verifyBlockUser(String email, String productKey);

}
