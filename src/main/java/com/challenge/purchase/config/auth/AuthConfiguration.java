package com.challenge.purchase.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		
	}
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/products").permitAll()
		.antMatchers(HttpMethod.GET, "/products/*").permitAll()
		.antMatchers(HttpMethod.GET, "/categories").permitAll()
		.antMatchers(HttpMethod.GET, "/categories/*").permitAll()
		.anyRequest().authenticated()
		.and().formLogin();
	}
	
	@Override
	public void configure (WebSecurity web) throws Exception {
		
	}
}

