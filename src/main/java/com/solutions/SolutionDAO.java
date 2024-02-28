package com.solutions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.user.UserDAO;

import utils.CommonLogger;
import utils.DB;
import utils.Query;
import utils.sqlFile;

public class SolutionDAO {

	static Logger logger = new CommonLogger(SolutionDAO.class).getLogger();
	Connection connection = DB.getConnection();

	private static SolutionDAO solutionDAO = new SolutionDAO();

	private SolutionDAO() {
		//
	}
	
	public static SolutionDAO getObj() {
		return solutionDAO;
	}
    
	/*
    public boolean insertSolution(String solID,String mailId, String qId,String status, String solvedType) throws Exception {
        
        try {
        	PreparedStatement statement = connection.prepareStatement(Query.InsertNewSolution);
        	statement.setString(1, solID);
            statement.setString(2, mailId);
            statement.setString(3, qId);
            statement.setString(4, status);
            statement.setString(5, solvedType);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
            	logger.info("Solution updated successfully.. solID"+solID+" user:"+mailId);
            	sqlFile.append(statement.toString());
            	return true;
            }else {
				throw new Exception("Error on saving solution! Please try again..");
			}
            
        }catch (SQLException e) {
        	logger.error("SQL error on inserting a solution in table: "+e);
		} catch (Exception e) {
        	logger.error("can't insert the solution in table:"+e);
            throw new Exception(e.getMessage()); 
        }
    }
    */
    
