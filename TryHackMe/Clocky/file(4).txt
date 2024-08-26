



#################################################
#					   	#
# Flag 4: THM{350020dc1a53e50e1e92bac2c35dd0a2} #
#					   	#
#################################################



CREATE DATABASE IF NOT EXISTS clocky;
USE clocky;

CREATE USER IF NOT EXISTS 'clocky_user'@'localhost' IDENTIFIED BY '!WE_LOVE_CLEARTEXT_DB_PASSWORDS!';
GRANT ALL PRIVILEGES ON *.* TO 'clocky_user'@'localhost' WITH GRANT OPTION;

CREATE USER IF NOT EXISTS 'clocky_user'@'%' IDENTIFIED BY '!WE_LOVE_CLEARTEXT_DB_PASSWORDS!';
GRANT ALL PRIVILEGES ON *.* TO 'clocky_user'@'%' WITH GRANT OPTION;

FLUSH PRIVILEGES;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS passwords;
/*
DROP TABLE IF EXISTS reset_token;
*/

CREATE TABLE users(
        ID INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        Created timestamp default current_timestamp );

INSERT INTO users (username) VALUES ("administrator");

CREATE TABLE passwords(
        ID INT AUTO_INCREMENT NOT NULL,
        password VARCHAR(256) NOT NULL,
        FOREIGN KEY (ID) REFERENCES users(ID) );

INSERT INTO passwords (password) VALUES ("Th1s_1s_4_v3ry_s3cur3_p4ssw0rd");

/* Do we actually need this part anymore?
I've updated app.py to not use this due to brute force errors

CREATE TABLE reset_token(
        ID INT AUTO_INCREMENT NOT NULL,
        username VARCHAR(50) UNIQUE NOT NULL,
        token VARCHAR(128) UNIQUE,
        FOREIGN KEY (ID) REFERENCES users(ID) );

### TEST TOKEN ###
INSERT INTO reset_token (username, token) VALUES ("administrator", "WyJhZG1pbmlzdHJhdG9yIl0.hFrZoI0BzkqoI01vfOL13haqpwY");
*/
