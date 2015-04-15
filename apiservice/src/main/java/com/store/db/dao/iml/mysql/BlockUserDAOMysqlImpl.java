package com.store.db.dao.iml.mysql;

import java.util.Date;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.db.dao.BlockUserDAO;
import com.store.entity.ApiBlockUser;

@Transactional
@Component("blockUserDAO")
public class BlockUserDAOImpl extends StoreDAOImpl<ApiBlockUser> implements
		BlockUserDAO {

	public void addBlockUser(String email, String productKey,
			Long blockUntilTimestamp) {

		ApiBlockUser user = new ApiBlockUser();
		user.setBlockuntiltimestamp(new Date(blockUntilTimestamp));
		user.setCreatetime(new Date());
		user.setEmail(email);
		user.setProduct(productKey);
		this.create(user);
	}

	/**
	 * return true - given user is blocked false - given user is not on the
	 * block list
	 */
	public Boolean verifyBlockUser(String email, String productKey) {

		Query query = factory
				.getCurrentSession()
				.createQuery(
						" from ApiBlockUser as user where user.email = :email and user.product = :product ");
		query.setString("email", email);
		query.setString("product", productKey);

		Object o = query.uniqueResult();
		return o != null;
	}

}
