DROP DATABASE if exists NoExcuses;
CREATE DATABASE NoExcuses;
USE NoExcuses;

CREATE TABLE Users (
	username VARCHAR(50) PRIMARY KEY NOT NULL,
    userpassword VARCHAR(50) NOT NULL,
    phonenumber VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    ticket VARCHAR(50),
    ticketvalidated VARCHAR(50),
    lattitude FLOAT(24),
    longitude FLOAT( 24)
);