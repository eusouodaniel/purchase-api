package com.challenge.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Qualifier("authenticationManagerBean")
	@Autowired
	AuthenticationManager authenticationManager;
	
	public Authentication auth(UsernamePasswordAuthenticationToken loginData) {
		return authenticationManager.authenticate(loginData);
	}
}
