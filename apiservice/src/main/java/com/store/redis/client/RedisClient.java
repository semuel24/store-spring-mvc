package com.store.redis.client;

import org.redisson.Config;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//http://redis.io/topics/quickstart
//http://redis.io/topics/persistence
//https://www.linode.com/docs/databases/redis/redis-on-ubuntu-12-04-precise-pangolin
//refer to https://github.com/mrniko/redisson/wiki/Usage-examples
/**
 * Precondition:
 * 1. mysql still has all data, except user usage data for usage sensitive products  
 */
@Component
public class RedisClient implements InitializingBean, DisposableBean {
	
	private static final Logger logger = LoggerFactory
			.getLogger(RedisClient.class);

	private Redisson redisson;
	
	@Autowired
	@Qualifier("redisServerAddress")
	private String redisServerAddress;

	public void afterPropertiesSet() {
		
		if(logger.isInfoEnabled()) {
			logger.info("start redis server address:" + redisServerAddress);
		}
		
		Config config = new Config();
		config.useSingleServer().setAddress(redisServerAddress);
//		config.useSingleServer().setAddress("127.0.0.1:6379");
		redisson = Redisson.create(config);
	}

	public void destroy() throws Exception {
		if(logger.isInfoEnabled()) {
			logger.info("stop redis server");
		}
		redisson.shutdown();
	}

	public Redisson getRedisson() {
		return redisson;
	}

	public void setRedisson(Redisson redisson) {
		this.redisson = redisson;
	}
	
}
