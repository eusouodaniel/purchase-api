package com.challenge.purchase.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.purchase.form.auth.AuthForm;
import com.challenge.purchase.service.TokenService;

@RestController
@RequestMapping("/login")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<?> auth(@RequestBody @Valid AuthForm form) {
		UsernamePasswordAuthenticationToken loginData = form.convert();
		try {
			Authentication authentication = authenticationManager.authenticate(loginData);
			String token = tokenService.generate(authentication);
			
			return ResponseEntity.ok().build();
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		 
	}
}