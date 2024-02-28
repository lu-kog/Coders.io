package com.user.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.user.UserDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class FetchAttemptedSolutionsOfaUser
 */
@WebServlet("/FetchAttemptedSolutionsOfaUser")
public class FetchAttemptedSolutionsOfaUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchAttemptedSolutionsOfaUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * get mailID from param
		 * fetch all his unfinished solutions
		 * return in JSON
		 */
		Logger logger = new CommonLogger(FetchAttemptedSolutionsOfaUser.class).getLogger();
		String mailID="";
		try {
			mailID = request.getParameter("mailID");
			JSONArray attemptedSolutions = UserDAO.getObj().fetchAllUnfinishedSolutions(mailID);
			JSONObject respJson = JSON.Create(200, "Fetched successfully");
			respJson.put("data", attemptedSolutions);
			
			logger.info("Fetched all unfinished solutions of "+mailID);
			response.getWriter().write(respJson.toString());
			
		} catch (Exception e) {
			logger.error("Error on fetching unfinished solutions of "+mailID+" error:"+e);
			JSONObject errJson = JSON.Create(400, "Problem on fetching unfinished solutions.");
			response.getWriter().write(errJson.toString());
		}
	}

}
