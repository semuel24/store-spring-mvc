package com.store.db.dao.iml.mysql;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.db.dao.SessionUsageDAO;
import com.store.entity.ApiSessionUsage;

@Transactional
@Component("sessionUsageDAO")
public class SessionUsageDAOImpl extends StoreDAOImpl<ApiSessionUsage>
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

	@SuppressWarnings("unchecked")
	public ApiSessionUsage findSessionBySessionId(Long sessionId) {
		Query query = factory
				.getCurrentSession()
				.createQuery(
						" from ApiSessionUsage as sessions where sessions.sessionId = :sessionId ");
		query.setLong("sessionId", sessionId);

		return (ApiSessionUsage) query.uniqueResult();
	}
}
