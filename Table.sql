
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
	clanName varchar(100),
	Admin varchar(30),
	FOREIGN KEY(Admin) REFERENCES Login(mailID)
);

CREATE table Users
(
	mailID varchar(30) not null unique,
	userName varchar(50),
	score int default 0,
	streak int default 0,
	Datejoined date,
	Streakdate date,
	Ace_badge int default 0,
	Conquer_badge int default 0,
	Crown_badge int default 0,
	FOREIGN KEY(mailID) REFERENCES Login(mailID)
);

CREATE table ClanRelation
(
	clanID varchar(8),
	mailID varchar(30) NOT NULL UNIQUE,
	FOREIGN KEY(clanID) REFERENCES Clan(clanID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(mailID) REFERENCES Login(mailID) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE table ClanRequest
(
	clanID varchar(8),
	mailID varchar(30),
	FOREIGN KEY(clanID) REFERENCES Clan(clanID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(mailID) REFERENCES Login(mailID) ON DELETE CASCADE ON UPDATE CASCADE
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
    levelID tinyint,
    Author varchar(30),
    FOREIGN KEY(Author) REFERENCES Login(mailID),
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
	status enum('ATTEMPTED', 'COMPLETED'),
	solvedType enum('PRACTICE','TOURNAMENT'),
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
	Q_ID varchar(8),
	Tag_ID int,
	FOREIGN KEY(Q_ID) REFERENCES Questions(Q_ID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(Tag_ID) REFERENCES Tags(Tag_ID) ON DELETE CASCADE ON UPDATE CASCADE
);



INSERT into Languages(lang_name) values('Java'), ('JavaScript'), ('Python');

INSERT into Levels(level_name,score) values ('8Kyu',2),('7Kyu',3),('6Kyu',4);

insert into Login values 
	("gokul01@gmail.com", "12345", 'USER'),
	("vasanth02@gmail.com", "14863", 'USER'),
	("charu07@gmail.com", "18451", 'USER'),
	("santhiya04@gmail.com", "78934", 'USER'),
	("tharan001@gmail.com", "51973", 'USER'),
	("sorimuthu33@gmail.com", "09876", 'USER');

insert into Clan(clanID, clanName) values
	("5442987", "zohoSchools"),
	("2098578", "coders"),
	("3057823", "softwareEngineers");
	
insert into Users (mailID, userName) values
	("gokul01@gmail.com", "Gokul"),
	("vasanth02@gmail.com", "Vasanth"),
	("charu07@gmail.com", "Charu"),
	("santhiya04@gmail.com", "Santhiya"),
	("tharan001@gmail.com", "Tharan"),
	("sorimuthu33@gmail.com", "Sorimuthu");
	
INSERT into Questions(Q_ID, Q_name,description,levelID,Author) values ("56873465","Remove the minimum",'The museum of incredibly dull things wants to get rid of some exhibits. Miriam, the interior architect, comes up with a plan to remove the most boring exhibits. She gives them a rating, and then removes the one with the lowest rating. However, just as she finished rating all exhibits, shes off to an important fair, so she asks you to write a program that tells her the ratings of the exhibits after removing the lowest one. Fair enough. Task Given an array of integers, remove the smallest value. Do not mutate the original array/list. If there are multiple elements with the same value, remove the one with the lowest index. If you get an empty array/list, return an empty array/list. Dont change the order of the elements that are left. Examples * Input: [1,2,3,4,5], output = [2,3,4,5] * Input: [5,3,2,1,4], output = [5,3,2,4] * Input: [2,2,1,2,1], output = [2,2,2,1]',1,"gokul01@gmail.com");


INSERT into Questions(Q_ID, Q_name,description,levelID,Author) values ("56321409","Basic subclasses",'According to the creation myths of the Abrahamic religions, Adam and Eve were the first Humans to wander the Earth.
You have to do Gods job. The creation method must return an array of length 2 containing objects (representing Adam and Eve). The first object in the array should be an instance of the class Man. The second should be an instance of the class Woman. Both objects have to be subclasses of Human. Your job is to implement the Human, Man and Woman classes.',2,"vasanth02@gmail.com");


INSERT into Questions(Q_ID, Q_name,description,levelID,Author) values ("09796534","Multiply the number",'Jack really likes his number five: the trick here is that you have to multiply each number by 5 raised to the number of digits of each numbers, so, for example:

Kata.multiply(3) == 15      // 3 * 5¹
Kata.multiply(10) == 250    // 10 * 5²
Kata.multiply(200) == 25000 // 200 * 5³
Kata.multiply(0) == 0       // 0 * 5¹
Kata.multiply(-3) == -15    // -3 * 5¹',2,"gokul01@gmail.com");
    
    
INSERT into Questions(Q_ID, Q_name,description,levelID,Author) values ("67984563","All Star Code Challenge #18",'Create a function that accepts a string and a single character, and returns an integer of the count of occurrences the 2nd argument is found in the first one.

If no occurrences can be found, a count of 0 should be returned.

("Hello", "o")  ==>  1
("Hello", "l")  ==>  2
("", "z")       ==>  0

str_count("Hello", \'/o\'); // returns 1
str_count("Hello", \'/o\'); // returns 2
str_count("", \'/o\'); // returns 0

Notes

    The first argument can be an empty string
    In languages with no distinct character data type, the second argument will be a string of length 1',2,"vasanth02@gmail.com");
    
    
INSERT into Questions(Q_ID, Q_name,description,levelID,Author) values ("45986677","Invert values",'Given a set of numbers, return the additive inverse of each. Each positive becomes negatives, and the negatives become positives.

invert([1,2,3,4,5]) == [-1,-2,-3,-4,-5]
invert([1,-2,3,-4,5]) == [-1,2,-3,4,-5]
invert([]) == []',2,"charu07@gmail.com");



INSERT into Questions(Q_ID, Q_name,description,levelID,Author) values ("45983322","Powers of 2",'Complete the function that takes a non-negative integer n as input, and returns a list of all the powers of 2 with the exponent ranging from 0 to n ( inclusive ).
Examples

n = 0  ==> [1]        # [2^0]
n = 1  ==> [1, 2]     # [2^0, 2^1]
n = 2  ==> [1, 2, 4]  # [2^0, 2^1, 2^2]',2,"charu07@gmail.com");
