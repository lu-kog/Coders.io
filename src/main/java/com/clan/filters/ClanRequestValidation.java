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
import org.apache.naming.factory.MailSessionFactory;
import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.DB;
import utils.JSON;

/**
 * Servlet Filter implementation class ClanRequestValidation
 */
@WebFilter("/sendClanRequest")
public class ClanRequestValidation extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;
    static Logger logger = new CommonLogger(ClanRequestValidation.class).getLogger();
	
    /**
     * @see HttpFilter#HttpFilter()
     */
    public ClanRequestValidation() {
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
		try {
	        // Check if user is already in any clan
	        String userID = request.getParameter("mailID");
	        if (alreadyInClan(userID)) {
	            throw new Exception("User is already in a clan.");
	        }

	        // Check if clanID is valid
	        String clanID = request.getParameter("clanID");
	        logger.info("validating clan request: clan:"+clanID+" mailID"+userID);
	        
	        if (!isValidClanID(clanID)) {
	            throw new Exception("Invalid clan ID!");
	        }

	        chain.doFilter(request, response);

		} catch (Exception e) {
			JSONObject errJson = JSON.Create(400, e.getMessage());
			response.getWriter().write(errJson.toString());
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	
	private boolean alreadyInClan(String mailID) throws Exception {
        return ClanDAO.getObj().AvailInAnyClan(mailID);
    }
	
	private boolean isValidClanID(String clanID) {
        return DB.checkValueisExist(clanID, "Clan", "clanID");
    }
}
