package com.clan.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet Filter implementation class UserLeftFromClanFilter
 */
@WebFilter("/LeftFromClan")
public class UserLeftFromClanFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public UserLeftFromClanFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// user should be in clan as member or Co-admin. 
		// If he is an admin, make someone as next Admin.
		Logger logger = new CommonLogger(UserLeftFromClanFilter.class).getLogger();
		String mailID="";
		try {
			mailID = request.getParameter("mailID");
			String clanID = ClanDAO.getObj().getClanId(mailID);
			System.out.println(clanID);
			
			if (isAdmin(mailID)) {
				// make any co-leader as admin
				String nextAdmin = chooseAdminFromCo_Admins(clanID);
				if (nextAdmin == null) {
					logger.info("No co-admins found on clan:"+clanID);
					nextAdmin = chooseAdminFromMembers(clanID);
					if (nextAdmin== null) { // means, there is no members in this clan. so, delete the clan.
						logger.info("No Members found on clan:"+clanID);
						ClanDAO.getObj().deleteClan(clanID);
					}else {	
						ClanDAO.getObj().makeAdminOfClan(nextAdmin, clanID);
						chain.doFilter(request, response);
					}
				}else {
					ClanDAO.getObj().makeAdminOfClan(nextAdmin, clanID);
					chain.doFilter(request, response);
				}
			}else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			JSONObject errJson = JSON.Create(400, e.getMessage());
			response.getWriter().write(errJson.toString());
		}
		
	}
	
	private String chooseAdminFromMembers(String clanID) throws Exception {
		return ClanDAO.getObj().pickNextAdminFromMembers(clanID);
	}

	private String chooseAdminFromCo_Admins(String clanID) throws Exception {
		return ClanDAO.getObj().pickNextAdminFromCoAdmin(clanID);
	}

	private boolean isAdmin(String mailID) throws Exception {
		String clanID = ClanDAO.getObj().getClanId(mailID);
		return ClanDAO.getObj().isAdmin(clanID, mailID);
	}
	


	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
