ALTER TABLE `user` 
DROP COLUMN `username`,
CHANGE COLUMN `status` `status` INT(11) NULL ,
DROP INDEX `username_UNIQUE` ;


ALTER TABLE `user` 
add COLUMN `name` varchar(255) null;


CREATE TABLE `freeserver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) NOT NULL,
  `lastmodifytime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
