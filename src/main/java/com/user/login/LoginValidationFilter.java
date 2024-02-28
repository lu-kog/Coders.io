package com.user.login;

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

import utils.DB;
import utils.JSON;

/**
 * Servlet Filter implementation class LoginValidationFilter
 */
@WebFilter("/LoginUser")
public class LoginValidationFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public LoginValidationFilter() {
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
		/*
		 * Take mailID, Passwd as params
		 * null, "" check, Validate on DB.
		 * Chain with LoginUser
		 */
	
		String mailID = request.getParameter("mailID");
	    String passwd = request.getParameter("passwd");

	    if (mailID == null || mailID.trim().isEmpty() || passwd == null || passwd.trim().isEmpty() || (!validMailID(mailID))) {
	        Logger.getLogger(LoginValidationFilter.class).error("Login credentials failed:"+mailID+" pass:"+passwd);
	    	JSONObject errJson = JSON.Create(401, "Invalid Credentials!");
	        response.getWriter().write(errJson.toString());
	        return;
	    }else {
	    	chain.doFilter(request, response);
		}
		
	}

	private boolean validMailID(String mailID) {
		return DB.checkValueisExist(mailID, "Users", "mailID");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
