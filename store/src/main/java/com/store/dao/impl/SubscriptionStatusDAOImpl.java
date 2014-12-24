package com.store.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.dao.SubscriptionStatusDAO;
import com.store.entity.Product;
import com.store.entity.SubscriptionStatus;
import com.store.entity.User;

@Transactional
@Component("subscriptionStatusDAO")
public class SubscriptionStatusDAOImpl extends StoreDAOImpl<SubscriptionStatus>
		implements SubscriptionStatusDAO {

	public void save(User user, Product product) {
		SubscriptionStatus ss = new SubscriptionStatus();
		ss.setBalance(0);
		ss.setProduct(product);
		ss.setSubscriptionStatus(true);
		ss.setUsage(0L);
		ss.setUser(user);
		this.create(ss);
	}

	public void Update(SubscriptionStatus subscriptionStatus) {
		throw new UnsupportedOperationException(
				"SubscriptionStatusDAOImpl Update");
	}
}
