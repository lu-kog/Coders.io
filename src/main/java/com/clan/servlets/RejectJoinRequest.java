package com.clan.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class RejectJoinRequest
 */
@WebServlet("/RejectJoinRequest")
public class RejectJoinRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RejectJoinRequest() {
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
		Logger logger = new CommonLogger(RejectJoinRequest.class).getLogger();

		String adminID = "";
		String memberID = "";
		String clanID = "";
		try {

			adminID = request.getParameter("adminID");
			memberID = request.getParameter("memberID");
			clanID = request.getParameter("clanID");

			ClanDAO.getObj().removeJoinRequestsOfClan(clanID, memberID);

			logger.info(memberID+"'s request of clan:"+clanID+" is removed by "+adminID);
			
			JSONObject respJson = JSON.Create(200, "Request removed!");
			response.getWriter().write(respJson.toString());

		} catch (Exception e) {
			logger.error("Error on Rejecting a clan join request " + e);
			JSONObject errJson = JSON.Create(400, e.getMessage());
			response.getWriter().write(errJson.toString());
		}
	}

}
