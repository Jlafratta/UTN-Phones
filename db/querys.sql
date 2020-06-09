
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
order by call_date desc limit 1;