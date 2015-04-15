package com.store.db.dao;

import java.util.List;
import com.store.entity.ApiSessionUsage;

public interface SessionUsageDAO extends StoreDAO<ApiSessionUsage>{

	public List<ApiSessionUsage> findUsageSessions(Integer userUsageId);
	public ApiSessionUsage findSessionBySessionId(Long sessionId);
}
