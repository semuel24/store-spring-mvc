package com.store.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.store.dao.SessionDAO;
import com.store.entity.Session;

//@Repository("sessionDAOImpl")
public class SessionDAOImpl extends StoreDAOImpl<Session> implements SessionDAO {

//	@Override
//	public Session findBySessionKey(String sessionkey) {
//		Query query = factory.getCurrentSession().createQuery(
//				" from Session as session where session.sessionkey = :sessionkey");
//		query.setString("sessionkey", sessionkey);
//
//		return (Session) query.uniqueResult();
//	}
//
//	@Override
//	public int invalidateSession(String sessionkey) {
//		Query query = factory.getCurrentSession().createSQLQuery(
//				" delete from session where sessionkey=:sessionkey ");
//		query.setString("sessionkey", sessionkey);
//
//		return query.executeUpdate();	
//	}
	
}
