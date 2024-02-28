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
 * Servlet implementation class FetchAuthoredQuestions
 */
@WebServlet("/FetchAuthoredQuestions")
public class FetchAuthoredQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchAuthoredQuestions() {
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
		 * get mailID from request
		 * get all questions which he created.
		 * create a json array and send.
		 */
		
		Logger logger = new CommonLogger(FetchAuthoredQuestions.class).getLogger();
		String mailID = request.getParameter("mailID");
		try {
			JSONArray authoredQuestions = UserDAO.getObj().getAllAuthoredQuestions(mailID);
			JSONObject respJson = new JSONObject();
			respJson.put("statuscode", 200);
			respJson.put("data", authoredQuestions);
			logger.info("fetched solutions successfully");
			response.getWriter().write(respJson.toString());
		} catch (Exception e) {
			logger.error("error on fetching authored questions."+e);
			JSONObject errorResp = JSON.Create(500, e.getMessage());
			response.getWriter().write(errorResp.toString());
		}
	}

}
