package com.store.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.dao.ProductDAO;
import com.store.entity.Product;

@Transactional
@Component("productDAO")
public class ProductDAOImpl extends StoreDAOImpl<Product> implements ProductDAO {

	public Product findProductByKey(String productKey) {
		Query query = factory.getCurrentSession().createQuery(
				" from Product as product where product.productkey = :productKey");
		query.setString("productKey", productKey);

		return (Product) query.uniqueResult();
	}
	
}
