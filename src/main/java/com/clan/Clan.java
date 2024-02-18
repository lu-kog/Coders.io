package com.clan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Clan")
public class Clan extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mailId = request.getParameter("mailId");
		String passwd = request.getParameter("passwd");
		String role = request.getParameter("role");
		
		String option;
		int joinClan = 1;
		int createClan = 2;
		int userChoice = Integer.parseInt(request.getParameter("userChoice"));
		String clanId = request.getParameter("clanId");
		String clanName = request.getParameter("clanName");
		String username = request.getParameter("username");
		
		AddUser add = new AddUser(mailId, passwd, role);
		
		add.addLogin(mailId, passwd, role);
		add.addUsers(mailId, username);
			
		if(userChoice == joinClan) {
			JoinClan join = new JoinClan();
			
			String clanJoined = join.joinClan(mailId, clanId);
			
			response.getWriter().write(clanJoined);
		}
		else {
			CreateClan create = new CreateClan();
			
			String clanCreated = create.createClan(clanId, clanName);
			
			response.getWriter().write(clanCreated);
		}
		
		
		
	}

}
