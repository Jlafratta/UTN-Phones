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
        SET prefixOrigin = (SELECT prefix from cities where NEW.pnumber_origin like concat(prefix, '%') order by length(prefix) desc limit 1);
        SET prefixDestination = (SELECT prefix from cities where NEW.pnumber_destination like concat(prefix, '%') order by length(prefix) desc limit 1);
        
		IF ISNULL(prefixOrigin) OR ISNULL(prefixDestination) THEN
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Prefix not found', MYSQL_ERRNO = 1;
        END IF;
        
        SET NEW.city_origin = (select id_city from cities where prefix like prefixOrigin);
        SET NEW.city_origin_name = (select city_name from cities where id_city = NEW.city_origin);
        SET NEW.city_destination = (select id_city from cities where prefix like prefixDestination);
        SET NEW.city_destination_name = (select city_name from cities where id_city = NEW.city_destination);
        SET NEW.pline_origin = (SELECT id_pline FROM phone_lines where phone_number like NEW.pnumber_origin AND state = 1);
        SET NEW.pline_destination = (SELECT id_pline FROM phone_lines where phone_number like NEW.pnumber_destination AND state = 1);
        
        IF ISNULL(NEW.pline_origin) OR ISNULL(NEW.pline_destination) THEN
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Phone number not found', MYSQL_ERRNO = 2;
        END IF;
        
        # Asigno los valores correspondientes a la tarifa
        SET NEW.tariff_key = concat(prefixOrigin, prefixDestination);
        SET NEW.cost = format((select cost from tariff where tariff_key = NEW.tariff_key),2);
		SET NEW.price = format((select price from tariff where tariff_key = NEW.tariff_key),2);
        SET NEW.total_cost = format((NEW.cost * (NEW.duration / 60)),2);
        SET NEW.total_price = format((NEW.price * (NEW.duration / 60)),2);
        
        # Seteo la bill en null, debido a que no esta facturado (se asigna en el sp de facturacion)
		SET NEW.id_bill = null;
END //
DELIMITER ;