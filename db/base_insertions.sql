
USE `PhonesAPI`;

SHOW TABLES;

SELECT * FROM `provinces`;
SELECT * FROM `cities`;
SELECT * FROM `user_profile`;
SELECT * FROM `users`;
select * from `user_types`;
select * from `phone_lines`;
select * from `tariff`;
select * from `calls`;
select * from `bills`;

------------------

INSERT INTO `provinces` (province_name) values ("Buenos Aires");	#1
INSERT INTO `provinces` (province_name) values ("Catamarca");		#2
INSERT INTO `provinces` (province_name) values ("Chaco");			#3
INSERT INTO `provinces` (province_name) values ("Chubut");			#4
INSERT INTO `provinces` (province_name) values ("Cordoba");			#5
INSERT INTO `provinces` (province_name) values ("Corrientes");		#6
INSERT INTO `provinces` (province_name) values ("Entre Rios");		#7
INSERT INTO `provinces` (province_name) values ("Formosa");			#8
INSERT INTO `provinces` (province_name) values ("Jujuy");			#9
INSERT INTO `provinces` (province_name) values ("La Pampa");		#10
INSERT INTO `provinces` (province_name) values ("La Rioja");		#11
INSERT INTO `provinces` (province_name) values ("Mendoza");			#12
INSERT INTO `provinces` (province_name) values ("Misiones");		#13
INSERT INTO `provinces` (province_name) values ("Neuquen");			#14
INSERT INTO `provinces` (province_name) values ("Rio Negro");		#15
INSERT INTO `provinces` (province_name) values ("Salta");			#16
INSERT INTO `provinces` (province_name) values ("San Juan");		#17
INSERT INTO `provinces` (province_name) values ("San Luis");		#18
INSERT INTO `provinces` (province_name) values ("Santa Cruz");		#19
INSERT INTO `provinces` (province_name) values ("Santa Fe");		#20
INSERT INTO `provinces` (province_name) values ("Santiago del Estero");		#21
INSERT INTO `provinces` (province_name) values ("Tierra del Fuego");		#22
INSERT INTO `provinces` (province_name) values ("Tucuman");					#23

------------------

INSERT INTO `cities` (prefix, city_name, id_province) values ("11","Buenos Aires", 1);		#1
INSERT INTO `cities` (prefix, city_name, id_province) values ("221","La Plata", 1);			#2
INSERT INTO `cities` (prefix, city_name, id_province) values ("223","Mar del Plata", 1);	#3
INSERT INTO `cities` (prefix, city_name, id_province) values ("249","Tandil", 1);			#4
INSERT INTO `cities` (prefix, city_name, id_province) values ("260","San Rafael", 12);		#5
INSERT INTO `cities` (prefix, city_name, id_province) values ("261","Mendoza", 12);			#6
INSERT INTO `cities` (prefix, city_name, id_province) values ("263","San Martin", 12);		#7


------------------

INSERT INTO `tariff` (tariff_key, cost, price) values ("11223", 10,  25);
INSERT INTO `tariff` (tariff_key, cost, price) values ("223249", 4, 11);

------------------

INSERT INTO `user_profile` (name, lastname, dni) values ("Julian", "Lafratta", 41307551);
INSERT INTO `user_profile` (name, lastname, dni) values ("Nicolas", "Garcia", 40475927);
INSERT INTO `user_profile` (name, lastname, dni) values ("admin", "admin", 99999999);
INSERT INTO `user_profile` (name, lastname, dni) values ("admin2", "admin2", 99999999);

------------------
INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values ("LaGorrita", "asd123", true, 1, 3);
INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values ("NicoGarcia137", "asd123", false, 2, 7);
INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values ("admin", "admin", true, 3, 6);
INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values ("admin2", "admin2", true, 4, 5);

------------------

INSERT INTO `user_types` (type_name) values ("FIJO");
INSERT INTO `user_types` (type_name) values ("MOBIL");

------------------

insert into `phone_lines` (phone_number, state, id_user, id_type) values ("2235997823", true, 1, 1);
INSERT INTO `phone_lines` (phone_number, state, id_user, id_type) values ("2635872341", true, 2, 2);
INSERT INTO `phone_lines` (phone_number, state, id_user, id_type) values ("2615890643", true, 3, 1);
INSERT INTO `phone_lines` (phone_number, state, id_user, id_type) values ("2608746395", true, 4, 2);

-- inserto otra phoneline a un mismo usuario
insert into `phone_lines` (phone_number, state, id_user, id_type) values ("2235252225", true, 1, 1);

------------------

INSERT INTO `tariff` (tariff_key, cost, price) values ("223263",  2, 5);
INSERT INTO `tariff` (tariff_key, cost, price) values ("261260", 5, 8);

------------------

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 2, 4, 25, 50, now(), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 5, 10, 50, 100, DATE_ADD(NOW(),INTERVAL 2 DAY), "2615890643", "2608746395", 3, 4, 6, 5, null, "261260");

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 2, 4, 25, 50, DATE_ADD(NOW(),INTERVAL 4 DAY), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 5, 10, 50, 100, DATE_ADD(NOW(),INTERVAL 6 DAY), "2615890643", "2608746395", 3, 4, 6, 5, null, "261260");

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 2, 4, 100, 200, DATE_ADD(NOW(),INTERVAL 8 DAY), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 5, 10, 50, 100, DATE_ADD(NOW(),INTERVAL 10 DAY), "2615890643", "2608746395", 3, 4, 6, 5, null, "261260");

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 2, 4, 150, 300, DATE_ADD(NOW(),INTERVAL 12 DAY), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 7, 14, 75, 150, DATE_ADD(NOW(),INTERVAL 4 DAY), "2235252225", "2635872341", 5, 2, 3, 7, null, "223263");
                    
------------------

INSERT INTO `bills` (cost, price, bill_date, expire_date, calls_count, id_pline) values (1.2, 1000, now(), date_add(bill_date, INTERVAL 15 DAY), 4, 1);
INSERT INTO `bills` (cost, price, bill_date, expire_date, calls_count, id_pline) values (1.7, 250, now(), date_add(now(), INTERVAL 15 DAY), 1, 5);
INSERT INTO `bills` (cost, price, bill_date, expire_date, calls_count, id_pline) values (1.15, 750, now(), date_add(now(), INTERVAL 15 DAY), 1, 3);


SET foreign_key_checks = 0;
UPDATE `calls` SET id_bill = 10
        WHERE pline_origin = 1;
SET foreign_key_checks = 1;

select * from bills;
select * from calls order by pline_origin;





