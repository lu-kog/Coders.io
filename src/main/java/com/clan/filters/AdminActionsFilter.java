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

import org.apache.catalina.valves.JsonAccessLogValve;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet Filter implementation class AdminActionsFilter
 */
@WebFilter("/AdminAction/*")
public class AdminActionsFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public AdminActionsFilter() {
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
		Logger logger = new CommonLogger(AdminActionsFilter.class).getLogger();
		String adminID = request.getParameter("adminID");
		String clanID;
		try {
			clanID = ClanDAO.getObj().getClanId(adminID);
			if (isadmin(clanID, adminID)) {
				chain.doFilter(request, response);
			}else {
				throw new Exception("Access denied!");
			}
		} catch (Exception e) {
			logger.error("Check admin for admin actions"+e);
			JSONObject errJson = JSON.Create(401, "Access denied!");
			response.getWriter().write(errJson.toString());
		}
	}

	private boolean isadmin(String clanID, String adminID) throws Exception {
		return ClanDAO.getObj().isAdmin(clanID, adminID);
	}
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
