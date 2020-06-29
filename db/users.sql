
CREATE USER  'client'@'localhost' IDENTIFIED BY '1234';
GRANT SELECT on PhonesApi.users TO 'client'@'localhost'; 
GRANT SELECT on PhonesApi.phone_lines TO 'client'@'localhost'; 
GRANT SELECT on PhonesApi.calls TO 'client'@'localhost'; 
GRANT SELECT on PhonesApi.bills TO 'client'@'localhost'; 

CREATE USER 'backoffice'@'localhost' IDENTIFIED BY '4321';
GRANT SELECT,INSERT,UPDATE,DELETE
ON PhonesApi.users TO 'backoffice'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE
ON PhonesApi.phone_lines TO 'backoffice'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE
ON PhonesApi.tariff TO 'backoffice'@'localhost';

CREATE USER 'infrastructure'@'localhost' IDENTIFIED BY 'admin';
GRANT INSERT ON PhonesApi.calls TO 'infrastructure'@'localhost';
