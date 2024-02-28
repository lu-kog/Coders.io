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
 * Servlet implementation class sendClanRequest
 */
@WebServlet("/sendClanRequest")
public class sendClanRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendClanRequest() {
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
		Logger logger = new CommonLogger(sendClanRequest.class).getLogger();
		try {
			String clanID = request.getParameter("clanID");
	        String mailID = request.getParameter("mailID");

	        logger.info(mailID+" sending join request to clan:"+clanID);
	        // Call DAO method to send clan request
	        boolean success = ClanDAO.getObj().sendJoinRequestToClan(clanID, mailID);

	        JSONObject respJson;
	        if (success) {
	        	respJson = JSON.Create(200, "Request sent!");
		        response.getWriter().write(respJson.toString());
	        } else {
	        	throw new Exception("Can't send request!");
	        }
		} catch (Exception e) {
        	JSONObject errJson = JSON.Create(400, e.getMessage());
	        response.getWriter().write(errJson.toString());

		}
		

	}

}
