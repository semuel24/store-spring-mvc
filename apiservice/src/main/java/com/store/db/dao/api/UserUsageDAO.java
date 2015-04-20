package com.store.db.dao.api;

import com.store.exception.DBException;
import com.store.redis.model.VpnUser;

public interface UserUsageDAO{

	public void createUser(VpnUser redisUser);
	public void updateUser(VpnUser redisUser) throws DBException;

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser);

	public VpnUser findUserByKey(String email, String productKey);

}
