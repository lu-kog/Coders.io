package com.clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JoinClan {
	
	DBConnectivity instance = DBConnectivity.getInstance();
	Connection connection = instance.getConnection();
	
	public String joinClan(String mailId, String clanId) {
		
		boolean flag = checkUserAlreadyExistInClan(mailId, clanId);
		
		String clanJoined = "";
		
		if(flag) {
			clanJoined = "User cannot join in clan";
		}
		else {
			String query = "insert into ClanRelation values (?,?)";
			
			try {
				PreparedStatement pstmt = connection.prepareStatement(query);
				pstmt.setString(1, clanId);
				pstmt.setString(2, mailId);
				
				int rs = pstmt.executeUpdate();
				
				if(rs > 0) {
					clanJoined = "User successfully joined in the clan";
				}
				else {
					clanJoined = "User failed to join in the clan";
				}
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		return clanJoined;
	}
	
	
	public boolean checkUserAlreadyExistInClan(String mailId, String clanId) {
		
		boolean flag = false;
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(Constant.selectMAil);
			pstmt.setString(1, clanId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(mailId.equals(rs.getString("mailID"))) {
					flag = true;
				}
			}
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return flag;
	}
}
