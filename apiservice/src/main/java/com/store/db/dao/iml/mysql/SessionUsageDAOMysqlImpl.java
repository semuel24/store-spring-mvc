package com.store.db.dao.iml.mysql;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.store.db.dao.mysql.SessionUsageDAO;
import com.store.entity.ApiSessionUsage;

@Transactional
@Component("sessionUsageDAOMysql")
public class SessionUsageDAOMysqlImpl extends StoreDAOMysqlImpl<ApiSessionUsage>
		implements SessionUsageDAO {

	@SuppressWarnings("unchecked")
	public List<ApiSessionUsage> findUsageSessions(Integer userUsageId) {
		Query query = factory
				.getCurrentSession()
				.createSQLQuery(
						" select * from api_session_usage as sessions where sessions.user_usage_id = :userUsageId ")
				.addEntity(ApiSessionUsage.class);
		query.setInteger("userUsageId", userUsageId);

		return (List<ApiSessionUsage>) query.list();
	}

	public ApiSessionUsage findSessionBySessionId(Long sessionId) {
		Query query = factory
				.getCurrentSession()
				.createQuery(
						" from ApiSessionUsage as sessions where sessions.sessionId = :sessionId ");
		query.setLong("sessionId", sessionId);

		return (ApiSessionUsage) query.uniqueResult();
	}
	
	public void deleteById(Long sessionId) {
		Query query = factory
				.getCurrentSession()
				.createSQLQuery(
						" delete from api_session_usage where sessionid = :sessionId ");
		query.setLong("sessionId", sessionId);
		query.executeUpdate();
	}
}
