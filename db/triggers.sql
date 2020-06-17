DROP TRIGGER IF EXISTS TIB_calls;
DELIMITER //
CREATE TRIGGER TIB_calls BEFORE INSERT ON `calls` FOR EACH ROW
BEGIN
    # EN LA INSERCION ME TRAEN :
	-- nro origen
    -- nro destino
	-- duracion
    -- fecha y hora

		# Traigo los valores de los prefijos
		DECLARE prefixOrigin varchar(4);
        DECLARE prefixDestination varchar(4);
        SET prefixOrigin = (SELECT prefix from cities where NEW.pnumber_origin like concat(prefix, '%') order by prefix desc limit 1);
        SET prefixDestination = (SELECT prefix from cities where NEW.pnumber_destination like concat(prefix, '%') order by prefix desc limit 1);
        
		IF ISNULL(prefixOrigin) OR ISNULL(prefixDestination) THEN
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Prefix not found', MYSQL_ERRNO = 1;
        END IF;
        
        SET NEW.city_origin = (select id_city from cities where prefix like prefixOrigin);
        SET NEW.city_destination = (select id_city from cities where prefix like prefixDestination);
        SET NEW.pline_origin = (SELECT id_pline FROM phone_lines where phone_number like NEW.pnumber_origin);
        SET NEW.pline_destination = (SELECT id_pline FROM phone_lines where phone_number like NEW.pnumber_destination);
        
        IF ISNULL(NEW.pline_origin) OR ISNULL(NEW.pline_destination) THEN
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Phone number not found', MYSQL_ERRNO = 2;
        END IF;
        
        # Asigno los valores correspondientes a la tarifa
        SET NEW.tariff_key = concat(prefixOrigin, prefixDestination);
        SET NEW.cost = (select cost from tariff where tariff_key = NEW.tariff_key);
		SET NEW.price = (select price from tariff where tariff_key = NEW.tariff_key);
        SET NEW.total_cost = NEW.cost * (NEW.duration / 60);
        SET NEW.total_price = NEW.price * (NEW.duration / 60);
        
        # Seteo la bill en null, debido a que no esta facturado (se asigna en el sp de facturacion)
		SET NEW.id_bill = null;
END //
DELIMITER ;



INSERT INTO `calls` (duration, call_date, pnumber_origin, pnumber_destination)
	VALUES (120, '2020-06-16 01:00:00', "2235252225", "2635872342");
    
select * from calls;

select * from cities;
insert into cities (prefix, city_name, id_province) values (2232, "mardel3", 1);

SELECT prefix from cities where 2235297823 like concat(prefix, '%') order by prefix desc limit 1;
