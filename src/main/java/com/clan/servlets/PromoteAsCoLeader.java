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
 * Servlet implementation class PromoteAsCoLeader
 */
@WebServlet("/AdminAction/promoteAsCoLeader")
public class PromoteAsCoLeader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromoteAsCoLeader() {
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
		
		Logger logger = new CommonLogger(PromoteAsCoLeader.class).getLogger();
		try {
			String adminID = request.getParameter("adminID");
			String clanId = ClanDAO.getObj().getClanId(adminID);
			String memberID = request.getParameter("memberID");
			
			boolean flag = ClanDAO.getObj().availInThisClan(memberID, clanId);
			
			if (!flag) {
				throw new Exception("Member not found in your clan.");
			}
			
			boolean isCoAdmin = ClanDAO.getObj().promoteAsCoAdmin(memberID, clanId);
			
			if(isCoAdmin) {
				logger.info(memberID + " is promoted as Co-Admin by the Admin " + adminID + " in the " + clanId + " clan.");
				JSONObject resObj = JSON.Create(200, memberID+" Promoted as Co-Admin ");
				response.getWriter().write(resObj.toString());
			}
			else {
				throw new Exception("Sorry! Something went wrong");
			}
		}
		catch(Exception e) {
			logger.error("can't promote as Co-Admin "+e);
			JSONObject errObj = JSON.Create(400, e.getMessage());
			response.getWriter().write(errObj.toString());
		}
	}

}
