package com.revature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import com.revature.User;
import com.revature.UserDao;
import com.revature.ConnectionUtil;


public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public login() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); 
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(false);
		
		try {
			if (loginChecker(username, password)) {
				session = request.getSession();
				User user = getUser(username);
				session.setAttribute("userName", username);
				session.setAttribute("userType", user.getUserType());
				if (user != null) {
					if (user.getUserType().equals("employee")) {
						String result = "employee";
						response.setContentType("text/html");
						response.getWriter().write(result);
						response.sendRedirect("Employee.html");
					} else if (user.getUserType().equals("manager")) {
						String result = "manager";
						response.setContentType("text/html");
						response.getWriter().write(result);
						response.sendRedirect("Manager.html");
					}
				}
			} else {
				String result = "false";
				response.setContentType("text/html");
				response.getWriter().write(result);
			}
			}catch(SQLException e) {
				e.printStackTrace();
		}
	}
	
	public static boolean loginChecker(String username, String password) throws IOException {
		ConnectionUtil connection = new ConnectionUtil();
		boolean bool = false;
		UserDao userDao = new UserDao(connection.getConnection());
		User user = userDao.validate(username, password);
			
			if(user != null) {
				return true;
			}
		
		return bool;
	}
	
	public User getUser(String username) throws SQLException, IOException {
		ConnectionUtil connection = new ConnectionUtil();
		UserDao userDao = new UserDao(connection.getConnection());
		User user = userDao.getUserByUsername(username);
		connection.close();
		return user;
	}

}
