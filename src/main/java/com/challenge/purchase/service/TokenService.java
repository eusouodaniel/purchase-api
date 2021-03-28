package com.challenge.purchase.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.challenge.purchase.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService  implements BaseService {
	
	@Value("${purchase.jwt.secret}")
	private String secret;
	
	@Value("${purchase.jwt.expiration}")
	private String expiration;

	public String generate(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		return Jwts.builder()
				.setIssuer("Purchase API")
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(this.generateExpiration())
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	private Date generateExpiration() {
		Date today = new Date();
		return new Date(today.getTime() + Long.parseLong(expiration));
	}

}
