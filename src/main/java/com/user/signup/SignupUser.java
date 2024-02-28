package com.user.signup;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.clan.ClanDAO;
import com.user.UserDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class CreateAccount
 */
@WebServlet("/signup")
public class SignupUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger logger = new CommonLogger(SignupUser.class).getLogger();
		/*
		 * Take valid credentials
		 * Create new Account for the user
		 * Create login session 
		 */
		
		try {

			String mailId = request.getParameter("mailID");
			String passwd = request.getParameter("passwd");
			String userName = request.getParameter("userName");

			System.out.println(userName+mailId+passwd);
			
			String hashPassword = BCrypt.hashpw(passwd, BCrypt.gensalt());
			boolean createAc = UserDAO.getObj().createAccount(userName, mailId, hashPassword);

			if (createAc) {
				request.setAttribute("mailID", mailId);
				request.setAttribute("passwd", passwd);
				RequestDispatcher rd = request.getRequestDispatcher("/LoginUser");
		        rd.forward(request, response);
			} else {
				throw new Exception("Sorry! Something went wrong");
			}

		} catch (Exception e) {
			logger.error("Failed to signup " + e);
			JSONObject errObj = JSON.Create(400, e.getMessage());
			response.getWriter().write(errObj.toString());
		}
	}

}
