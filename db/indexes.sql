
	CREATE INDEX idx_calls_date ON calls(call_date) USING BTREE;	-- Rango de fechas entre llamadas
	CREATE INDEX idx_bills_date ON bills(bill_date) USING BTREE;	-- Rango de fechas entre facturas
    CREATE INDEX idx_pline_origin ON calls(pline_origin) USING BTREE; -- Ordenado para top 10

explain extended SELECT * FROM users as u
INNER JOIN phone_lines as pl 
ON u.id_user = pl.id_user 
INNER JOIN calls as c 
ON c.pline_origin = pl.id_pline 
WHERE u.id_user = 1
AND c.call_date between "2020-06-10 000000" AND "2020-07-01 000000";

explain extended SELECT * from users as u 
	 INNER JOIN phone_lines as pl 
	 ON u.id_user = pl.id_user 
	 INNER JOIN calls as c 
	 ON c.pline_origin = pl.id_pline 
	 WHERE u.id_user = 1
	 GROUP BY c.pline_destination 
	 ORDER BY count(c.pline_destination) desc 
	LIMIT 10;

explain extended select pnumber_origin, city_origin, pnumber_destination, city_destination, total_price, duration, call_date 
from calls where call_date between cast("2020-06-28" as date) AND cast("2020-07-01" as date);





DROP PROCEDURE IF EXISTS sp_insert_calls ;
DELIMITER //
CREATE PROCEDURE sp_insert_calls()
BEGIN 
	DECLARE i int default 0;
    DECLARE vDate datetime default now();
    DECLARE vOrigin int default 2230000000;
    DECLARE vDest int default 2210000000;
    
    WHILE(i < 1000) DO

        SET vDate = date_add(vDate, interval 1 minute);
		
		insert into `calls` (duration, cost, total_cost, price, total_price, call_date, pnumber_origin, pnumber_destination, pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)
		values (1 ,1.0, 1.0, 1.0, 1.0, vDate, 2230000000, 2210000000, 1, 2, 1, 2, null, 223221);
 
        SET i = i + 1;
        
    END WHILE;
	
END //
DELIMITER ;

CALL sp_insert_calls;

SELECT table_schema AS "Database",
ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS "Size (MB)"
FROM information_schema.TABLES
GROUP BY table_schema;