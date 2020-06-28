
USE `PhonesAPI`;

-- ----------------

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

-- ----------------

INSERT INTO `cities` (prefix, city_name, id_province) values ("11","Buenos Aires", 1);		#1
INSERT INTO `cities` (prefix, city_name, id_province) values ("221","La Plata", 1);			#2
INSERT INTO `cities` (prefix, city_name, id_province) values ("223","Mar del Plata", 1);	#3
INSERT INTO `cities` (prefix, city_name, id_province) values ("249","Tandil", 1);			#4
INSERT INTO `cities` (prefix, city_name, id_province) values ("260","San Rafael", 12);		#5
INSERT INTO `cities` (prefix, city_name, id_province) values ("261","Mendoza", 12);			#6
INSERT INTO `cities` (prefix, city_name, id_province) values ("263","San Martin", 12);		#7

-- ----------------

INSERT INTO `user_profile` (name, lastname, dni) values ("Julian", "Lafratta", 41307551);
INSERT INTO `user_profile` (name, lastname, dni) values ("Nicolas", "Garcia", 40475927);
INSERT INTO `user_profile` (name, lastname, dni) values ("admin", "admin", 99999999);

-- ----------------

INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values ("LaGorrita", "asd123", false, 1, 3);
INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values ("NicoGarcia137", "asd123", false, 2, 7);
INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values ("admin", "admin", true, 3, 6);

-- ----------------

INSERT INTO `user_types` (type_name) values ("FIJO");
INSERT INTO `user_types` (type_name) values ("MOBIL");

-- ----------------

insert into `phone_lines` (phone_number, state, id_user, id_type) values ("2231111111", true, 1, 1);
INSERT INTO `phone_lines` (phone_number, state, id_user, id_type) values ("2602222222", true, 1, 1);
INSERT INTO `phone_lines` (phone_number, state, id_user, id_type) values ("2213333333", true, 2, 1);
INSERT INTO `phone_lines` (phone_number, state, id_user, id_type) values ("1144444444", true, 2, 1);

-- ----------------

INSERT INTO `tariff` (tariff_key, cost, price) values ("22311", 3,  5);
INSERT INTO `tariff` (tariff_key, cost, price) values ("223221", 4, 7);
INSERT INTO `tariff` (tariff_key, cost, price) values ("223260", 5, 8);
INSERT INTO `tariff` (tariff_key, cost, price) values ("223261", 5, 8);
INSERT INTO `tariff` (tariff_key, cost, price) values ("223223", 5, 8);

INSERT INTO `tariff` (tariff_key, cost, price) values ("26011", 6, 10);
INSERT INTO `tariff` (tariff_key, cost, price) values ("260221", 7, 12);
INSERT INTO `tariff` (tariff_key, cost, price) values ("260261", 8, 14);
INSERT INTO `tariff` (tariff_key, cost, price) values ("260223", 8, 14);
INSERT INTO `tariff` (tariff_key, cost, price) values ("260260", 8, 14);

INSERT INTO `tariff` (tariff_key, cost, price) values ("1111", 6, 10);
INSERT INTO `tariff` (tariff_key, cost, price) values ("11221", 7, 12);
INSERT INTO `tariff` (tariff_key, cost, price) values ("11261", 8, 14);
INSERT INTO `tariff` (tariff_key, cost, price) values ("11223", 8, 14);
INSERT INTO `tariff` (tariff_key, cost, price) values ("11260", 8, 14);

INSERT INTO `tariff` (tariff_key, cost, price) values ("22111", 6, 10);
INSERT INTO `tariff` (tariff_key, cost, price) values ("221221", 7, 12);
INSERT INTO `tariff` (tariff_key, cost, price) values ("221261", 8, 14);
INSERT INTO `tariff` (tariff_key, cost, price) values ("221223", 8, 14);
INSERT INTO `tariff` (tariff_key, cost, price) values ("221260", 8, 14);

-- ----------------
      
      
INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (200, '2020-06-16 15:00:00', "2231111111", "1144444444");
INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (120, '2020-06-16 10:00:00', "2231111111", "1144444444");

INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (200, '2020-06-16 15:00:00', "2231111111", "2213333333");
INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (120, '2020-06-16 10:00:00', "2231111111", "2213333333");

INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (200, '2020-06-16 15:00:00', "2231111111", "2602222222");
INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (120, '2020-06-16 10:00:00', "2231111111", "2602222222");

INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (200, '2020-06-16 15:00:00', "2602222222", "1144444444");
INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (120, '2020-06-16 10:00:00', "2602222222", "1144444444");
    
INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (200, '2020-06-16 15:00:00', "2602222222", "2213333333");
INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (120, '2020-06-16 10:00:00', "2602222222", "2213333333");
      
-- ----------------