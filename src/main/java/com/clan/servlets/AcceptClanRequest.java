package com.clan.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.JSON;

/**
 * Servlet implementation class AcceptClanRequest
 */
@WebServlet("/acceptClanRequest")
public class AcceptClanRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptClanRequest() {
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
		try {
			String adminID = request.getParameter("adminID");
	        String memberID = request.getParameter("memberID");
	        String clanID = request.getParameter("clanID");

	        // Call DAO method to join member in clan
	        boolean success = joinMemberInClan(adminID, memberID, clanID);

	        if (success) {
	        	JSONObject respJson = JSON.Create(200, "Member added.");
	        	response.getWriter().write(respJson.toString());
	        } 

		} catch (Exception e) {
			JSONObject errJson = JSON.Create(400, e.getMessage());
			response.getWriter().write(errJson.toString());
		}
	}
	
	private boolean joinMemberInClan(String adminID, String memberID, String clanID) throws Exception {
        ClanDAO.getObj().removeJoinRequestsOfClan(clanID, memberID);
		return ClanDAO.getObj().joinUserInClan(clanID, memberID);
    }


}
