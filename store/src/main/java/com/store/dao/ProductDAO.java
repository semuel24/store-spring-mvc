package com.store.dao;

import com.store.entity.Product;

public interface ProductDAO extends StoreDAO<Product> {
	
	public Product findProductByKey(String productKey);
}
