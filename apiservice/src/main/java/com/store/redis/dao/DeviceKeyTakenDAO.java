package com.store.redis.dao;

import java.util.UUID;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.store.redis.client.RedisClient;
import com.store.redis.model.DeviceTaken;

/**
 * stores which account(email) is using this device(deviceKey)
 * We suppose one device is ONLY allowed to be used by one account.
 * If one user uses a friend's device, this friend will not be able to 
 * use his own account again. In this case, this friend will need to contact
 * us to re-activate his account. On our side, we will review the utilization
 * history.
 * 
 * storage schema
 * --------------
 * key: deviceKey
 * value: email
 */
@Component("deviceKeyTakenDAO")
public class DeviceKeyTakenDAO {

	@Autowired
	@Qualifier("redisClient")
	private RedisClient redisClient;

	private static String DEVICEKEY_TAKEN_MAP_PREFIX = "/devicekey/taken/";

	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}

	public DeviceTaken findDeviceByDeviceKey(String deviceKey) {
		RBucket<DeviceTaken> bucket = redisClient.getRedisson().getBucket(
				normalizeDeviceKey(deviceKey));
		return bucket.get();
	}

	public void addDevice(String deviceKey, String email) {
		DeviceTaken device = new DeviceTaken();
		device.setDeviceKey(deviceKey);
		device.setEmail(email);

		RBucket<DeviceTaken> bucket = redisClient.getRedisson().getBucket(
				normalizeDeviceKey(deviceKey));
		bucket.set(device);
	}

	private String normalizeDeviceKey(String deviceKey) {
		if (deviceKey == null || deviceKey.equalsIgnoreCase("")) {
			throw new RuntimeException(
					"DeviceKeyTakenDAO.normalizeDeviceKey Invalid device");
		}

		return DEVICEKEY_TAKEN_MAP_PREFIX + deviceKey;
	}

	public static void main(String[] args) throws Exception {

		// get a redis connection
		RedisClient client = new RedisClient();
		Config config = new Config();
		config.useSingleServer().setAddress("127.0.0.1:6379");
		client.setRedisson(Redisson.create(config));
		
		DeviceKeyTakenDAO dao = new DeviceKeyTakenDAO();
		dao.setRedisClient(client);

		// can't find a device
		DeviceTaken missingDevice = dao.findDeviceByDeviceKey(UUID.randomUUID().toString());
		
		// insert a device
		String newDeviceKey = UUID.randomUUID().toString();
		dao.addDevice(newDeviceKey, "test@gmail.com");

		// find a device
		DeviceTaken foundDevice = dao.findDeviceByDeviceKey(newDeviceKey);
	}
}
