package com.clan.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class KickoutMember
 */
@WebServlet("/KickoutMemberFromClan")
public class KickoutMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KickoutMember() {
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
		
		Logger logger = new CommonLogger(KickoutMember.class).getLogger();
		try {
			String memberID = request.getParameter("memberID");
			String clanId = ClanDAO.getObj().getClanId(memberID);
			String adminID = request.getParameter("adminID");
			
			
			boolean kickOutTheUser = ClanDAO.getObj().removeUserFromClan(memberID, clanId);
			
			if(kickOutTheUser) {
				logger.info(memberID + " is removed from the clan " + clanId + "by Admin "+adminID);
				JSONObject resObj = JSON.Create(200, "Admin removed the user " + memberID);
				response.getWriter().write(resObj.toString());
			}
			else {
				throw new Exception("Can't remove user!");
			}
		}
		catch(Exception e) {
			logger.error("Can't remove user from this clan"+e);
			JSONObject resObj = JSON.Create(400, e.getMessage());
			response.getWriter().write(resObj.toString());
		}
	}

}
