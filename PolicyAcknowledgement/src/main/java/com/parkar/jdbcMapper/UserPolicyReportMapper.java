package com.parkar.jdbcMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.parkar.model.UserPolicyReport;

public class UserPolicyReportMapper implements RowMapper<UserPolicyReport>{

	@Override
	public UserPolicyReport mapRow(ResultSet rs, int arg1) throws SQLException {
		UserPolicyReport userRep =  new UserPolicyReport();
		userRep.setUserid(rs.getInt("userid"));
		userRep.setUsername(rs.getString("username"));
		userRep.setStatusNda(rs.getString("statusNda"));
		userRep.setStatusPolicy(rs.getString("statusPolicy"));
		userRep.setEmployee_code(rs.getString("employee_code"));
		userRep.setDate(rs.getString("sysdate"));
		userRep.setFull_name(rs.getString("full_name"));
		
		System.out.println("Date from data base is "+rs.getString("sysdate"));
		System.out.println("Date in user policy is"+userRep.getDate());
		return userRep;
	}


}
