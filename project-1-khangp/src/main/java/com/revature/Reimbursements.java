package com.revature;

public class Reimbursements {
	private String id;
	private String username;
	private String cost;
	private String reimbursementType;
	private String reimbursementStatus;
	
	
	
	public Reimbursements() {
		super();
		
	}
	
	public Reimbursements(String id, String username, String cost, String reimbursementType, String reimbursementStatus) {
		super();
		this.id = id;
		this.username = username;
		this.cost = cost;
		this.reimbursementType = reimbursementType;
		this.reimbursementStatus = reimbursementStatus;
	}
	public Reimbursements(String username, String cost, String reimbursementType) {
		super();
		this.username = username;
		this.cost = cost;
		this.reimbursementType = reimbursementType;
	}

	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getReimbursementType() {
		return reimbursementType;
	}
	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}
	public String getReimbursementStatus() {
		return reimbursementStatus;
	}
	public void setReimbursementStatus(String reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}
	
	@Override
	public String toString() {
		return "Reimbursements [id=" + id + ", username=" + username + ", cost=" + cost + ", reimbursementType=" + reimbursementType + 
				", reimbursementStatus=" + reimbursementStatus + "]";
	}

}