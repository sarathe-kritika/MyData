package com.parkar.dao;

import java.util.List;

import com.parkar.model.UserPolicyReport;

public interface IUserPolicyReportDao {
	
	public void save(UserPolicyReport user);
	public List<UserPolicyReport> getReport();
	
	

}
