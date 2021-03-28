package com.challenge.purchase.controller.dto.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.challenge.purchase.model.Product;

public class ProductDto {
	private Long id;
	private String name;
	private String description;
	private Long categoryId;
	private LocalDateTime dateCreation = LocalDateTime.now();
	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.categoryId = product.getCategory().getId();
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
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public static List<ProductDto> convert(List<Product> products) {
		return products.stream().map(ProductDto::new).collect(Collectors.toList());
	}
}
