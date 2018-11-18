DROP DATABASE if exists NoExcuses;
CREATE DATABASE NoExcuses;
USE NoExcuses;

CREATE TABLE Users (
	username VARCHAR(50) PRIMARY KEY NOT NULL,
    userpassword VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    lattitude VARCHAR(50),
    longitude VARCHAR( 50),
    loggedin VARCHAR(50) NOT NULL,
    ticketNumber VARCHAR(50) NOT NULL,
	FOREIGN KEY (ticketNumber) REFERENCES Tickets(ticketNumber)
);

CREATE TABLE Tickets (
	ticketNumber VARCHAR(50) PRIMARY KEY NOT NULL,
    validated VARCHAR(50) NOT NULL
);

CREATE TABLE Friends (
    username VARCHAR(50) PRIMARY KEY NOT NULL,
    friendusername VARCHAR(50) NOT NULL,
	FOREIGN KEY (username) REFERENCES Users(username),
	FOREIGN KEY (friendusername) REFERENCES Users(username)
);