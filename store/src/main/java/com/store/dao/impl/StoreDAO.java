package com.store.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.store.dao.StoreDAOInterface;
import com.store.exception.DBException;

@Transactional
public class StoreDAO<Entity> implements StoreDAOInterface<Entity> {

	protected SessionFactory factory;
	
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@Override
	@Transactional(readOnly = false)
	public void create(Entity e) {
		this.factory.getCurrentSession().saveOrUpdate(e);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Entity e) {
		this.factory.getCurrentSession().saveOrUpdate(e);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Entity e) throws DBException {
		try {
			this.factory.getCurrentSession().delete(e);
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new DBException(re.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DBException(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteFlush(Entity e) throws DBException {
		try {
			this.factory.getCurrentSession().delete(e);
			this.factory.getCurrentSession().flush();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new DBException(re.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DBException(ex.getMessage());
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void merge(Entity e) throws DBException {
		try {
			this.factory.getCurrentSession().merge(e);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DBException(ex.getMessage());
		}
	}

	/*
	 * A size of zero means return all records
	 */
	public static void setPaginationParams(Query query, int start, int size) {
		query.setFirstResult(start);
		if (size > 0) {
			query.setMaxResults(size);
		}
	}

	/*
	 * A size of zero means return all records
	 */
	public static void setPaginationParams(Criteria c, int start, int size) {
		c.setFirstResult(start);
		if (size > 0) {
			c.setMaxResults(size);
		}
	}

	public SessionFactory getSessionFactory() {
		return factory;
	}

	@Override
	public void create(List<Entity> es) {
		if(es != null) {
			Session session = factory.getCurrentSession();
			for(Entity e : es) {
				session.saveOrUpdate(e);
				//session.flush();
			}
		}
	}

	@Override
	public void update(List<Entity> es) {
		if(es != null) {
			Session session = factory.getCurrentSession();
			for(Entity e : es) {
				session.update(e);
				//session.flush();
			}
		}
	}
}
