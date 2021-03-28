package com.challenge.purchase.form.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthForm {
	private String email;
	private String password;
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
