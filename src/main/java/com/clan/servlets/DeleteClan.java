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
 * Servlet implementation class DeleteClan
 */
@WebServlet("/deleteClan")
public class DeleteClan extends HttpServlet {
	private static final long serialVersionUID = 1L;
    static Logger logger = new CommonLogger(DeleteClan.class).getLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteClan() {
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
			String mailId = request.getParameter("mailId");
			String clanId = ClanDAO.getObj().getClanId(mailId);
			
			boolean clanDeleted = ClanDAO.getObj().deleteClan(clanId);
			if(clanDeleted) {
				logger.info("Clan deleted successfully");
				JSONObject respJson = JSON.Create(200, "Clan deleted successfully!");
				response.getWriter().write(respJson.toString());
			}
			else {
				throw new Exception("Sorry! can't delete Clan. Please contact admin.");
			}
			
		}
		catch(Exception e) {
			JSONObject errorResp = JSON.Create(400, e.getMessage()); 
            logger.error("Something error on deletion of clan: "+e);
            response.getWriter().write(errorResp.toString());
		}
 
	}
	

}
