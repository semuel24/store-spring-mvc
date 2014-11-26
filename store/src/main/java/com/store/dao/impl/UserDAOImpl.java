package com.store.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.store.dao.UserDAO;
import com.store.dao.impl.StoreDAOImpl;
import com.store.entity.User;

@Repository("userDAOImpl")
public class UserDAOImpl extends StoreDAOImpl<User> implements UserDAO {

	@Transactional(readOnly = true)
	@Override
	public User findById(Long id) {
		Query query = factory.getCurrentSession().createQuery(
				" from User as user where user.id = :id");
		query.setLong("id", id);

		return (User) query.uniqueResult();
	}
	
	@Transactional(readOnly = true)
	@Override
	public User findByUsername(String username) {
		Query query = factory.getCurrentSession().createQuery(
				" from User as user where user.username = :username");
		query.setString("username", username);

		return (User) query.uniqueResult();
	}
	
	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		Query query = factory.getCurrentSession().createQuery(
				" from User as user where user.email = :email");
		query.setString("email", email);

		return (User) query.uniqueResult();
	}
	
	@Transactional(readOnly = true)
	@Override
	public User findByUsernameOrEmail(String username, String email) {
		Query query = factory.getCurrentSession().createQuery(
				" from User as user where user.username = :username or user.email = :email");
		
		query.setString("username", username);
		query.setString("email", email);

		return (User) query.uniqueResult();
	}
	
}
