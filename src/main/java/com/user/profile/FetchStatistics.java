package com.user.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.user.UserDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class FetchStatistics
 */
@WebServlet("/FetchStatistics")
public class FetchStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchStatistics() {
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
		
		// Check if it is his own profile or someothers profile
				/** If own page,
				 * ownPage: true, show chart details, Clan controlls
				 * 
				 * If someOnes profile: 
				 * ownPage: false, just show all details. except answers.
				 */
				
			Logger logger = new CommonLogger(FetchStatistics.class).getLogger();
				// check given mailID and current user's mailID is equal.
				String userMail = request.getParameter("userMail");
				String ownMail = request.getParameter("mailID");
				boolean flag = userMail.equals(ownMail);
				try {
					JSONObject statistics = UserDAO.getObj().getStats(userMail);
					JSONObject data = UserDAO.getObj().getUserProfileData(userMail);
					// this object has successcode and object of stats values
					JSONObject ProfileDataWithStats = new JSONObject();
					ProfileDataWithStats.put("statuscode", 200);
					ProfileDataWithStats.put("isOwnPage", flag);
					ProfileDataWithStats.put("profilePic", "");
					ProfileDataWithStats.put("userData", data);
					ProfileDataWithStats.put("statistics", statistics);
					
					logger.info("User profile statistics fetched successfully for "+userMail);
					

					response.getWriter().write(ProfileDataWithStats.toString());
				} catch (Exception e) {
					logger.error("error fetching stats");
					JSONObject errorResp = JSON.Create(400, e.getMessage());
					response.getWriter().write(errorResp.toString());
				}
	}

}
