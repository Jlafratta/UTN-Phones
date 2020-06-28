
	# Stored procedure para la facturacion de todas las llamadas con id_bill = null

DROP PROCEDURE IF EXISTS sp_bills_facturation ;
DELIMITER //
CREATE PROCEDURE sp_bills_facturation()
BEGIN    
    DECLARE vIdPhoneLine INT;
    DECLARE vCallsCount INT DEFAULT 0;
    DECLARE vCost DOUBLE DEFAULT 0;
    DECLARE vPrice DOUBLE DEFAULT 0;
    DECLARE vFinished INT DEFAULT 0;
    DECLARE vIdBill INT;
    
    # Cursor que me trae todas las llamadas de cada phoneline
    DECLARE cur_calls_facturation CURSOR FOR 
    SELECT c.pline_origin as phoneLine, COUNT(c.id_call) as callsCount, SUM(c.total_cost) as cost, SUM(c.total_price) as price
    FROM calls as c
    INNER JOIN phone_lines as pl
    ON c.pline_origin = pl.id_pline
    WHERE c.id_bill IS NULL
    GROUP BY c.pline_origin;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = 1;

    OPEN cur_calls_facturation;
    # Traigo los datos del cursor por primera vez
    FETCH cur_calls_facturation INTO vIdPhoneLine, vCallsCount, vCost, vPrice;
    # Si hay datos, ejecuto el while
    SET autocommit = 0;
    WHILE (vFinished=0) DO
    START TRANSACTION;
        # Creo la bill con la data del cursor
        INSERT INTO `bills` (cost, price, bill_date, expire_date, calls_count, id_pline) values (vCost, vPrice, now(), date_add(bill_date, INTERVAL 15 DAY), vCallsCount, vIdPhoneLine);
        SET vIdBill = last_insert_id();
        SET foreign_key_checks = 0; # Habilito la modificacion de las fk
        # Updateo las llamadas que inserte en la bill, asignandoles el id de la bill a la que pertenecen
        UPDATE `calls` as c SET id_bill = vIdBill
        WHERE pline_origin = vIdPhoneLine
        AND c.id_bill IS NULL;
        SET foreign_key_checks = 1; # Deshabilito la modificacion de las fk
        
        # Traigo los datos para la proxima pasada del while, si no hay datos corta el while x el corte del cursor (vFinished = 1)
        FETCH cur_calls_facturation INTO vIdPhoneLine, vCallsCount, vCost, vPrice;
	COMMIT;
    END WHILE;
	SET autocommit = 1;
    # Cierro el cursor
    CLOSE cur_calls_facturation;
END //
DELIMITER ;