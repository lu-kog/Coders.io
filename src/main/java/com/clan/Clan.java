package com.clan;

import java.util.List;

import org.json.JSONObject;

import com.user.User;

public class Clan {
	String clanID;
	String clanName;
	User admin;
	List<User> coAdmins;
	List<User> clanMembers;
	List<User> joinRequests;
	
	public Clan(String clanID) {
		this.clanID = clanID;
	}
	
	public JSONObject toJSON() throws Exception{
		try {
			JSONObject clanData = new JSONObject();
			clanData.put("clanID", clanID);
			clanData.put("clanName", clanName);
			clanData.put("admin", admin.getMailID());
			clanData.put("coAdmins", coAdmins);
			clanData.put("clanMembers", clanMembers);
			clanData.put("joinRequests", joinRequests);

			return clanData;
		} catch (Exception e) {
			throw new Exception("Can't fetch clan Details");
		}

	}
	
	
}
