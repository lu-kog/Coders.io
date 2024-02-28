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
 * Servlet implementation class LeftFromClan
 */
@WebServlet("/LeftFromClan")
public class LeftFromClan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static Logger logger = new CommonLogger(LeftFromClan.class).getLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeftFromClan() {
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
		String clanId = null;
		try {
			String mailId = request.getParameter("mailID");
			logger.info(mailId+" leaving from clan:"+clanId);

			clanId = ClanDAO.getObj().getClanId(mailId);
			
			
			boolean removed = ClanDAO.getObj().removeUserFromClan(mailId, clanId);
			
			if(removed) {
				logger.info(mailId + " left from the clan:" + clanId);
				JSONObject resObj = JSON.Create(200, "You left the clan");
				response.getWriter().write(resObj.toString());
			}
			else {
				throw new Exception("Sorry! Something went wrong");
			}
		}
		catch(Exception e) {
			logger.error("Error on lefting from a clan:"+clanId+" error:"+e);
			JSONObject errObj = JSON.Create(400, e.getMessage());
			response.getWriter().write(errObj.toString());
		}
	}

}
