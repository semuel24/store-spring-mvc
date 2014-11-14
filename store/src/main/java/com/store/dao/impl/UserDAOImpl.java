package com.store.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.store.dao.UserDAO;
import com.store.dao.impl.StoreDAO;
import com.store.entity.User;

@Repository("userDAOImpl")
public class UserDAOImpl extends StoreDAO<User> implements UserDAO {

	@Transactional(readOnly = true)
	@Override
	public User findById(Long id) {
		Query query = factory.getCurrentSession().createQuery(
				" from User as user where user.Id = :id");
		query.setLong("id", id);

		return (User) query.uniqueResult();
	}

}
