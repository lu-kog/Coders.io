package com.clan.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.clan.Clan;
import com.clan.ClanDAO;
import com.user.UserDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class GetClanDetails
 */
@WebServlet("/GetClanDetails")
public class GetClanDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetClanDetails() {
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
		 * get clanID and usermail from req
		 * get clan details such as
		 * clan members like along with their roles
		 * clan join requests list
		 * Get user role and add it to cookie.
		 */
		
		Logger logger = new CommonLogger(GetClanDetails.class).getLogger();
		String user = request.getParameter("user");
		String ownMail = request.getParameter("mailID");
		boolean flag = user.equals(ownMail);
		try {
			JSONObject clanDetails = ClanDAO.getObj().getClanDetails(user);
			
			JSONObject respJson = new JSONObject();
			respJson.put("statuscode", 200);
			respJson.put("isOwnPage", flag);
			if (flag) {
				// get role of clan
				String clanID = ClanDAO.getObj().getClanId(ownMail);
				String role = ClanDAO.getObj().getClanRole(ownMail, clanID);
				
				// give access to controll clan
				List<Cookie> cookies = new ArrayList<Cookie>();
				cookies.add(new Cookie("mailID", ownMail));
				cookies.add(new Cookie("clanID", clanID));
				cookies.add(new Cookie("role", role));
				cookies.forEach(x-> response.addCookie(x));
				
				
			}
			respJson.put("clanData", clanDetails);
			logger.info("clan details fetched successfully");
			
			response.getWriter().write(respJson.toString());
		} catch (Exception e) {
			JSONObject errorResp = JSON.Create(400, e.getMessage());
			logger.error("error on fetching clan details"+e);
			response.getWriter().write(errorResp.toString());
		}
	}

}
