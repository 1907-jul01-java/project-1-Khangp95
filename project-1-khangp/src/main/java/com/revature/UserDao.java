package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User>{
	Connection connection;
	
	public UserDao(Connection connection) {
		super();
		this.connection = connection;
	}

	public List<User> getUsers() {
		User user;
		List<User> Users = new ArrayList<>();
		try  {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from Users");
			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getString("id"));
				user.setUserName(resultSet.getString("userName"));
				user.setPassword(resultSet.getString("password"));	
				user.setUserType(resultSet.getString("userType"));	
				user.setFirstName(resultSet.getString("firstName"));
				user.setLastName(resultSet.getString("lastName"));
				Users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return Users;
	}
	
	public User getUserByUsername(String username) {
		User user = null;

		try { 
			PreparedStatement pStatement = connection.prepareStatement("select * from users where username = ?");
			pStatement.setString(1, username);
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				String id = resultSet.getString("id");
				String userName = resultSet.getString("username");
				String password = resultSet.getString("password");
				String userType = resultSet.getString("userType");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				
				user = new User( id, userName, password, userType, firstName, lastName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User validate (String username, String password) {
		User user = null;

		try { 
			PreparedStatement pStatement = connection.prepareStatement("select * from users where username = ? and password = ?");
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			ResultSet resultSet = pStatement.executeQuery();
			while(resultSet.next()) {
				String id = resultSet.getString("id");
				String userName = resultSet.getString("username");
				String passWord = resultSet.getString("password");
				String userType = resultSet.getString("userType");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				
				user = new User( id, userName, passWord, userType, firstName, lastName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<Reimbursements> getEmployeeReim(String Username) {
		Reimbursements reimb;
    	List<Reimbursements> reimbursements = new ArrayList<>();
        try {
        	 PreparedStatement pStatement = connection.prepareStatement("select * from Reimbursements where username = ?");
        	 pStatement.setString(1, Username);
	         ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
            	reimb = new Reimbursements();
            	reimb.setId(resultSet.getString("id"));
				reimb.setUsername(resultSet.getString("username"));
				reimb.setCost(resultSet.getString("cost"));
				reimb.setReimbursementType(resultSet.getString("reimbursementType"));
				reimb.setReimbursementStatus(resultSet.getString("reimbursementStatus"));
				
				reimbursements.add(reimb);
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        System.out.println("Hello");
        return reimbursements;
    }

	
    public List<Reimbursements> getAll() {
    	Reimbursements reimb;
    	List<Reimbursements> reimbursements = new ArrayList<>();
        try {
        	 PreparedStatement pStatement = connection.prepareStatement("select * from Reimbursements");
	         ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
            	reimb = new Reimbursements();
            	reimb.setId(resultSet.getString("id"));
				reimb.setUsername(resultSet.getString("username"));
				reimb.setCost(resultSet.getString("cost"));
				reimb.setReimbursementType(resultSet.getString("reimbursementType"));
				reimb.setReimbursementStatus(resultSet.getString("reimbursementStatus"));
				
				reimbursements.add(reimb);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return reimbursements;
    }

	public boolean createReimReq(Reimbursements reim) {
		boolean reimStatus = false;
		 try {
	            PreparedStatement pStatement = connection.prepareStatement("insert into Reimbursements(username,cost,"
	            		+ "reimbursementType,reimbursementStatus) values(?,?,?,?)");
	        	pStatement.setString(1, reim.getUsername());
	        	pStatement.setString(2, reim.getCost());
	            pStatement.setString(3, reim.getReimbursementType());
	            pStatement.setString(4, reim.getReimbursementStatus());
	            pStatement.executeUpdate();  
	            reimStatus = true;
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return reimStatus;
	        }
		 return reimStatus;
	    }
	public boolean updateReimStatus(String reimId2, boolean approval) {
		boolean approvalStatus = false;;
		String reimId = reimId2;
		boolean status = approval;
		try {
			if(status) {
			PreparedStatement pStatement = connection.prepareStatement("update Reimbursements set reimbursementStatus = 'Approved' where id = ?");
		    pStatement.setString(1, reimId );
		    pStatement.executeQuery();
		    approvalStatus = true;
			}
			else {
            PreparedStatement pStatement = connection.prepareStatement("update Reimbursements set reimbursementStatus = 'Denied' where id = ?");
            pStatement.setString(1, reimId );
            pStatement.executeQuery();
            approvalStatus = false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return approvalStatus;
	}
	public void approveRequest(int id ) {
		    	try {
		            PreparedStatement pStatement = connection.prepareStatement("update Reimbursements set reimbursementStatus = 'Approved' where id = ?");
		            pStatement.setInt(1, id );
		            pStatement.executeQuery();
		        } catch (SQLException e) {
		        }
		    }
	public void denyRequest(int id) {
		try {
            PreparedStatement pStatement = connection.prepareStatement("update Reimbursements set reimbursementStatus = 'Denied' where id = ?");
            pStatement.setInt(1, id );
            pStatement.executeQuery();
        } catch (SQLException e) {
        }
	}

	@Override
	public void insert(User e) {
		
	}

	@Override
	public void select() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void delete() {
		
	}

	
	
	
	



}