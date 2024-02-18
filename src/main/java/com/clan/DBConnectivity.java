package com.clan;

import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnectivity {

	private static DBConnectivity instance;
	
	private Connection connection;
	
	private DBConnectivity() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/codeWars","Sorimuthu","33");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static DBConnectivity getInstance() {
		if(instance == null) {
			instance = new DBConnectivity();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
}