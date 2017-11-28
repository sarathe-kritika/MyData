package com.parkar.jdbcMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.parkar.model.EmployeeDirectory;


public class EmployeeDirectoryMapper implements RowMapper<EmployeeDirectory>{

	@Override
	public EmployeeDirectory mapRow(ResultSet rs, int arg1) throws SQLException {
		EmployeeDirectory emp = new EmployeeDirectory();
		emp.setEmployee_code(rs.getString("employee_code"));
		emp.setFull_name(rs.getString("full_name"));
		emp.setUsername(rs.getString("username"));
		emp.setEmail_id(rs.getString("email_id"));
		emp.setRole(rs.getString("role"));
		return emp;
	}

}
