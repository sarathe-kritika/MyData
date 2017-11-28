package com.parkar.jdbcMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.parkar.model.Login;

public class LoginMapper implements RowMapper<Login> {

	@Override
	public Login mapRow(ResultSet rs, int arg1) throws SQLException {
		Login login = new Login();
		System.out.println("Username is"+rs.getString("username"));
		System.out.println("Password is"+rs.getString("password"));
		login.setUsername(rs.getString("username"));
		login.setPassword(rs.getString("password"));
		// TODO Auto-generated method stub
		return login;
	}

}
