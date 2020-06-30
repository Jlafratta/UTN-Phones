
USE `PhonesAPI`;

-- ----------------

INSERT INTO `provinces` (province_name) values ("Buenos Aires"),	#1
("Catamarca"),		#2
 ("Chaco"),			#3
("Chubut"),			#4
("Cordoba"),		#5
("Corrientes"),		#6
 ("Entre Rios"),	#7
("Formosa"),		#8
("Jujuy"),			#9
("La Pampa"),		#10
("La Rioja"),		#11
("Mendoza"),		#12
("Misiones"),		#13
("Neuquen"),		#14
("Rio Negro"),		#15
("Salta"),			#16
("San Juan"),		#17
("San Luis"),		#18
("Santa Cruz"),		#19
("Santa Fe"),		#20
("Santiago del Estero"),	#21
("Tierra del Fuego"),		#22
("Tucuman");				#23

-- ----------------

INSERT INTO `cities` (prefix, city_name, id_province) values ("11","Buenos Aires", 1),		#1
("221","La Plata", 1),			#2
("223","Mar del Plata", 1),	#3
("249","Tandil", 1),			#4
("260","San Rafael", 12),		#5
("261","Mendoza", 12),			#6
("263","San Martin", 12);		#7

-- ----------------

INSERT INTO `user_profile` (name, lastname, dni) values 
("Julian", "Lafratta", 41307551), 
("Nicolas", "Garcia", 40475927), 
("admin", "admin", 99999999);

-- ----------------

INSERT INTO `users` (username, password, isEmployee, id_profile, id_city) values 
("JLafratta", "bfd59291e825b5f2bbf1eb76569f8fe7", false, 1, 3),
("NicoGarcia137", "bfd59291e825b5f2bbf1eb76569f8fe7", false, 2, 7),
("admin", "21232f297a57a5a743894a0e4a801fc3", true, 3, 6);

-- ----------------

INSERT INTO `user_types` (type_name) values ("FIJO"), ("MOBIL");

-- ----------------

insert into `phone_lines` (phone_number, state, id_user, id_type) values 
("2231111111", true, 1, 1),
("2602222222", true, 1, 1),
("2213333333", true, 2, 1),
("1144444444", true, 2, 1);

-- ----------------

INSERT INTO `tariff` (tariff_key, cost, price) values 
("22311", 3,  5),
("223221", 4, 7),
("223260", 5, 8),
("223261", 5, 8),
("223223", 5, 8);

INSERT INTO `tariff` (tariff_key, cost, price) values 
("26011", 6, 10),
("260221", 7, 12),
("260261", 8, 14),
("260223", 8, 14),
("260260", 8, 14);

INSERT INTO `tariff` (tariff_key, cost, price) values 
("1111", 6, 10),
("11221", 7, 12),
("11261", 8, 14),
("11223", 8, 14),
("11260", 8, 14);

INSERT INTO `tariff` (tariff_key, cost, price) values 
("22111", 6, 10),
("221221", 7, 12),
("221261", 8, 14),
("221223", 8, 14),
("221260", 8, 14);

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