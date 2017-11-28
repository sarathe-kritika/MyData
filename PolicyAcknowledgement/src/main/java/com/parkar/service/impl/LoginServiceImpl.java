package com.parkar.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.parkar.jdbcMapper.LoginMapper;
import com.parkar.model.Login;
import com.parkar.service.LoginService;

@Repository(value="loginServiceImpl")
public class LoginServiceImpl implements LoginService{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	@Override
	public List<Login> findByUserName() {
	//	System.out.println("User name in impls"+username);
		String SQL = "select * from login";
		System.out.println("SQL is"+SQL);
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//System.out.println("JDBC TEMPL"+jdbcTemplateObject.query(SQL,new Object[]{username},new LoginMapper()));
		List<Login> login = jdbcTemplateObject.query(SQL,new LoginMapper());
		//.out.println("username is"+username);
		//System.out.println("username is"+login.getPassword());
		return login;
	
}}
