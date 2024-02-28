package com.clan.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.DB;
import utils.Generator;
import utils.JSON;

/**
 * Servlet implementation class CreateClan
 */
@WebServlet("/createClan")
public class CreateClan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateClan() {
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
			String mailID = request.getParameter("mailID");
	        String clanName = request.getParameter("clanName");

	        // Generate 8 digit clan ID
	        String newclanID = Generator.ValidNumerical(8, "Clan", "clanID");

	        // Call DAO method to create clan
	        boolean success = ClanDAO.getObj().createClan(newclanID, clanName, mailID);

	        if (success) {
	        	// initiate clan relation with admin
	        	if (ClanDAO.getObj().joinUserInClan(mailID, newclanID)) {
	        		ClanDAO.getObj().changeRoleInClan(mailID, newclanID, "ADMIN");
	        		JSONObject respJson = new JSONObject();
	        		respJson.put("statuscode", 200);
	        		respJson.put("clanID", newclanID);
	        		respJson.put("clanName", clanName);
	        		respJson.put("isAdmin", true);
	        		response.getWriter().write(respJson.toString());
	        	}
	        	
	        } else {
				throw new Exception("Failed to create clan. Please try again.");
	        }
		} catch (Exception e) {
			JSONObject errJson = JSON.Create(400, e.getMessage());
            response.getWriter().write(errJson.toString());
		}
		

	}

}
