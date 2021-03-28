	package com.challenge.purchase.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.challenge.purchase.model.User;
import com.challenge.purchase.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService  implements BaseService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${purchase.jwt.secret}")
	private String secret;
	
	@Value("${purchase.jwt.expiration}")
	private String expiration;

	public String generate(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setIssuer("Purchase API")
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(this.generateExpiration())
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	private Date generateExpiration() {
		return new Date(System.currentTimeMillis() + Long.parseLong(expiration) * 1000);
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		
		// Get id user
		return Long.parseLong(claims.getSubject());
	}
	
	public User getUser(Long userId) {
		return userRepository.getOne(userId);
	}
}
