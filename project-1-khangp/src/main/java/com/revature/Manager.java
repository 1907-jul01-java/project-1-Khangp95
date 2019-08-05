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

public class Manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manager() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String methodname = request.getParameter("methodname");
		String commandType = request.getParameter("command");
		if (session != null) {
			String userType = (String) session.getAttribute("userType");
			if (userType.equals("manager")) {
				if (methodname != null) {
					if (methodname.equals("loademp")) {
						displayEmployeeInfo(session, response);
					} else if (methodname.equals("loadAllReim")) {
						displayUserReim(response);
					} else if (methodname.equals("handleReim")) {
						String reimId = request.getParameter("reimId");
						String reimStat = request.getParameter("reimCode");
					//	String userName = (String) session.getAttribute("userName");
						if (reimStat.equals("Approved")) {
							updateReimStatus(reimId, true, response);
						} else if (reimStat.equals("Denied")) {
							updateReimStatus(reimId, false, response);
						}
					}else if (methodname.equals("loadAllEmp")) {
						getUsers();
				}
				if (commandType != null) {
					if (commandType.equals("LOGOUT")) {
						logOut(session, response);
					}
				}
			
				}
	}
		}
				else {
				logOut(session, response);
				return;
		}
	}
	
	public void displayEmployeeInfo(HttpSession session, HttpServletResponse response) throws IOException {
		String userName = (String) session.getAttribute("username");
		User user = getUser(userName);			
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
	
	public void displayUserReim(HttpServletResponse response) throws IOException {
		try {
			response.setContentType("text/html");
			response.getWriter().write(generateReimbJson(getReim()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public List<Reimbursements> getReim() throws IOException{
		ConnectionUtil connection = new ConnectionUtil();
		UserDao userDao = new UserDao(connection.getConnection());
		List<Reimbursements> reimbursements = userDao.getAll();
		connection.close();
		return reimbursements;
	}
	
	public List<User> getUsers() throws IOException{
		ConnectionUtil connection = new ConnectionUtil();
		UserDao userDao = new UserDao(connection.getConnection());
		List<User> users = userDao.getUsers();
		connection.close();
		return users;
	}
	
	public void updateReimStatus(String reimId, boolean Accept, HttpServletResponse response) throws IOException {
		boolean isSuccess = false;
		boolean reimStatus = false;
		try {
			ConnectionUtil connection = new ConnectionUtil();
			UserDao userDao = new UserDao(connection.getConnection());
			reimStatus = userDao.updateReimStatus(reimId, Accept);
			connection.close();
			isSuccess = true;
		} catch (Exception ex) {
			isSuccess = false;
		}
		if (isSuccess) {
			if (reimStatus) {
				String result = "true";
				String stringStat = new Gson().toJson(result);
				response.setContentType("text/html");
				response.getWriter().write(stringStat);
			} else {
				String result = "false";
				String stringStat = new Gson().toJson(result);
				response.setContentType("text/html");
				response.getWriter().write(stringStat);
			}
		}
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
	
	public void logOut(HttpSession session, HttpServletResponse response) throws IOException {
		if (session != null) {
		//	String userType = (String) session.getAttribute("username");
			session.invalidate();
		}
		response.sendRedirect("Default.html");
	}

}