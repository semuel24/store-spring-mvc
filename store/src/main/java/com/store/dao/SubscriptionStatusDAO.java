package com.store.dao;

import java.util.List;
import com.store.entity.Product;
import com.store.entity.SubscriptionStatus;
import com.store.entity.User;

public interface SubscriptionStatusDAO extends StoreDAO<SubscriptionStatus> {

	public void save(User user, Product product);
	public void Update(SubscriptionStatus subscriptionStatus);
	public List<String> findByUserId(Integer userId);
}