	/*
    
    public boolean insertSolution(Solution solution) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(Query.InsertNewSolution);
            statement.setString(1, solution.getSolID());
            statement.setString(2, solution.getUser().getMailID());
            statement.setString(3, solution.getqID());
            statement.setString(4, solution.getStatus());
            statement.setString(5, solution.getSolvedType());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Solution updated successfully. solID: " + solution.getSolID() + " user: " + solution.getMailId());
                sqlFile.append(statement.toString());
                return true;
            } else {
                throw new Exception("Error on saving solution! Please try again.");
            }
        } catch (SQLException e) {
            logger.error("SQL error on inserting a solution in table: " + e.getMessage());
            throw new Exception("SQL error on inserting a solution in table: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error inserting the solution in table: Solution:"+solution.toJSON() + e);
            throw new Exception("Error on saving the solution! Please try again..");
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    
    // Method to update the solution text in the Solutions table
    public boolean updateSolution(int solId, String newSolution) throws Exception {
        boolean success = false;
        String query = Querry.updateSolution;
        try {
        	PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newSolution);
            preparedStatement.setInt(2, solId);
            int rowsUpdated = preparedStatement.executeUpdate();
            success = rowsUpdated > 0;
        } catch (Exception e) {
            logger.error("can't update the solution");
            throw new Exception("can't update the solution"); 
        }
        logger.info("update the solution to the database successfully!!");
        return success;
    }
    
    // Method to check if the user has completed the question
    public boolean isQuestionCompleted(String questionId, String userId) throws Exception {
        String query = Querry.QuestionCompleted;
        try{
        	PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, questionId);
            statement.setString(2, userId);
            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    logger.info("the user has completed the question check");
                    return count > 0;                   
                }
                
        }
        catch (Exception e) {
        	//Logger 
        	 logger.error("can't find his paticipation on this question");
        	throw new Exception("can't find his paticipation on this question");
			// TODO Auto-generated catch block
		}
        return false;
    }
    
    // Method to check if the user has attempt the question
    public String getAttemptedSolution(String questionId, String mailId,String languageName) throws Exception {
        String query = Querry.QuestionAttempt;
        String sol="none";
        try{
        	PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, questionId);
            statement.setString(2, mailId);
            statement.setString(3, languageName);
            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    sol= resultSet.getString("Sol");
                    
                    logger.info("fecth solution is successfully!!!"+sol);
                }
                
        }
        catch (Exception e) {
        	//Logger 
        	 logger.error("can't find his paticipation on this question");
        	throw new Exception("can't find his paticipation on this question");
			// TODO Auto-generated catch block
		}
        return sol;
    }

    // Method to fetch function name from the question
    public String fecthFuncName(String questionId,String lang) throws Exception {
        String query = Querry.fecthFuncName;
    	
    	//String query = "SELECT funcName FROM LanguageRelation lr JOIN Languages l ON lr.l_ID = l.l_ID WHERE l.lang_name like ? AND lr.Q_ID like ?";
        try{
        	System.out.println(query);
        	PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lang);
            statement.setString(2, questionId);
            
            System.out.println(lang+"   :   "+questionId);
//        	Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT funcName FROM LanguageRelation lr JOIN Languages l ON lr.l_ID = l.l_ID WHERE l.lang_name = " + lang + " AND lr.Q_ID = " + questionId);
            ResultSet resultSet  =statement.executeQuery();
            System.out.println("result1");
                if (resultSet.next()) {
                	System.out.println("result");
                    String sol= resultSet.getString("funcName");
                    logger.info("fecth funcName is successfully!!!");
                    return sol;
                }
                else {
                	System.out.println("no result set");
                }
                
        }
        catch (Exception e) {
        	//Logger 
        	 logger.error("can't find his paticipation on this question");
        	throw new Exception("can't find his paticipation on this question");
			// TODO Auto-generated catch block
		}
       return "";
    }
    
    public Solution solutionDetails(int solId) throws Exception {
    	    Solution solution = new Solution();
    	    String query = Querry.fetchSolutionDetails;
    	    try{
    	    	PreparedStatement statement = connection.prepareStatement(query);
    	        statement.setInt(1, solId);
    	        ResultSet resultSet = statement.executeQuery();
    	            if (resultSet.next()) {
    	                String mailID = resultSet.getString("mailID");
    	                String Q_ID = resultSet.getString("Q_ID");
    	                String sol = resultSet.getString("Sol");
    	                String status = resultSet.getString("status");
    	                String solvedType = resultSet.getString("solvedType");
    	                Users user = new Users();
    	                user.setMailID(mailID); 
    	                Questions question = new Questions();
    	                question.setQuestionID(Q_ID); 
    	                System.out.println("sol"+sol);
    	                solution.setSolID(solId);
    	                solution.setQID(question);
    	                solution.setUser(user);
    	                solution.setSol(sol);
    	                solution.setStatusFromString(status);
    	                solution.setSolvedTypeFromString(solvedType);
    	            }
    	    } catch (Exception e) {
    	    	logger.error("can't get the solutionDetails");
                throw new Exception("can't get the solutionDetails"); 
    	        //e.printStackTrace();
    	    }
    	    logger.info("fecth the solution details successfully!!!!");
    	    return solution;
    	}
    
    
    // Method to fetch solutions for a completed question
    public List<Solution> fetchSolutions(String questionId, String userId, String attemptStatus) throws Exception {
        List<Solution> solutions = new ArrayList<>();
        String query = Querry.fetchSolutions;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, questionId);
            statement.setString(2, userId);
            statement.setString(3, attemptStatus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Create a new Solution object for each row and add it to the list
                Solution solution = new Solution();
                solution.setSolID(resultSet.getInt("Sol_ID"));
                solution.setSol(resultSet.getString("Sol"));
                solution.setStatusFromString(resultSet.getString("status"));
                solutions.add(solution);
            }
        } catch (SQLException e) {
            // Log
        	logger.error("Can't fetch solutions for this question");
            throw new Exception("Can't fetch solutions for this question");
        }
        logger.info("List of Solution fecth successfully!!!!");
        return solutions;
    }

    // Method to fetch all solutions for a completed question
    public List<Solution> fetchAllSolutions(String questionId, String attemptStatus) throws Exception {
        List<Solution> solutions = new ArrayList<>();
        String query = Querry.fetchAllSolutions;
        try{
        	PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, questionId);
            statement.setString(2, attemptStatus);
            ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Solution solution = new Solution();
                    solution.setSolID(resultSet.getInt("Sol_ID"));
                    solution.setSol(resultSet.getString("Sol"));
                    solution.setStatusFromString(resultSet.getString("status"));
                    // Create a Users object and set it for the solution
                    Users user = new Users();
                    user.setMailID(resultSet.getString("mailID"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setScore(resultSet.getInt("score"));
                    user.setStreak(resultSet.getInt("streak"));
                    solution.setUser(user);
                    solutions.add(solution);
            }
        } catch (SQLException e) {
            // Log 
        	//System.out.println(e.getMessage());
        	logger.error("Can't fetch solutions for this question");
            throw new Exception("Can't fetch solutions for this question");
        }
        logger.info("List of Solution fecth successfully!!!!");
        return solutions;
    }
    
    */
	
	
}


 
