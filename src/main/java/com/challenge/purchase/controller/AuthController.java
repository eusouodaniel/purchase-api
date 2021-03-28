package com.challenge.purchase.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.purchase.dto.auth.TokenDto;
import com.challenge.purchase.form.auth.AuthForm;
import com.challenge.purchase.service.TokenService;
import com.challenge.purchase.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	TokenService tokenService;
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<TokenDto> auth(@RequestBody @Valid AuthForm form) {
		UsernamePasswordAuthenticationToken loginData = form.convert();
		try {
			Authentication authentication = userService.auth(loginData);
			String token = tokenService.generate(authentication);
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
		 
	}
}