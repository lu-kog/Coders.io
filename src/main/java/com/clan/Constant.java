package com.clan;

public class Constant {
	static String selectMAil = "SELECT mailID FROM ClanRelation WHERE clanID = ?";
	static String selectClanValues = "insert into Clan values (?,?)";
	static String insertValuesIntoLogin = "insert into Login values (?,?,?)";
	static String insertValuesIntoUsers = "insert into Users (mailId, username) values (?,?)";
}
