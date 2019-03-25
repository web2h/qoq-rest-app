USE `qoq`;

CREATE TABLE `users` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(256) NOT NULL,
	`cell_hone_number` VARCHAR(16) DEFAULT NULL,
	`alias` VARCHAR(32) NOT NULL,	
	`token` VARCHAR(128) DEFAULT NULL,
	`preferred_language` VARCHAR(2) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `idx_unique_users_email` (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `listings` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(1) NOT NULL,
	`title` VARCHAR(64) NOT NULL,
	`key_admin` VARCHAR(8) NOT NULL,
	`key_public` VARCHAR(8) NOT NULL,
	`deadline` TIMESTAMP NOT NULL,	
	`precision` VARCHAR(512) DEFAULT NULL,
	`user_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `idx_unique_listings_key_admin` (key_admin),
	UNIQUE INDEX `idx_unique_listings_key_public` (key_public),
	CONSTRAINT `fk_listings_user_id_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
