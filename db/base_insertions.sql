
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

INSERT INTO `tariff` (tariff_key, value) values ("11223",  25);
INSERT INTO `tariff` (tariff_key, value) values ("223249", 11);

------------------

INSERT INTO `user_profile` (name, lastname, dni) values ("Julian", "Lafratta", 41307551);
INSERT INTO `user_profile` (name, lastname, dni) values ("Nicolas", "Garcia", 40475927);
INSERT INTO `user_profile` (name, lastname, dni) values ("admin", "admin", 99999999);
INSERT INTO `user_profile` (name, lastname, dni) values ("admin2", "admin2", 99999999);

------------------
INSERT INTO `users` (username, password, id_profile, id_city) values ("LaGorrita", "asd123", 1, 3);
INSERT INTO `users` (username, password, id_profile, id_city) values ("NicoGarcia137", "asd123", 2, 7);
INSERT INTO `users` (username, password, id_profile, id_city) values ("admin", "admin", 3, 6);
INSERT INTO `users` (username, password, id_profile, id_city) values ("admin2", "admin2", 4, 5);

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

INSERT INTO `tariff` (tariff_key, value) values ("223263",  0.25);
INSERT INTO `tariff` (tariff_key, value) values ("261260", 0.10);

------------------

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.25, 250, now(), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.10, 100, DATE_ADD(NOW(),INTERVAL 2 DAY), "2615890643", "2608746395", 3, 4, 6, 5, null, "261260");

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.25, 250, DATE_ADD(NOW(),INTERVAL 4 DAY), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.10, 100, DATE_ADD(NOW(),INTERVAL 6 DAY), "2615890643", "2608746395", 3, 4, 6, 5, null, "261260");

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.25, 250, DATE_ADD(NOW(),INTERVAL 8 DAY), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.10, 100, DATE_ADD(NOW(),INTERVAL 10 DAY), "2615890643", "2608746395", 3, 4, 6, 5, null, "261260");

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.25, 250, DATE_ADD(NOW(),INTERVAL 12 DAY), "2235997823", "2635872341", 1, 2, 3, 7, null, "223263");

INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination,
                    pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
                    VALUES (100, 0.25, 250, DATE_ADD(NOW(),INTERVAL 4 DAY), "2235252225", "2635872341", 5, 2, 3, 7, null, "223263");
                    
------------------

INSERT INTO `bills` (cost, total, bill_date, expire_date, calls_count, id_pline) values (1, 1000, now(), date_add(now(), INTERVAL 15 DAY), 4, 1);
INSERT INTO `bills` (cost, total, bill_date, expire_date, calls_count, id_pline) values (1, 250, now(), date_add(now(), INTERVAL 15 DAY), 1, 5);
INSERT INTO `bills` (cost, total, bill_date, expire_date, calls_count, id_pline) values (1, 750, now(), date_add(now(), INTERVAL 15 DAY), 1, 3);

select * from bills;




-- traer las llamadas de un usuario y una linea
SELECT c.id_call, c.call_date, c.pnumber_origin, c.pline_origin, u.id_user, u.username
FROM calls as c 
inner join phone_lines as pl 
on c.pline_origin = pl.id_pline
inner join users as u 
on u.id_user = 1
where c.call_date > now() AND c.call_date < DATE_ADD(NOW(),INTERVAL 8 DAY) AND c.pline_origin = 1;

-- traer las llamadas de un usuario (puede tener mas de una linea)
SELECT * from users as u
inner join phone_lines as pl
on u.id_user = pl.id_user
inner join calls as c
on c.pline_origin = pl.id_pline
where u.id_user = 1 AND c.call_date >= CAST('2020-06-03' AS DATE) AND c.call_date <= CAST('2020-06-12'AS DATE);

select * from calls;

-- traer las facturas de un usuario
SELECT * from users as u
inner join phone_lines as pl
on u.id_user = pl.id_user
inner join bills as b
on b.id_pline = pl.id_pline
where u.id_user = 1 AND b.bill_date >= CAST('2020-06-01' AS DATE) AND b.bill_date <= CAST('2020-06-12'AS DATE);

-- devuelve nombre y apellido de usuario, y total de duracion de las llamadas de un mes en particular
SELECT up.name as Nombre, up.lastname as Apellido, sum(c.duration) as Duracion_total
from user_profile as up
inner join users as u
on u.id_profile = up.id_profile
inner join phone_lines as pl
on u.id_user = pl.id_user
inner join calls as c
on c.pline_origin = pl.id_pline
where u.id_user = 1 AND c.call_date >= CAST('2020-06-01' AS DATE) AND c.call_date <= DATE_ADD( CAST('2020-06-01'AS DATE), INTERVAL 1 MONTH );


select CAST(concat( (select(year(now()))) , '-05-01') AS DATE);

select date_add( (select CAST(concat( (select(year(now()))) , '-05-01') AS DATE)) , INTERVAL 1 MONTH );

select year(now());

select * from tariff as t
WHERE tariff_key like '22%';

-- traer cantidad de llamadas de un usuario
SELECT up.name as Nombre, up.lastname as Apellido, count(c.id_call) as Cant_Llamadas
from user_profile as up
inner join users as u
on u.id_profile = up.id_profile
inner join phone_lines as pl
on u.id_user = pl.id_user
inner join calls as c
on c.pline_origin = pl.id_pline
where u.id_user = 1 ;

select * from calls;

 -- traer la ultima llamada de un usuario
SELECT * FROM calls as c
inner join phone_lines as pl
on c.pline_origin = pl.id_pline
inner join users as u
on pl.id_user = u.id_user
where u.id_user = 1
order by call_date desc limit 1


