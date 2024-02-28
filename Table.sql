create database Coders;

USE Coders;


CREATE table Users
(
	mailID varchar(30) PRIMARY KEY,
	userName varchar(50),
	score int default 0,
	streak int default 0,
	Datejoined date,
	Streakdate date,
	Ace_badge int default 0,
	Conquer_badge int default 0,
	Crown_badge int default 0
);

CREATE table Login
(
	mailID varchar(30) unique,
	passwd varchar(255) not null,
	Role ENUM('USER', 'ADMIN') default 'USER',
	FOREIGN KEY(mailID) REFERENCES Users(mailID)
);

CREATE table Session
(
	sessionID varchar(40) PRIMARY KEY,
	mailID varchar(30),
   	loggedTime TIMESTAMP,
	FOREIGN KEY(mailID) REFERENCES Users(mailID) 
);



CREATE table Clan
(
	clanID varchar(8) PRIMARY KEY,
	clanName varchar(100),
	Admin varchar(30),
	FOREIGN KEY(Admin) REFERENCES Users(mailID)
);



CREATE table ClanRelation
(
	clanID varchar(8),
	mailID varchar(30) NOT NULL UNIQUE,
	role enum('CO_ADMIN', 'MEMBER') default 'MEMBER',
	FOREIGN KEY(clanID) REFERENCES Clan(clanID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(mailID) REFERENCES Users(mailID) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE table ClanRequest
(
	clanID varchar(8),
	mailID varchar(30),
	FOREIGN KEY(clanID) REFERENCES Clan(clanID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(mailID) REFERENCES Users(mailID) ON DELETE CASCADE ON UPDATE CASCADE
);


create table Levels(
    levelID tinyint auto_increment primary key,
    level_name varchar(10),
    score tinyint default 0
);

CREATE table Questions
(
    Q_ID varchar(8)primary key,
    Q_name varchar(100),
    description text,
    example text,
    levelID tinyint,
    Author varchar(30),
    FOREIGN KEY(Author) REFERENCES Users(mailID),
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
	Q_ID varchar(8),
	funcName text,
	testCases varchar(255),
	FOREIGN KEY(l_ID) REFERENCES Languages(l_ID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE table Solutions
(
	Sol_ID varchar(8) PRIMARY KEY,
	mailID varchar(30),
	Q_ID varchar(8),
	Sol text,
	lang_ID int,
	status enum('ATTEMPTED', 'COMPLETED'),
	solvedType enum('PRACTICE','TOURNAMENT'),
	FOREIGN KEY(lang_ID) REFERENCES Languages(l_ID),
	FOREIGN KEY(mailID) REFERENCES Users(mailID),
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID)
);

CREATE table Tags
(
	Tag_ID int AUTO_INCREMENT PRIMARY KEY,
	Tag_name varchar(100)
);

CREATE table TagsRelation
(
	Q_ID varchar(8),
	Tag_ID int,
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(Tag_ID) REFERENCES Tags(Tag_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE table Tournament
(
	mailID varchar(30) unique,
	Start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	End_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	Q_ID varchar(8),
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID),
	FOREIGN KEY(mailID) REFERENCES Users(mailID) 
);

INSERT into Languages(lang_name) values('Java'), ('Python');

INSERT into Levels(level_name,score) values ('8Kyu',2),('7Kyu',3),('6Kyu',4);

