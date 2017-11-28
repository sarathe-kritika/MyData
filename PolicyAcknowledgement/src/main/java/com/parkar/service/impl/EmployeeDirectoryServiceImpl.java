package com.parkar.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.parkar.jdbcMapper.EmployeeDirectoryMapper;
import com.parkar.jdbcMapper.UserPolicyReportMapper;
import com.parkar.model.EmployeeDirectory;
import com.parkar.model.UserPolicyReport;
import com.parkar.service.EmployeeDirectoryService;



@Repository(value="employeeDirectoryServiceImpl")
public class EmployeeDirectoryServiceImpl implements EmployeeDirectoryService {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	
	@Override
	public String save(EmployeeDirectory emp) {
		String username = emp.getUsername();
		String employee_code = emp.getEmployee_code();
		String full_name = emp.getFull_name();
		String email_id = emp.getEmail_id();
		String role = emp.getRole();
		String SQL = "insert into employee_directory (username, employee_code, full_name, email_id, role) values (?, ?, ?, ?, ?)";
		jdbcTemplateObject.update( SQL, username, employee_code, full_name, email_id, role);
		return username;
	}
	
	
	@Override
	public EmployeeDirectory getEmployeeCodeByUserName(String username) {
		String SQL = "select * from employee_directory where username = ?";
		List<EmployeeDirectory> list = jdbcTemplateObject.query(SQL,new Object[]{username}, new EmployeeDirectoryMapper());
		EmployeeDirectory emp;
		System.out.println("UserPolicyReportController"+list.get(0).getFull_name());
		if(list.size()>0){
			emp = (EmployeeDirectory)list.get(0);
		}
		else{
			emp = null;
		}
		
		// TODO Auto-generated method stub
		return emp;
	}


	@Override
	public boolean searchEmployeeCode(String employee_code) {
		boolean flage = false;
		String SQL = "select * from employee_directory where employee_code = ?";
		List<EmployeeDirectory> list = jdbcTemplateObject.query(SQL,new Object[]{employee_code}, new EmployeeDirectoryMapper());
		//EmployeeDirectory emp;
		
		if(list.size()>0 && list.get(0).getEmployee_code()!=null){
			//emp = (EmployeeDirectory)list.get(0);
			flage = true;
		}
		else{
			
			flage = false;
		}
		
		return flage;
	}


	
}	
