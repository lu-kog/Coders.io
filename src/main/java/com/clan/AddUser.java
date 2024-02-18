package com.clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddUser {
	
	String username;
	String password;
	String role;
	
	DBConnectivity instance = DBConnectivity.getInstance();
	Connection connection = instance.getConnection();
	
	public AddUser(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public void addLogin(String mailId, String password, String role) {
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(Constant.insertValuesIntoLogin);
			pstmt.setString(1, mailId);
			pstmt.setString(2, password);
			pstmt.setString(3, role);
			int rs = pstmt.executeUpdate();
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public void addUsers(String mailId, String username) {
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(Constant.insertValuesIntoUsers);
			pstmt.setString(1, mailId);
			pstmt.setString(2, username);
			
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
