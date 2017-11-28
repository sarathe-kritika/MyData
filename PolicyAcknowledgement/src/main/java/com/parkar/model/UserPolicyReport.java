package com.parkar.model;

import java.util.Date;

public class UserPolicyReport {

	int userid;
	String username;
	String statusNda;
	public String getStatusNda() {
		return statusNda;
	}


	public void setStatusNda(String statusNda) {
		this.statusNda = statusNda;
	}


	public String getStatusPolicy() {
		return statusPolicy;
	}


	public void setStatusPolicy(String statusPolicy) {
		this.statusPolicy = statusPolicy;
	}

	String statusPolicy;
	String employee_code;
	String date;
	String full_name;
	
	
	
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}


	public String getFull_name() {
		return full_name;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmployee_code() {
		return employee_code;
	}

	public void setEmployee_code(String employee_code) {
		this.employee_code = employee_code;
	}

	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	
	
	
}
