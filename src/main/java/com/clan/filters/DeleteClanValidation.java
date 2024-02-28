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
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.clan.ClanDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet Filter implementation class DeleteClanValidation
 */
@WebFilter("/DeleteClan")
public class DeleteClanValidation extends HttpFilter implements Filter {
    static Logger logger = new CommonLogger(DeleteClanValidation.class).getLogger();
    private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public DeleteClanValidation() {
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
		HttpServletResponse httpResponse = (HttpServletResponse) response;

        String mailId = request.getParameter("mailId");
        String clanID = null;
		try {
			clanID = ClanDAO.getObj().getClanId(mailId);
	        boolean isAdmin = ClanDAO.getObj().isAdmin(clanID, mailId);
	        if (isAdmin) {
	            logger.info(mailId+" going to delete clan: "+clanID);
	            chain.doFilter(request, response);
	        } else {
	            logger.error(mailId+" is not admin of "+clanID);
	            throw new Exception("You're not Admin of this clan.");
	        }
	    } catch (Exception e) {
	        logger.error("Error On validating deletion of clan:"+clanID+" error:"+ e);
	        JSONObject errJson = JSON.Create(400, e.getMessage());
	        response.getWriter().write(errJson.toString());
	    }
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
