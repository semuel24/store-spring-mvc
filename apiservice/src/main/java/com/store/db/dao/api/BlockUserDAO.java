package com.store.db.dao.api;

public interface BlockUserDAO{

	public void addBlockUser(String email, String productKey,
			Long blockUntilTimestamp);

	/**
	 * return true - given user is blocked
	 * 		  false - given user is not on the block list
	 */
	public Boolean verifyBlockUserByTime(String email, String productKey);

}
