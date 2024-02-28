package utils;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONObject;



public class DB {

	private static Connection conn = null;
	static Logger logger = new CommonLogger(DB.class).getLogger();
	
	public static Connection getConnection() {
		
		
		if (conn == null) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				logger.error("SQL driver not found!");
			}
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Coders", "root" , "");
				logger.info("DB connection created!");
			} catch (SQLException e) {
				logger.error(conn+"Error on sql: "+e);
			}
	      	
		}
		
		return conn;
    } 
	
	
	public static boolean checkValueisExist(String value, String table_name, String column) {
		Connection conn = DB.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("SELECT "+column+" FROM "+table_name+" WHERE "+column+" like ?");
            statement.setString(1, value);
            
            logger.info("value:"+value+" - "+statement.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            
        } catch (SQLException e) {
        	
			logger.error("SQL querry exception: "+e+" Querry: "+ statement);
		}
		
		return false;
	}
	
	
	
	
	
	/*
	
	public static String createNewSession(String userID) throws Exception {
		
		Connection conn = DB.getConnection();
        
        try (PreparedStatement statement = conn.prepareStatement(Query.createNewSession)) {
        	
        	String sessionID = Generator.createUUID("Session", "sessionID");
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        	statement.setString(1,sessionID);
        	statement.setString(2, userID);
        	statement.setTimestamp(3, currentTimestamp);

        	deleteSession(userID);
        	
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                logger.error("can't create new session: " + statement.toString());
            } else {
                logger.info("new session created successfully.");
                sqlFile.append(statement.toString());
                return sessionID;
            }
        }catch (SQLException e) {
            logger.error("SQL error on creating session"+e);
            throw new Exception("Can't create session! Please contact admin.");
        }

        return null;
    }
	
	
	
	public static void deleteSession(String userID) throws Exception {
		
		Connection conn = DB.getConnection();
        
        try (PreparedStatement statement = conn.prepareStatement(Query.deleteSession)) {
        	
        	statement.setString(1, userID);

            if(statement.execute()) {
            	sqlFile.append(statement.toString());
                logger.info("session deleted successfully.");
			}
        }catch (SQLException e) {
            logger.error("SQL error on deleting session"+e);
            throw new Exception("session error! Please contact admin.");
        }

    }
	
	
public static boolean validateSession(String sessionID, String userID) throws Exception {
		
		Connection conn = DB.getConnection();
        
        try {
        	PreparedStatement statement = conn.prepareStatement(Query.validateSession);
        	
        	statement.setString(1, userID);

        	ResultSet rs = statement.executeQuery();
            if(rs.next()) {
            	boolean validSession = sessionID.equals(rs.getString("sessionID"));
            	if (validSession) {
					logger.info("valid session!"+sessionID);
					return true;
				}else {
					logger.info("Session invalid!"+sessionID);
					return false;
				}
			}else {
				logger.info("sesison not found!");
				throw new Exception("Session not found!"+sessionID);
			}
        }catch (SQLException e) {
            logger.error("SQL error on deleting session"+e);
            throw new Exception("session error! Please contact admin.");
        }

    }
	
	 * 
	 */
	
}
