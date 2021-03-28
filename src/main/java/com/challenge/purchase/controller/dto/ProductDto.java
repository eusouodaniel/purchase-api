package com.challenge.purchase.controller.dto;

import java.time.LocalDateTime;

import com.challenge.purchase.model.Product;

public class ProductDto {
	private Long id;
	private String name;
	private String description;
	private LocalDateTime dateCreation = LocalDateTime.now();
	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.dateCreation = product.getDateCreation();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	
	
}
