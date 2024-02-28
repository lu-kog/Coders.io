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
 * Servlet implementation class FetchCompletedSolutionsOfUser
 */
@WebServlet("/FetchCompletedSolutionsOfUser")
public class FetchCompletedSolutionsOfUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchCompletedSolutionsOfUser() {
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
		 * 
		 */
		Logger logger = new CommonLogger(FetchCompletedSolutionsOfUser.class).getLogger();
		String mailID = request.getParameter("mailID");
		
		try {
			JSONArray completedSolutions = UserDAO.getObj().fetchCompletedSolutionsOfAUser(mailID);
			JSONObject success = new JSONObject();
			success.put("statuscode", 200);
			success.put("data", completedSolutions);
			logger.error("fetched solutions of user:"+mailID+" successfully.");
			response.getWriter().write(success.toString());
		} catch (Exception e) {
			JSONObject errorResp = JSON.Create(400, e.getMessage());
			logger.error("error fetching completed solutions of user:"+mailID);
			response.getWriter().write(errorResp.toString());
		}
	}

}
