package com.clan;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateClan {

	DBConnectivity instance = DBConnectivity.getInstance();
	Connection connection = instance.getConnection();
	
	public String createClan(String clanId, String clanName) {
		
		
		String clanCreated = "";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(Constant.selectClanValues);
			pstmt.setString(1, clanId);
			pstmt.setString(2, clanName);
			
			int rs = pstmt.executeUpdate();
			
			if(rs > 0) {
				
				clanCreated = "Clan successfully created";
			}
			else {
				clanCreated = "Clan failed to create";
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return clanCreated;
	}
	
	
}
