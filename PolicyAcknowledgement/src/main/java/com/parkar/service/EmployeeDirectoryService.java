package com.parkar.service;

import com.parkar.model.EmployeeDirectory;

public interface EmployeeDirectoryService {

	public String save(EmployeeDirectory emp);
	public EmployeeDirectory getEmployeeCodeByUserName(String username); 
	public boolean searchEmployeeCode(String empcode);
	/*public EmployeeDirectory getFullNameByUserName(String username); 
	public EmployeeDirectory getEmailByUserName(String username);
*/}
