package com.challenge.purchase.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
	private String password;
	@OneToOne(mappedBy = "seller")
	private Sale seller;
	@OneToOne(mappedBy = "buyer")
	private Sale buyer;
	private LocalDateTime dateCreation = LocalDateTime.now();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Sale getSeller() {
		return seller;
	}
	public void setSeller(Sale seller) {
		this.seller = seller;
	}
	public Sale getBuyer() {
		return buyer;
	}
	public void setBuyer(Sale buyer) {
		this.buyer = buyer;
	}
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

}
