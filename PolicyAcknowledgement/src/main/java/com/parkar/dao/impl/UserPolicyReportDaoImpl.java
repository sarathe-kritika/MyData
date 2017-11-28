package com.parkar.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.parkar.dao.IUserPolicyReportDao;
import com.parkar.jdbcMapper.UserPolicyReportMapper;
import com.parkar.model.UserPolicyReport;

public class UserPolicyReportDaoImpl implements IUserPolicyReportDao{

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	@Override
	public void save(UserPolicyReport user) {
		UserPolicyReport userRep = new UserPolicyReport();
		String username = userRep.getUsername();
		String status = userRep.getStatusNda();
		 String SQL = "insert into user_policy_report (username, status) values (?, ?)";
	      jdbcTemplateObject.update( SQL, username, status);
	      System.out.println("Created Record username = " + username + " Status = " + status);
	      return;
	}

	@Override
	public List<UserPolicyReport> getReport() {
		String SQL = "select * from user_policy_report";
	      List <UserPolicyReport> report = jdbcTemplateObject.query(SQL, 
	                                new UserPolicyReportMapper());
	      return report;
	      
	}

}
