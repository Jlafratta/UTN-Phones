
USE `PhonesAPI`;

SHOW TABLES;

SELECT * FROM `province`;
SELECT * FROM `city`;
SELECT * FROM `user_profile`;
SELECT * FROM `user`;
select * from `user_type`;
select * from phone_line;

------------------

INSERT INTO `province` (province_name) values ("Buenos Aires");

------------------

INSERT INTO `city` (prefix, city_name, id_province) values ("223","Mar del Plata", 1);

------------------

INSERT INTO `user_profile` (name, lastname, dni) value ("Julian", "Lafratta", 41307551);

------------------

INSERT INTO `user` (username, password, id_profile, id_city) values ("LaGorrita", "asd123", 1, 1);

------------------

INSERT INTO `user_type` (type_name) values ("FIJO");
INSERT INTO `user_type` (type_name) values ("MOBIL");

------------------

insert into `phone_line` (phone_number, state, id_user, id_type) values ("2235997823", true, 1, 1);
