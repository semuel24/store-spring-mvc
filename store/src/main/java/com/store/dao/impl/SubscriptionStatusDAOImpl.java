package com.store.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
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
	
	@SuppressWarnings("unchecked")
	public List<String> findByUserId(Integer userId) {

		Query query = factory
				.getCurrentSession()
				.createSQLQuery(
						" select p.* from subscription_status as ss "
						+ " inner join product as p on ss.product_id=p.id where user_id =:user_id ")
				.setResultTransformer(Transformers.aliasToBean(Product.class));
		query.setInteger("user_id", userId);

		List<Product> list = query.list();
		List<String> productkeys = new ArrayList<String>();
		for(Product _product : list) {
			productkeys.add(_product.getProductkey());
		}
		return productkeys;
	}
}
