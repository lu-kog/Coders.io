package com.clan.filters;

import java.io.IOException;
import java.lang.reflect.Member;

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
 * Servlet Filter implementation class KickoutUserFromClan
 */
@WebFilter("/KickoutMemberFromClan")
public class KickoutUserFromClanFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;
    static Logger logger = new CommonLogger(KickoutUserFromClanFilter.class).getLogger();
	/**
     * @see HttpFilter#HttpFilter()
     */
    public KickoutUserFromClanFilter() {
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
		
		// Admin can remove anyone
		// Co-leaders can remove only members of the clan
		// members can't remove others.
		String adminID="";
		String clanID="";
		String memberID="";
		try {
			adminID = request.getParameter("adminID"); // we should get from cookie
	        memberID = request.getParameter("memberID");
	        clanID = ClanDAO.getObj().getClanId(adminID);

	        if (adminID.equals(memberID)) {
				throw new Exception("You can't kickout yourself.");
			}
	        
	        if (AvailInThisClan(memberID, clanID)) {
	        	if (isAdmin(clanID, adminID)) {
		        	chain.doFilter(request, response);
		        }else if (isCoAdmin(clanID, adminID)) {
		        	if (isClanMember(memberID, clanID)) {
		        		chain.doFilter(request, response);
		        	}else {
			            throw new Exception("You can't remove this member from clan.");
					}
				}else {
					throw new Exception("You don't have access to remove members.");
				}
			}
	        
		} catch (Exception e) {
			logger.error("Error on removing member:"+memberID+" AdminID:"+adminID);
			JSONObject errJson = JSON.Create(400, e.getMessage());
			response.getWriter().write(errJson.toString());
		}
		
	}

	 private boolean AvailInThisClan(String memberID, String clanID) throws Exception {
		return ClanDAO.getObj().availInThisClan(memberID, clanID);
	}

	private boolean isCoAdmin(String adminID, String clanID) throws Exception {
		return ClanDAO.getObj().isCoAdminOfThisClan(adminID, clanID);
	}

	private boolean isClanMember(String memberID, String clanID) throws Exception {
		 String role = ClanDAO.getObj().getClanRole(memberID, clanID);
		 return role.equals("MEMBER");

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
