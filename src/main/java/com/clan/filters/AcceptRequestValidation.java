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
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.DB;
import utils.JSON;

/**
 * Servlet Filter implementation class AcceptRequestValidation
 */
@WebFilter("/acceptClanRequest")
public class AcceptRequestValidation extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public AcceptRequestValidation() {
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
		Logger logger = new CommonLogger(AcceptRequestValidation.class).getLogger();
		// Admin and Co-Admins can accept join requests;
		// request exists check
		
		String adminID = "";
		String memberID = "";
		String clanID="";
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			adminID = request.getParameter("adminID");
	        memberID = request.getParameter("memberID");
	        clanID = request.getParameter("clanID");

	        
	        if (!requestExists(memberID, clanID)) {
				throw new Exception("Request not found!");
			}
	        
	        if (isAdmin(clanID, adminID) || isCoAdmin(clanID, adminID)) {
	        	if (alreadyInClan(memberID)) {
		            throw new Exception("Member is already in a clan.");
		        }else {
		        	chain.doFilter(request, response);	
				}
			}else {
	            throw new Exception("Invalid request! You are not admin of the clan.");
			}
	    
	        
		} catch (Exception e) {
			logger.error("Error on Accepting clan join request "+ e);
			JSONObject errJson = JSON.Create(400, e.getMessage());
			response.getWriter().write(errJson.toString());
		}
	}

	private boolean requestExists(String memberID, String clanID) throws Exception {
		// check clan relation if the request is still exists.
		return ClanDAO.getObj().joinReqExists(memberID, clanID);
	}

	private boolean isCoAdmin(String clanID, String adminID) throws Exception {
		return ClanDAO.getObj().isCoAdminOfThisClan(adminID, clanID);
	}

	private boolean alreadyInClan(String memberID) throws Exception {
        return ClanDAO.getObj().AvailInAnyClan(memberID);
    }

    
    private boolean isAdmin(String clanID, String adminID) throws Exception {
        return ClanDAO.getObj().isAdmin(clanID, adminID);
    }
    
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

    
}
