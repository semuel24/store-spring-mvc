package com.store.db.dao;

import com.store.entity.ApiUserUsage;
import com.store.redis.model.VpnUser;

public interface UserUsageDAO extends StoreDAO<ApiUserUsage>{

	public void createUser(VpnUser redisUser);
	public void updateUser(VpnUser redisUser);

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser);

	public VpnUser findUserByKey(String email, String productKey);

}
