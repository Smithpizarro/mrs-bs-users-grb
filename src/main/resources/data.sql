INSERT INTO tbl_users (id, name, email, password,last_login,  active ,created_user, created_date,updated_date) VALUES ('76129f42-3237-11ee-b3bf-12dee90996cb','jonathan smith','jpizarroaguado@gmail.com','$2a$12$lIbsNyYyWvrhKLmoc/.TM.9xQLVIz7cNP2cmlvN2pYt25r6ZGwuKu',CURRENT_TIMESTAMP, true,'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tbl_phones ( number, citycode, countrycode, user_id) VALUES ( 922334455,1, 2,'76129f42-3237-11ee-b3bf-12dee90996cb');
INSERT INTO tbl_phones ( number, citycode, countrycode, user_id) VALUES ( 911223344 ,1, 3,'76129f42-3237-11ee-b3bf-12dee90996cb');
