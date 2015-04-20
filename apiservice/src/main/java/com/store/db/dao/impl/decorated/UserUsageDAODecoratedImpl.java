package com.store.db.dao.impl.decorated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.db.dao.api.UserUsageDAO;
import com.store.exception.DBException;
import com.store.redis.model.VpnUser;

@Transactional
@Component("userUsageDAODecorated")
public class UserUsageDAODecoratedImpl implements UserUsageDAO {

	@Autowired
	@Qualifier("userUsageDAOMysql")
	private UserUsageDAO userUsageDAO;

	public void createUser(VpnUser redisUser) {
		userUsageDAO.createUser(redisUser);
	}

	public void updateUser(VpnUser redisUser) throws DBException {
		userUsageDAO.updateUser(redisUser);
	}

	/**
	 * only needs email and productKey
	 */
	public void deleteUser(VpnUser redisUser) {
		throw new UnsupportedOperationException(
				"UserUsageDAODecoratedImpl.deleteUser()");
	}

	public VpnUser findUserByKey(String email, String productKey) {
		return userUsageDAO.findUserByKey(email, productKey);
	}

}
