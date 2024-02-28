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

import org.json.JSONObject;

import com.clan.ClanDAO;
import com.mysql.cj.util.Base64Decoder;

import utils.DB;
import utils.JSON;

/**
 * Servlet Filter implementation class ClanValidationFilter
 */
@WebFilter("/createClan")
public class ClanValidationFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public ClanValidationFilter() {
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
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		try {
			String clanName = httpRequest.getParameter("clanName");
	        String mailID = httpRequest.getParameter("mailID");
	        if (mailID == null || mailID.isEmpty()) {
	            throw new Exception("Invalid user: Mail ID is missing.");
	        }else if (alreadyInClan(mailID)) {
				throw new Exception("User is already in a clan.");
			}else if (checkClanName(clanName)) {
				throw new Exception("Clan name already exist. Please choose another name.");
			}
	        else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			JSONObject errJson = JSON.Create(400, e.getMessage());
			response.getWriter().write(errJson.toString());
		}
        


		
	}

	private boolean checkClanName(String clanName) {
		return DB.checkValueisExist(clanName, "Clan", "clanName");
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

}
