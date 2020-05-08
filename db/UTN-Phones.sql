DROP DATABASE PhonesAPI;

CREATE DATABASE `PhonesAPI`;

USE `PhonesAPI`;

SHOW TABLES;

DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`(
	`id_province` INTEGER NOT NULL AUTO_INCREMENT,
    `province_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id_province`) 
);

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`(
	`id_city` INTEGER NOT NULL AUTO_INCREMENT,
    `prefix` VARCHAR(4) NOT NULL,
    `city_name` VARCHAR(20) NOT NULL,
    `id_province` INTEGER NOT NULL,
    PRIMARY KEY (`id_city`),
    CONSTRAINT `fk_id_province` FOREIGN KEY (`id_province`) REFERENCES `province` (`id_province`)
);

DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile`(
	`id_profile` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `lastname` VARCHAR(20),
    `dni` INTEGER NOT NULL,
    PRIMARY KEY (`id_profile`)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
	`id_user` INTEGER NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) UNIQUE NOT NULL,
	`password` VARCHAR(8) NOT NULL,
	`id_profile` INTEGER,
	`id_city` INTEGER,
	PRIMARY KEY (`id_user`),
	CONSTRAINT `fk_id_profile` FOREIGN KEY (`id_profile`) REFERENCES `user_profile` (`id_profile`),
    CONSTRAINT `fk_id_city` FOREIGN KEY (`id_city`) REFERENCES `city` (`id_city`)
);

DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type`(
	`id_type` INTEGER NOT NULL AUTO_INCREMENT,
    `type_name` VARCHAR(20),
    PRIMARY KEY (`id_type`)
);

DROP TABLE IF EXISTS `phone_line`;
CREATE TABLE `phone_line`(
	`id_pline` INTEGER NOT NULL AUTO_INCREMENT,
    `phone_number` VARCHAR(11) UNIQUE NOT NULL,
    `id_user` INTEGER NOT NULL,
    `id_type` INTEGER,
    PRIMARY KEY (`id_pline`),
    CONSTRAINT `fk_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
    CONSTRAINT `fk_id_type` FOREIGN KEY (`id_type`) REFERENCES `user_type` (`id_type`)
);

DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`(
	`id_bill` INTEGER NOT NULL AUTO_INCREMENT,
    `cost` INTEGER,
    `total` INTEGER,
    `bill_date` DATETIME,
    `expire_date` DATETIME,
    `calls_count` INTEGER,
    `id_pline` INTEGER NOT NULL,
    PRIMARY KEY (`id_bill`),
    CONSTRAINT `fk_id_pline` FOREIGN KEY (`id_pline`) REFERENCES `phone_line` (`id_pline`)
);

DROP TABLE IF EXISTS `tariff`;
CREATE TABLE `tariff`(
	`tariff_key` INTEGER NOT NULL,
    `value` INTEGER,
    PRIMARY KEY (`tariff_key`)
);

DROP DATABASE IF EXISTS `call`;
CREATE TABLE `call`(
	`id_call` INTEGER NOT NULL AUTO_INCREMENT,
    `duration` INTEGER NOT NULL,
    `cost` INTEGER NOT NULL,
    `total_price` INTEGER,
    `call_date` DATETIME,
    `pnumber_origin` VARCHAR(11) NOT NULL,
    `pnumber_destination` VARCHAR(11) NOT NULL,
    -- FK's --
    `pline_origin` INTEGER NOT NULL,
    `pline_destination` INTEGER NOT NULL,
    `city_origin` INTEGER NOT NULL,
    `city_destination` INTEGER NOT NULL,
    `id_bill` INTEGER,
    `tariff_key` INTEGER,
    PRIMARY KEY (`id_call`),
    CONSTRAINT `fk_pline_origin` FOREIGN KEY (`pline_origin`) REFERENCES `phone_line` (`id_pline`),
    CONSTRAINT `fk_pline_destination` FOREIGN KEY (`pline_destination`) REFERENCES `phone_line` (`id_pline`),
    CONSTRAINT `fk_city_origin` FOREIGN KEY (`city_origin`) REFERENCES `city` (`id_city`),
    CONSTRAINT `fk_city_destination` FOREIGN KEY (`city_destination`) REFERENCES `city` (`id_city`),
    CONSTRAINT `fk_id_bill` FOREIGN KEY (`id_bill`) REFERENCES `bill` (`id_bill`),
    CONSTRAINT `fk_tariff_key` FOREIGN KEY (`tariff_key`) REFERENCES `tariff` (`tariff_key`)
);