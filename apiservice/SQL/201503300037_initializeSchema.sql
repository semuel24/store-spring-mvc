CREATE TABLE `api_block_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `product` varchar(255) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `blockuntiltimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `block_user_email_index` (`email`),
  KEY `block_user_product_index` (`product`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `api_device_key_taken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceKey` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `api_device_key_taken_deviceKey_index` (`deviceKey`),
  KEY `api_device_key_taken_email_index` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `api_vpn_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productkey` varchar(45) NOT NULL,
  `ip` varchar(45) NOT NULL,
  `email` varchar(255) NULL,
  `createtime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `ip_UNIQUE` (`ip`),
  KEY `api_vpn_server_productkey_idx` (`productkey`),
  KEY `api_vpn_server_ip_idx` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `api_user_usage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `productkey` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `serviceStartTimestamp` bigint(20) NULL DEFAULT NULL,
  `period` varchar(20) NOT NULL,
  `currentCycleEndTimestamp` bigint(20) NULL DEFAULT NULL,
  `userUsageLimit` int(20) NOT NULL,
  `totalUsageofExpiredSessions` bigint(20) NULL DEFAULT NULL,
  `totalUsageofAllSessions` bigint(20) NULL DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `index5` (`email`,`productkey`),
  KEY `api_user_usage_email_idx` (`email`),
  KEY `api_user_usage_productkey_idx` (`productkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `api_session_usage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_usage_id` int(11) NOT NULL,
  `sessionid` bigint(20) NOT NULL,
  `usage` int(20) NOT NULL,
  `lastModifyTimestamp` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `api_session_usage_sessionid_idx` (`sessionid`),
  KEY `fk_user_usage_id_idx` (`user_usage_id`),
  CONSTRAINT `fk_user_usage_id` FOREIGN KEY (`user_usage_id`) REFERENCES `api_user_usage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


