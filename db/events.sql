	
    USE PhonesAPI;
    
DROP EVENT IF EXISTS facturation_event;
DELIMITER //
CREATE EVENT IF NOT EXISTS facturation_event
ON SCHEDULE EVERY 1 MONTH STARTS CAST(CONCAT( year(now()), '-', month(now()) , '-01') AS DATE) # Comienza a partir de principios del mes actual
ON COMPLETION PRESERVE
DO
BEGIN
	CALL sp_bills_facturation;
END //
DELIMITER ;