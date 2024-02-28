package com.user.signup;

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
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import utils.CommonLogger;
import utils.DB;
import utils.JSON;

/**
 * Servlet Filter implementation class ValidationFilter
 */
@WebFilter("/signup")
public class ValidationFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public ValidationFilter() {
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
		 * Take username, mailID, passwd as params
		 * Check null, "", regex - (mail, passwd), already exists (username, mailID)
		 * chain to SignupUser
		 */
		
		Logger logger = new CommonLogger(ValidationFilter.class).getLogger();
		
		try {
			
			String username = request.getParameter("userName");
		    String mailID = request.getParameter("mailID");
		    String passwd = request.getParameter("passwd");

		    JSONObject errJson;
			// Validate parameters for null or empty values
		    if (username == null || username.trim().isEmpty() ||
		            mailID == null || mailID.trim().isEmpty() ||
		            passwd == null || passwd.trim().isEmpty()) {
		    	logger.error("Invalid creds: null or Empty"+username+" "+passwd+" "+mailID);
		    	errJson = JSON.Create(401, "invalid Credentials!");
		        response.getWriter().write(errJson.toString());
		        return; 
		    }
		    
		    // mail check
		    if (!mailID.matches(".+@.+\\.com")) {
		    	errJson = JSON.Create(401, "Invalid email!");
		        response.getWriter().write(errJson.toString());
		        return;
		    }
		    
		    // username, email exist check
		    if (isExistMail(mailID)) {
		    	errJson = JSON.Create(401, "Email already exists!");
		        response.getWriter().write(errJson.toString());
		        return;
			}
		    if (isExistUserName(username)) {
		    	errJson = JSON.Create(401, "Username already taken!");
		        response.getWriter().write(errJson.toString());
		        return;
		    }
		    if(!isValidPassword(passwd)) {
				errJson = JSON.Create(401, "Invalid password pattern!");
		        response.getWriter().write(errJson.toString());
		        return;
			}
			
			chain.doFilter(request, response);
			
		}
		catch(Exception e) {
			logger.error(e);
			JSONObject errJson = JSON.Create(400, "Invalid credentials!");
	        response.getWriter().write(errJson.toString());
		}
	}

	private boolean isExistMail(String mailID) {
        return (DB.checkValueisExist(mailID, "Users", "mailID"));
	}

	private boolean isExistUserName(String username) {
        return DB.checkValueisExist(username, "Users", "userName");
	}

	private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*].*");
    }
	
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
