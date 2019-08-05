package com.revature;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.ConnectionUtil;
import com.revature.Reimbursements;
import com.revature.User;
import com.revature.UserDao;



public class Employee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Employee() {
		super();
		
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hi");
		HttpSession session = request.getSession(false);
		String commandType = request.getParameter("command");
		String methodName = request.getParameter("methodname");
		String userType = (String) session.getAttribute("userType");
		//System.out.println(methodName);
		if (userType.equals("employee")) {
			if (methodName != null) {
				if (methodName.equals("loademp")) {
					displayEmployeeInfo(session, response);
				} else if (methodName.equals("loadAllReim")) {
					System.out.println("Hello");
					displayUserReim(session, response);
				} else if (methodName.equals("newReim")) {
					createReim(session, request, response);
				} 
			}
			if (commandType != null) {
				if (commandType.equals("LOGOUT")) {
					logOut(session, response);
				}
			}
		}
		
	}
	
	public void displayEmployeeInfo(HttpSession session, HttpServletResponse response) throws IOException {
		String userName = (String) session.getAttribute("username");
		User user = getUser(userName);	
		System.out.println(user.getFirstName());
		response.setContentType("text/html");
		response.getWriter().write(generateUserJson(user));
	}

	private User getUser(String userName) throws IOException {
		ConnectionUtil connection = new ConnectionUtil();
		UserDao userDao = new UserDao(connection.getConnection());
		User user = userDao.getUserByUsername(userName);
		connection.close();
		return user;
	}


	public void displayUserReim(HttpSession session, HttpServletResponse response) throws IOException {
		try {
			System.out.println(generateReimbJson(getReim(session)));
			response.setContentType("text/html");
			response.getWriter().write(generateReimbJson(getReim(session)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<Reimbursements> getReim(HttpSession session) throws IOException{
		ConnectionUtil connection = new ConnectionUtil();
		UserDao userDao = new UserDao(connection.getConnection());
		String userName = (String) session.getAttribute("username");
		List<Reimbursements> reimbursements = userDao.getEmployeeReim(userName);
		connection.close();
		return reimbursements;
	}


	public void logOut(HttpSession session, HttpServletResponse response) throws IOException {
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("Default.html");
	}
	
	public void createReim(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ConnectionUtil connection = new ConnectionUtil();
		UserDao userDao = new UserDao(connection.getConnection());
		String userName = (String) session.getAttribute("username");
		String cost = request.getParameter("cost");
		String newType = request.getParameter("newType");

		Reimbursements newReim = new Reimbursements(userName, cost, newType);
		boolean status = userDao.createReimReq(newReim);
		connection.close();
		response.setContentType("text/html");
		if (status)
			response.getWriter().write("true");
		else
			response.getWriter().write("false");
	}
	
	private String generateReimbJson( List<Reimbursements> reim) {
		List<Reimbursements> reimbursements = reim;
		String userString = new Gson().toJson(reimbursements);
		return userString;
	}
	private String generateUserJson(User user) {
		User jsonUser = user;
		String userString = new Gson().toJson(jsonUser);
		return userString;
	}
	

}
