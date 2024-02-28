package com.clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import utils.CommonLogger;
import utils.DB;
import utils.Query;
import utils.sqlFile;

public class ClanDAO {
	
	static Logger logger = new CommonLogger(ClanDAO.class).getLogger();
	Connection connection = DB.getConnection();
	
	 private static ClanDAO clanDAObj = new ClanDAO();
	
	
	
    public static ClanDAO getObj() {
        return clanDAObj;
    }
	
    
    public String getClanName(String clanID) throws Exception {
    	try {
			PreparedStatement pstmt = connection.prepareStatement(Query.getClanNameByClanID);
			pstmt.setString(1, clanID);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				logger.info("Got clan name by clanID:"+clanID);
				String clanName = rs.getString("clanName");
				return clanName;
			}else {
				logger.error("Can't get clan name for this clan:"+clanID);
				throw new Exception("Oops! Can't get your clan..");
				
			}
		}
		catch(Exception e) {
			logger.error("Error in getting clan name of clan:"+ clanID+" error:"+ e);
			throw new Exception("Can't Access your clan!");
		}	
	}

	public boolean createClan(String clanId, String clanName, String mailId) throws Exception {
		
		// insert a new clan in clan table
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.createNewClan);
			pstmt.setString(1, clanId);
			pstmt.setString(2, clanName);
			pstmt.setString(3, mailId);
			
			int rs = pstmt.executeUpdate();
			
			if(rs > 0) {
				logger.info("Clan created: "+clanId+" by "+mailId);
				sqlFile.append(pstmt.toString());
				return true;
			}
			else {
				logger.info("Failed to create clan:"+clanId+" user:"+mailId);
				throw new Exception("Oops..Can't create clan!");
			}
			
		}
		catch(SQLException e) {
			logger.error("SQL query exception on creating a clan: "+clanId+" user:"+mailId+" error:"+e);
			throw new Exception("Oops! Can't create clan.. Please contact admin!");
		}
		
	}
	
	public String getClanRole(String mailID, String clanID) throws Exception {
		// select role from clan relation where clanID like ? and mailID like ?;
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.getClanRole);
			pstmt.setString(1, mailID);
			pstmt.setString(2, clanID);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				logger.info("Got clan role by Mail ID:"+mailID);
				String role = rs.getString("role");
				return role;
			}else {
				logger.error("Can't get clan role for this user:"+mailID);
				throw new Exception("Can't Access your clan!");
				
			}
		}
		catch(Exception e) {
			logger.error("Error in getting Role of clan:"+ clanID+" mail:"+mailID+" error:"+ e);
			throw new Exception("Can't Access your clan!");
		}
	}
	
	
	public int getClanPosition(String mailID, String clanID) throws Exception {
		try {
			PreparedStatement statement = connection.prepareStatement(Query.getClanRankOfaMember);
			statement.setString(1, mailID);
			statement.setString(2, clanID);
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
	            int clanPos = result.getInt("position");
	            logger.info("Clan position obtained: " + mailID);
	            return clanPos;
	        } else {
	            throw new Exception("Mail ID not found in the clan");
	        }

		} catch (Exception e) {
			throw new Exception("Can't get clan position");
		}
	}
	
	
	
	public boolean joinUserInClan(String clanId, String mailId) throws Exception {
			// insert into clan relation where clanID like ? and mailID like ? and role like 'Member';
			try {
				PreparedStatement pstmt = connection.prepareStatement(Query.joinUserInClanByAdmin);
				pstmt.setString(1, clanId);
				pstmt.setString(2, mailId);
				
				int rs = pstmt.executeUpdate();
				
				if(rs > 0) {
					logger.info(mailId+" joined clan: "+clanId);
					sqlFile.append(pstmt.toString());
					return true;
				}else {
					logger.info(mailId+" can't join the clan: "+clanId);
					throw new Exception("Something went wrong!");
				}
			}
			catch(SQLException e) {
				logger.error("SQL query exception on joining a user :"+mailId+" clan:"+clanId+" error:"+e);
				throw new Exception("Something went wrong!");
			}
			
		}
		
		
	
	
	public boolean removeUserFromClan(String mailId, String clanID) throws Exception {
		
		// delete from clan relation where mailID like ?;
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.leftFromClan);
			pstmt.setString(1, mailId);
			pstmt.setString(2, clanID);
			
			int rs = pstmt.executeUpdate();
			
			if(rs > 0) {
				logger.info(mailId+" removed from clan :"+clanID);
				return true;
			}
			else {
				logger.info(mailId+" can't remove from clan:"+clanID);
				throw new Exception("Oops.. Something went wrong!");
			}
			
		}
		catch(SQLException e) {
			logger.error("SQL query exception while left the clan :"+clanID+" user:"+mailId+" error:"+ e);
			throw new Exception("Oops.. Something went wrong!");
		}
		
	}
	

	
	
	public boolean deleteClan(String clanID) throws Exception {
		
		// delete from clanRelation where clanID like ?
		
		// delete from clan where clanID like ?
		// or on delete cascade
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.deleteClanByAdmin);
			pstmt.setString(1, clanID);
			
			int rs = pstmt.executeUpdate();
			
			if(rs > 0) {
				logger.info("Clan deleted:"+clanID);
				sqlFile.append(pstmt.toString());
				return true;
			}
			else {
				logger.info("Failed to delete clan:"+clanID);
				throw new Exception("Oops.. Something went wrong!");
			}
		}
		catch(SQLException e) {
			logger.error("SQL query exception while delete the clan "+clanID+" error:" + e);
			throw new Exception("Oops.. Something went wrong! please contact admin.");
		}
		
	}
	
	
	public String getClanId(String mailID) throws Exception {
		// select clanID from clan relation where mailID like ?;
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.getClanIDFromMailID);
			pstmt.setString(1, mailID);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				logger.info("Fetch clanID by Mail ID:"+mailID);
				String clanId = rs.getString("clanID");
				return clanId;
			}else {
				logger.error("Can't get clan for this user:"+mailID);
				throw new Exception("User not in any clan.");
				
			}
		}
		catch(Exception e) {
			logger.error("Error in getting clan id by mail:"+mailID+" error:"+ e);
			throw new Exception("Can't get clan for this user. Please contact admin!");
		}
		
	}


	
	public boolean isAdmin(String clanID ,String mailID) throws Exception {
	    
		// select * from clan where Admin like ? and clanID like ?;
	    try {
	        PreparedStatement pstmt = connection.prepareStatement(Query.checkAdminOfClanByAdminID);
	        pstmt.setString(1, clanID);
	        pstmt.setString(2, mailID);
	        ResultSet rs = pstmt.executeQuery();
	       
	        if (rs.next()) {
	        	return true;	            
	        } else {
	            return false;
	        }
	    } catch (Exception e) {
	        logger.error("Error in check admin by mail:"+mailID);
	        throw new Exception("Oops.. something went wrong!");
	    }
	    
	}
	
	
	
	public boolean isMemberOfThisClan(String mailID, String clanID) throws Exception {
	
		// select * from clan relation where mailID like ? and clanID like ? and role like 'Member';
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.AvailInThisClan);
			pstmt.setString(1, mailID);
			pstmt.setString(2, clanID);
			ResultSet rs = pstmt.executeQuery();
		
			if (rs.next()) {
				return true;
			}else {
				return false;
			}		
		
		}
		catch(SQLException e) {
			logger.error("SQL query exception on checking member of a clan: "+clanID+" member:"+mailID + e);
			throw new Exception("Oops.. something went wrong!");
		}
	
	}
	
	
	
	public boolean AvailInAnyClan(String mailID) throws Exception {
		
		// select * from clan relation where mailID like ?;
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.checkMemberInAnyClan);
			pstmt.setString(1, mailID);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}else {
				return false;
			}		
		
		}
		catch(SQLException e) {
			logger.error("SQL query exception on checking member:"+mailID + e);
			throw new Exception("Oops.. something went wrong!");
		}
	
	}

	
	public boolean availInThisClan(String mailID, String clanID) throws Exception {
		
		// select * from clan relation where mailID like ? and clanID like ?;
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.AvailInThisClan);
			pstmt.setString(1, mailID);
			pstmt.setString(2, clanID);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}else {
				return false;
			}		
		
		}
		catch(SQLException e) {
			logger.error("SQL query exception on checking member:"+mailID + e);
			throw new Exception("Oops.. something went wrong!");
		}
	
	}
	
	
	public boolean sendJoinRequestToClan(String clanID, String mailID) throws Exception {
        try {
            PreparedStatement pstmt = connection.prepareStatement(Query.insertClanRequest);
            pstmt.setString(1, clanID);
            pstmt.setString(2, mailID);
            
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                logger.info("Join request sent to clanID:" + clanID + " for user:" + mailID);
                sqlFile.append(pstmt.toString());
                return true;
            } else {
                logger.error("Failed to send join request to clanID:" + clanID + " for user:" + mailID);
                throw new Exception("Failed to send join request to clan.");
            }
        } catch(Exception e) {
            logger.error("Error sending join request to clanID:" + clanID + " for user:" + mailID + " error:" + e);
            throw new Exception("Failed to send join request to clan. Please try again later.");
        }
    }
	
	
	public void removeJoinRequestsOfClan(String clanID, String mailID) throws Exception {
        try {
            PreparedStatement pstmt = connection.prepareStatement(Query.deleteClanRequestsByMailID);
            pstmt.setString(1, clanID);
            pstmt.setString(2, mailID);
            
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                logger.info("Previous requests deleted on clanID:" + clanID + " for user:" + mailID);
                sqlFile.append(pstmt.toString());
            } else {
                logger.error("Failed to delete previous requests of clanID:" + clanID + " for user:" + mailID);
                throw new Exception("Failed to delete join requests of clan.");
            }
        } catch(Exception e) {
            logger.error("Error sending join request to clanID:" + clanID + " for user:" + mailID + " error:" + e);
            throw new Exception("Failed to delete join requests of clan. Please try again later.");
        }
    }



	public boolean isCoAdminOfThisClan(String co_adminID, String clanID) throws Exception {
		// select * from clan relation where clanID like ? and mailID like ? and role like 'Co_ADMIN';
		
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.checkCoAdminOfaClan);
            pstmt.setString(1, co_adminID);
            pstmt.setString(2, clanID);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                logger.info(co_adminID + " is a co_Admin of this clan:" + clanID);
                return true;
            } else {
                logger.error(co_adminID+" is not a co_admin of clan:" + clanID);
                return false;
            }
        } catch(Exception e) {
            logger.error("Error on getting co_admin from a clan:" + clanID + " for user:" + co_adminID + " error:" + e);
            throw new Exception("Something went wrong! Please contact admin.");
        }
	}
	
	
	
	public boolean promoteAsCoAdmin(String memberID, String clanID) throws Exception {
		// alter table clan relation set role = "co-admin" where memberId like ? and clanID like ?;
		
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.changeRoleOfMemberInClan);
            pstmt.setString(1, "CO_ADMIN");
            pstmt.setString(2, clanID);
            pstmt.setString(3, memberID);
            
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                logger.info(memberID + " is now co-admin of this clan:" + clanID);
                return true;
            } else {
                logger.error(memberID+" can't be promoted as co-admin of clan:" + clanID);
                throw new Exception("Something went wrong! Please contact admin.");
            }
        } catch(Exception e) {
            logger.error("Error on promotting co_admin of clan:" + clanID + " for user:" + memberID + " error:" + e);
            throw new Exception("Something went wrong! Please contact admin.");
        }
	}
	
	
	
	public boolean dePromoteAsMember(String coAdminID, String clanID) throws Exception {
		// alter table clan relation set role = "member" where memberId like ? and clanID like ?;
		
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.DepromoteAsMember);
            pstmt.setString(1, coAdminID);
            pstmt.setString(2, clanID);
            
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                logger.info(coAdminID + " is now depromoted as member of this clan:" + clanID);
                return true;
            } else {
                logger.error(coAdminID+" can't be promoted as co-admin of clan:" + clanID);
                throw new Exception("Something went wrong! Please contact admin.");
            }
        } catch(Exception e) {
            logger.error("Error on promotting co_admin of clan:" + clanID + " for user:" + coAdminID + " error:" + e);
            throw new Exception("Something went wrong! Please contact admin.");
        }
	}


	public JSONObject getClanDetails(String mailID) throws Exception {
		/*
		 * Needed Details: 
		 * For each members: Member name, score, clan position, mailID
		 * Get all join requests
		 */
		
		try {
			String clanID = getClanId(mailID);
			
			JSONObject clanData = new JSONObject();
			// getting members of clan
			PreparedStatement pstmt = connection.prepareStatement(Query.GetClanDetails);
			pstmt.setString(1, mailID);
			ResultSet res = pstmt.executeQuery();
			JSONArray clanMembers = new JSONArray();
			while(res.next()) {
				JSONObject member = new JSONObject();
				member.put("memberName", res.getString("userName"));
				member.put("score", res.getInt("score"));
				member.put("role", res.getString("role"));

				clanMembers.put(member);
			}
			logger.info("All members of clan:"+clanID+" fetched successfully!");
			
			// getting requests of a clan
			pstmt = connection.prepareStatement(Query.GetAllRequestsOfClan);
			pstmt.setString(1, clanID);
			ResultSet resuts = pstmt.executeQuery();
			JSONArray requests = new JSONArray();
			while(resuts.next()) {
				JSONObject req = new JSONObject();
				req.put("name", resuts.getString("userName"));
				req.put("mailID", resuts.getInt("mailID"));
				req.put("score", resuts.getString("score"));
				req.put("strike", resuts.getString("strike"));

				requests.put(req);
			}
			logger.info("clan requests obtained for "+clanID);
			
			clanData.put("clanID", clanID);
			clanData.put("members", clanMembers);
			clanData.put("joinRequests", requests);
			
			return clanData;
		} catch (Exception e) {
			throw new Exception("Oops! Error on fetching clan details");
		}
		
	}


	public boolean joinReqExists(String memberID, String clanID) throws Exception {
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.getClanReqByMemberID);
            pstmt.setString(1, memberID);
            pstmt.setString(2, clanID);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                logger.info(memberID + "'s request is still exists in this clan:" + clanID);
                return true;
            } else {
                logger.error(memberID+"'s request not found in:" + clanID);
                throw new Exception("Request not found! Please contact admin.");
            }
        } catch(Exception e) {
            logger.error("Error on getting join request of clan:" + clanID + " for user:" + memberID + " error:" + e);
            throw new Exception("Request not found! Please contact admin.");
        }
	}


	public String pickNextAdminFromCoAdmin(String clanID) throws Exception {
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.pickAdminFromCoAdmins);
            pstmt.setString(1, clanID);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
            	String memberID = rs.getString("mailID");
                return memberID;
            } else {
                return null;
            }
        } catch(Exception e) {
            logger.error("Error on picking admin for clan:" + clanID  + " error:" + e);
            throw new Exception("Oops! something went wrong.. Please contact admin.");
        }
	}
	
	public String pickNextAdminFromMembers(String clanID) throws Exception {
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.pickAdminFromMembers);
            pstmt.setString(1, clanID);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
            	String memberID = rs.getString("mailID");
                return memberID;
            } else {
                return null;
            }
        } catch(Exception e) {
            logger.error("Error on picking admin for clan:" + clanID  + " error:" + e);
            throw new Exception("Oops! something went wrong.. Please contact admin.");
        }
	}

	
	public boolean makeAdminOfClan(String mailID, String clanID) throws Exception {
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.makeAdminOftheClan);
            pstmt.setString(1, mailID);
            pstmt.setString(2, clanID);
            
            int rowAffected = pstmt.executeUpdate();
            if(rowAffected > 0) {
            	logger.info("Admin changed for clan:"+clanID+" New Admin:"+mailID);
            	changeRoleInClan(mailID, clanID, "ADMIN");
                return true;
            } else {
            	throw new Exception("Something went wrong!");
            }
        } catch(Exception e) {
            logger.error("Error on picking admin for clan:" + clanID  + " error:" + e);
            throw new Exception("Oops! something went wrong.. Please contact admin.");
        }
	}

	public boolean changeRoleInClan(String mailID, String clanID, String role) throws Exception {
		try {
            PreparedStatement pstmt = connection.prepareStatement(Query.changeRoleOfMemberInClan);
            pstmt.setString(1, role);
            pstmt.setString(2, clanID);
            pstmt.setString(3, mailID);
            
            int rowAffected = pstmt.executeUpdate();
            if(rowAffected > 0) {
            	logger.info("Role changed for user:"+mailID+" clan:"+clanID+" role:"+role);
                return true;
            } else {
            	throw new Exception("Something went wrong!");
            }
        } catch(Exception e) {
            logger.error("Error on change role as "+role+" user:"+mailID+" for clan:" + clanID  + " error:" + e);
            throw new Exception("Oops! something went wrong.. Please contact admin.");
        }
	}

	
}
