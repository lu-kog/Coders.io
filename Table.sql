
create database Coders;

USE Coders;

CREATE table Login
(
	mailID varchar(30) PRIMARY KEY,
	passwd varchar(255),
	Role ENUM('USER', 'ADMIN')
);

CREATE table Session
(
	sessionID varchar(40),
	mailID varchar(30),
   	loggedTime TIMESTAMP,
	FOREIGN KEY(mailID) REFERENCES Login(mailID)
);



CREATE table Clan
(
	clanID varchar(8) PRIMARY KEY,
	clanName varchar(100)
);

CREATE table Users
(
	mailID varchar(30) not null unique,
	userName varchar(50),
	score int,
	streak int,
	FOREIGN KEY(mailID) REFERENCES Login(mailID)
);

CREATE table ClanRelation
(
	clanID varchar(8),
	mailID varchar(30) NOT NULL UNIQUE,
	FOREIGN KEY(clanID) REFERENCES Clan(clanID),
	FOREIGN KEY(mailID) REFERENCES Login(mailID)
);


create table Levels(
    levelID tinyint auto_increment primary key,
    level_name varchar(10),
    score tinyint default 0
);

CREATE table Questions
(
    Q_ID int AUTO_INCREMENT PRIMARY KEY,
    Q_name varchar(100),
    description text,
    funcName text,
    levelID tinyint,
    mailID varchar(30),
    FOREIGN KEY(mailID) REFERENCES Login(mailID),
    FOREIGN KEY(levelID) REFERENCES Levels(levelID)
);

CREATE table Languages
(
	l_ID int AUTO_INCREMENT PRIMARY KEY,
	lang_name varchar(100)
);

CREATE table LanguageRelation
(
	l_ID int,
	Q_ID int,
	testCases varchar(255),
	FOREIGN KEY(l_ID) REFERENCES Languages(l_ID),
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID)
);

CREATE table Solutions
(
	Sol_ID int AUTO_INCREMENT PRIMARY KEY,
	mailID varchar(8),
	Q_ID int,
	Sol text,
	status enum('ATTEMPTED', 'COMPLETED'),
	FOREIGN KEY(mailID) REFERENCES Login(mailID),
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID)
);

CREATE table Tags
(
	Tag_ID int AUTO_INCREMENT PRIMARY KEY,
	Tag_name varchar(100)
);

CREATE table TagsRelation
(
	Q_ID int,
	Tag_ID int,
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID),
	FOREIGN KEY(Tag_ID) REFERENCES Tags(Tag_ID)
);

INSERT into Languages(lang_name) values('Java'), ('JavaScript'), ('Python');

INSERT into Levels(level_name,score) values ('8Kyu',2),('7Kyu',3),('6Kyu',4);