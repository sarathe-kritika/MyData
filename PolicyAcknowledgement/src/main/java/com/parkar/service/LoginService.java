package com.parkar.service;

import java.util.List;

import com.parkar.model.Login;

public interface LoginService {

	public List<Login> findByUserName();
	
}
