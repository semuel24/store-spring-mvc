package com.store.db.dao.impl.decorated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.db.dao.api.BlockUserDAO;

@Transactional
@Component("blockUserDAODecorated")
public class BlockUserDAODecoratedImpl implements BlockUserDAO {

	@Autowired
	@Qualifier("blockUserDAOMysql")
	private BlockUserDAO blockUserDAO;

	public void addBlockUser(String email, String productKey,
			Long blockUntilTimestamp) {
		blockUserDAO.addBlockUser(email, productKey, blockUntilTimestamp);
	}

	/**
	 * return true - given user is blocked false - given user is not on the
	 * block list
	 */
	public Boolean verifyBlockUserByTime(String email, String productKey) {
		return blockUserDAO.verifyBlockUserByTime(email, productKey);
	}

}
