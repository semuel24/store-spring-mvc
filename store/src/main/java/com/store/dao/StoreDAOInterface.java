package com.store.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.exception.DBException;

public interface StoreDAOInterface<Entity> {

	@Autowired
	public abstract void setSessionFactory(SessionFactory factory);

	public abstract void create(Entity e);

	public abstract void update(Entity e);

	public abstract void delete(Entity e) throws DBException;
	
	public abstract void deleteFlush(Entity e) throws DBException;

	public abstract void merge(Entity e) throws DBException;

	public abstract SessionFactory getSessionFactory();
	
	void create(List<Entity> es);
	
	void update(List<Entity> es);
}
