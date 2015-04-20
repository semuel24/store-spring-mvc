package com.store.db.dao.iml.mysql;

import java.util.Date;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.db.dao.api.BlockUserDAO;
import com.store.entity.ApiBlockUser;
import com.store.utils.TimeUtil;

@Transactional
@Component("blockUserDAOMysql")
public class BlockUserDAOMysqlImpl extends StoreDAOMysqlImpl<ApiBlockUser> implements
		BlockUserDAO {
	
	@Autowired
	private TimeUtil timeUtil;

	public void addBlockUser(String email, String productKey,
			Long blockUntilTimestamp) {

		if(verifyBlockUserByTime(email, productKey)) {
			return;
		}
		
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
	public Boolean verifyBlockUserByTime(String email, String productKey ) {

		Query query = factory
				.getCurrentSession()
				.createQuery(
						" from ApiBlockUser as user where user.email = :email and user.product = :product and user.blockuntiltimestamp> :currentTime");
		query.setString("email", email);
		query.setString("product", productKey);
		query.setTimestamp("currentTime", timeUtil.getCurrentDate());
		
		Object o = query.uniqueResult();
		return o != null;
	}

}
