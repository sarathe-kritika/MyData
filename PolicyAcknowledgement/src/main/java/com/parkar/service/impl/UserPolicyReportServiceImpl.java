package com.parkar.service.impl;


import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.parkar.jdbcMapper.UserPolicyReportMapper;
import com.parkar.model.UserPolicyReport;
import com.parkar.service.UserPolicyReportService;

@Repository(value="userPolicyReportServiceImpl")
public class UserPolicyReportServiceImpl implements UserPolicyReportService{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	
	@Override
	public int save(UserPolicyReport user) {
		String username = user.getUsername();
		System.out.println("username is"+username);
		String statusNda = user.getStatusNda();
		String statusPolicy = user.getStatusPolicy();
		String full_name = user.getFull_name();
		System.out.println("Stratus is "+statusNda);
		String employee_code = user.getEmployee_code();
		
		 String SQL = "insert into user_policy_report (username, statusNda, employee_code,full_name,statusPolicy) values (?, ?, ?,?,?)";
	      jdbcTemplateObject.update( SQL, username, statusNda,employee_code,full_name,statusPolicy);
	     // System.out.println("Created Record username = " + username + " Status = " + status);
	      return user.getUserid();
	}

	@Override
	public List<UserPolicyReport> getReport() {
		 UserPolicyReportMapper policy= new UserPolicyReportMapper();
		System.out.println("get Date"+policy.toString());
		String SQL = "select * from user_policy_report";
	      List <UserPolicyReport> report = jdbcTemplateObject.query(SQL, 
	                                new UserPolicyReportMapper());
	      System.out.println("Response from db"+report);
	      return report;
	      
	}

	@Override
	public UserPolicyReport getReportByUserName(String username) {
		String SQL = "select * from user_policy_report where username = ?";
		List<UserPolicyReport> list =  jdbcTemplateObject.query(SQL,new Object[]{username}, new UserPolicyReportMapper());
		UserPolicyReport user;
	
		if(list.size()>0){
			user = (UserPolicyReport)list.get(0);
		}
		else{
			user = null;
		}
		return user;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int updateStatusByUsername(String username, String statusNda,String statusPolicy,String sysdate) {
		System.out.println("username is="+username);
		System.out.println("statusNda is="+statusNda);
		System.out.println("statusPolicy is="+statusPolicy);
		System.out.println("sysdate is="+sysdate);
	String query="update user_policy_report set statusNda='"+statusNda+"',statusPolicy='"+statusPolicy+"',sysdate ='"+sysdate+"' where username='"+username+"' ";  
	  return jdbcTemplateObject.update(query);  
	}

}
