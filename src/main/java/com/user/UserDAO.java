package com.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.catalina.connector.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.DB;
import utils.Generator;
import utils.JSON;
import utils.Query;
import utils.sqlFile;

public class UserDAO {

	static Logger logger = new CommonLogger(UserDAO.class).getLogger();
	Connection connection = DB.getConnection();

	private static UserDAO userDAO = new UserDAO();

	public static UserDAO getObj() {
		return userDAO;
	}

	
	public boolean createAccount(String userName, String mailID, String passwd) throws Exception {
		// insert user in user table & insert passwd in Login table
		try {
			PreparedStatement statement = connection.prepareStatement(Query.CreateAccount);
			Date currentDate = Date.valueOf(LocalDate.now());
			statement.setString(1, mailID);
			statement.setString(2, userName);
			statement.setDate(3, currentDate);
			statement.setDate(4, currentDate);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				logger.error("can't create account: " + statement.toString());
				throw new Exception("Something went wrong!");
			} else {
				sqlFile.append(statement.toString());
				
				// inserting login creds
				statement = connection.prepareStatement(Query.InsertCredentials);
				statement.setString(1, mailID);
				statement.setString(2, passwd);
				statement.execute();
				logger.info("new account created successfully.");
				sqlFile.append(statement.toString());
				return true;
			}
		} catch (SQLException e) {
			logger.error("SQL error on creating account"+" mail:"+mailID+" userName"+userName+ e);
			throw new Exception("Oops! Something went wrong. Please contact admin.");
		}
	}
	
	
	public boolean LoginUser(String mailID, String passwd) throws Exception {
		System.out.println(mailID + " "+passwd);
		String hshPW = getPasswd(mailID);
		if (BCrypt.checkpw(passwd, hshPW)) {
			return true;
		} else {
			return false;
		}
	}

	private String getPasswd(String mailID) throws Exception {
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.getPasswordByMailID);
			pstmt.setString(1, mailID);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				logger.info("Fetch password by Mail ID:" + mailID);
				String pw = rs.getString("passwd");
				return pw;
			} else {
				logger.error("Can't get password for this user:" + mailID);
				throw new Exception("Something went wrong! Please contact admin.");
			}
		} catch (Exception e) {
			logger.error("Error in getting passwd by mail:" + mailID + " error:" + e);
			throw new Exception("Oops! Something went wrong.");
		}
	}

	public static String createNewSession(String mailID) throws Exception {

		Connection conn = DB.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement(Query.CreateNewSession);
			String sessionID = Generator.createUUID("Session", "sessionID");
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			statement.setString(1, sessionID);
			statement.setString(2, mailID);
			statement.setTimestamp(3, currentTimestamp);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				logger.error("can't create new session: " + statement.toString());
				throw new Exception("Invalid session!");
			} else {
				logger.info("new session created successfully.");
				sqlFile.append(statement.toString());
				return sessionID;
			}
		} catch (SQLException e) {
			logger.error("SQL error on creating session" + e);
			throw new Exception("Can't create session! Please contact admin.");
		}

	}

	public static void deleteSession(String sessionID) throws Exception {

		Connection conn = DB.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement(Query.DeleteSession);

			statement.setString(1, sessionID);

			if (statement.execute()) {
				sqlFile.append(statement.toString());
				logger.info("session deleted successfully.");
			}
		} catch (SQLException e) {
			logger.error("SQL error on deleting session" + e);
			throw new Exception("session error! Please contact admin.");
		}

	}

	public static boolean validateSession(String sessionID, String mailID) throws Exception {

		Connection conn = DB.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement(Query.validateSession);

			statement.setString(1, mailID);
			statement.setString(2, sessionID);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				logger.info("valid session!" + sessionID);
				return true;

			} else {
				logger.info("Session invalid!" + sessionID+" mailID:"+mailID);
				return false;
			}
		} catch (SQLException e) {
			logger.error("SQL error on deleting session" + e);
			throw new Exception("session error! Please contact admin.");
		}

	}


	public JSONArray getAllAuthoredQuestions(String mailID) throws Exception {
		
		// select * from questions where author like ?;

		JSONArray authoredQuestions = new JSONArray();
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.getAuthoredQuestionsOfaUser);
			pstmt.setString(1, mailID);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				JSONObject question = new JSONObject();
				ArrayList<String> lang = getLangForQues(res.getString("Q_ID"));
				question.put("languages", lang);
				question.put("ques", res.getString("Q_name"));
				question.put("level", res.getString("level_name"));
				authoredQuestions.put(question);
			}
			logger.info("Authored Questions obtained");
			
			return authoredQuestions;
		} catch (Exception e) {
			throw new Exception("Oops! something went wrong.");
		}
	}

	

	private ArrayList<String> getLangForQues(String questionID) throws Exception {
		// select lang from lang relation where questionid like ?;
		ArrayList<String> languages = new ArrayList<String>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.getLangListFromQuestionID);
			pstmt.setString(1, questionID);
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				languages.add(res.getString("lang_name"));
			}
			return languages;
		} catch (Exception e) {
			throw new Exception("Oops! Something went wrong!");
		}
		
	}


	public JSONArray fetchCompletedSolutionsOfAUser(String mailID) throws Exception {
		
		try {
			JSONArray completedSolutions = new JSONArray();
			PreparedStatement pstmt = connection.prepareStatement(Query.getAllCompletedSolutionsOfaUser);
			
			pstmt.setString(1, mailID);
			
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()) {
				JSONObject solutionDetails = new JSONObject();
				String quesName = res.getString("Q_name");
				String sol = res.getString("Sol");
				String status = res.getString("status");
				String level = res.getString("levelID");
				ArrayList<String> lang = getLangForQues(res.getString("Q_ID"));
				solutionDetails.put("Question", quesName);
				solutionDetails.put("solution", sol);
				solutionDetails.put("status", status);
				solutionDetails.put("level", level);
				solutionDetails.put("language", lang);
				completedSolutions.put(solutionDetails);
			}
			logger.info("completed solutions fetched succesfully"+mailID);
			return completedSolutions;
		} catch (Exception e) {
			logger.error("Error on fetching completed solutions of "+mailID+" Error:"+e);
			throw new Exception("Can't fetch completed solutions");
		}
		

	}
	
	
	public JSONArray fetchAllUnfinishedSolutions(String mailID) throws Exception {
		
		JSONArray AllAttemptedSolutions = new JSONArray();
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.GetAllAttemptedSolutions);
			pstmt.setString(1, mailID);
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()) {
				JSONObject attemptedSolution = new JSONObject();
				String quesName = res.getString("Q_name");
				String sol = res.getString("Sol");
				String status = res.getString("status");
				String level = res.getString("level");
				ArrayList<String> lang = getLangForQues(res.getString("Q_ID"));
				attemptedSolution.put("Question", quesName);
				attemptedSolution.put("soln", sol);
				attemptedSolution.put("status", status);
				attemptedSolution.put("level", level);
				attemptedSolution.put("language", lang);
				AllAttemptedSolutions.put(attemptedSolution);
			}
			logger.info("Completed attempted solutions fetched");
		} catch (Exception e) {
			throw new Exception("Can't fetch attempted solution!");
		}
		return AllAttemptedSolutions;
		
	}


	public JSONObject getStats(String userMail) throws Exception {
		/*
		 * Clan position, Total completed katas, Total authored katas, Language trained - solution count of each, 
		 */
		
		JSONObject respJson = new JSONObject();
		boolean availInClan = ClanDAO.getObj().AvailInAnyClan(userMail);
		try {
			if (availInClan) {
				String clanID = ClanDAO.getObj().getClanId(userMail);
				int clanPosition = ClanDAO.getObj().getClanPosition(userMail, clanID);
				respJson.put("clanID", clanID);
				respJson.put("clanPosition", clanPosition);
			}
			int totalCompletedKatas = getTotalCompletedKata(userMail);
			int totalAuthoredKatas = getTotalAuthoredKata(userMail);
			ArrayList<Integer> kataCountForEachLang = getCountOfEachLang(userMail);
			
			// responses
			respJson.put("availInClan", availInClan);
			respJson.put("totalCompletedKatas", totalCompletedKatas);
			respJson.put("totalAuthoredKatas", totalAuthoredKatas);
			respJson.put("kataCountForEachLang", kataCountForEachLang);
			
			return respJson;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	private ArrayList<Integer> getCountOfEachLang(String userMail) throws Exception {
		ArrayList<String> langList = new ArrayList<String>();
		langList.add("python");
		langList.add("java");
		langList.add("javascript");
		
		ArrayList<Integer> countForLang = new ArrayList<Integer>();
		try {
			PreparedStatement statement = connection.prepareStatement(Query.getCountOfSolutionsForEachLang);
			
			for (int i = 0; i < langList.size(); i++) {
				statement.setString(1, userMail);
				statement.setString(2, langList.get(i));
				ResultSet result = statement.executeQuery();
				int solutionCount = result.getInt("solCount");
				countForLang.add(solutionCount);
				
				logger.info(langList.get(i)+" Solution count getting for:"+userMail);
			}
		
			logger.info("Getting total count of solutions for each languages :"+countForLang+" user:"+userMail);
			return countForLang;
		} catch (Exception e) {
			logger.error("Error on getting count of completed solutions:"+e+" user:"+userMail);
			throw new Exception("Oops! something went wrong..");
		}
		
	}


	private int getTotalAuthoredKata(String userMail) throws Exception {
		try {
			PreparedStatement statement = connection.prepareStatement(Query.getCountOfAuthoredQuestions);
			statement.setString(1, userMail);
			ResultSet result = statement.executeQuery();
			int totalAuthored = result.getInt("authored");
			
			logger.info("Getting total count of authored kata of:"+userMail);
			return totalAuthored;
		} catch (Exception e) {
			throw new Exception("Can't get Authored kata count!");
		}
	}


	private int getTotalCompletedKata(String userMail) throws Exception {

		try {
			PreparedStatement statement = connection.prepareStatement(Query.getCountOfCompletedSolutions);
			statement.setString(1, userMail);
			ResultSet result = statement.executeQuery();
			int totalKata = result.getInt("completedKata");
			
			logger.info("Getting total count of completed solutions of:"+userMail);
			return totalKata;
		} catch (Exception e) {
			throw new Exception("Can't get solutions count!");
		}
		
	}


	public JSONObject getUserProfileData(String userMail) throws Exception {
		/*
		 * thevaiyana porutkal: user name, score, clan name, member since, badges count, streak.
		 */
		
		JSONObject userData = new JSONObject();
		try {
			PreparedStatement statement = connection.prepareStatement(Query.getFullUserData);
			statement.setString(1, userMail);
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				userData.put("mailID", result.getString("mailID"));
                userData.put("userName", result.getString("userName"));
                userData.put("score", result.getInt("score"));
                userData.put("streak", result.getInt("streak"));
                userData.put("Datejoined", result.getDate("Datejoined").toString());

                JSONObject badges = new JSONObject();
                badges.put("ace", result.getInt("Ace_badge"));
                badges.put("conquer", result.getInt("Conquer_badge"));
                badges.put("crown", result.getInt("Crown_badge"));

                userData.put("badges", badges);
                boolean inAnyClan = ClanDAO.getObj().AvailInAnyClan(userMail);
                userData.put("inAnyClan", inAnyClan);
                if (inAnyClan) {
					String clanID = ClanDAO.getObj().getClanId(userMail);
					userData.put("clanName", ClanDAO.getObj().getClanName(clanID));
				}

                return userData;
			}else {
				throw new Exception("User not found!");
			}		
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
