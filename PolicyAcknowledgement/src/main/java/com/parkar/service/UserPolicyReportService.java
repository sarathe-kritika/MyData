package com.parkar.service;


import java.util.Date;
import java.util.List;

import com.parkar.model.UserPolicyReport;

public interface UserPolicyReportService {
	
	
	public int save(UserPolicyReport user);
	public List<UserPolicyReport> getReport();
	public UserPolicyReport getReportByUserName(String username);
	public int updateStatusByUsername(String username,String statusNda, String statusPolicy,String sysDate);
	

}
