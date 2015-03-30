package com.store.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.dao.PasswordHelperCodeDAO;
import com.store.entity.PasswordHelperCode;

@Transactional
@Component("passwordHelperCodeDAO")
public class PasswordHelperCodeDAOImpl extends StoreDAOImpl<PasswordHelperCode>
		implements PasswordHelperCodeDAO {

	@Override
	public PasswordHelperCode lookup(String email, String code) {
		Query query = factory.getCurrentSession().createSQLQuery(
				" select password_helper_code.* from user inner join password_helper_code "
				+ " on user.id = password_helper_code.user_id "
				+ " where user.email=:email and password_helper_code.code=:code"
				+ " order by password_helper_code.id desc limit 0,1 ").addEntity(PasswordHelperCode.class);
		query.setString("email", email);
		query.setString("code", code);

		return (PasswordHelperCode) query.uniqueResult();
	}
}
