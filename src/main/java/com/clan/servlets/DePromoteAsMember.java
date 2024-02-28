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
 * Servlet implementation class DePromoteAsMember
 */
@WebServlet("/dePromoteAsMember")
public class DePromoteAsMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DePromoteAsMember() {
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
		 * Alter user relation table and set coAdmin as member in role.
		 */
		
		Logger logger = new CommonLogger(DePromoteAsMember.class).getLogger();
		try {
			String adminID = request.getParameter("mailId");
			String clanId = request.getParameter("clanId");
			String co_adminID = request.getParameter("co_adminID");
			
			boolean isCoAdmin = ClanDAO.getObj().dePromoteAsMember(co_adminID, clanId);
			
			if(isCoAdmin) {
				logger.info(co_adminID + " is promoted as Co-Admin by the Admin " + adminID + " in the " + clanId + " clan.");
				JSONObject resObj = JSON.Create(200, "Promoted as Co-Admin " + co_adminID);
				response.getWriter().write(resObj.toString());
			}
			else {
				throw new Exception("Sorry! Something went wrong");
			}
		}
		catch(Exception e) {
			logger.error("Can't depromote coAdmin"+e);
			JSONObject errObj = JSON.Create(400, e.getMessage());
			response.getWriter().write(errObj.toString());
		}
	}

}
