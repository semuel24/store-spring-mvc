package com.store.db.dao;

import com.store.entity.UserUsage;
import com.store.redis.model.VpnUser;

public interface UserUsageDAO extends StoreDAO<UserUsage>{

	public void saveOrUpdateUser(VpnUser redisUser);

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser);

	public VpnUser findUserByKey(String email, String productKey);

}
