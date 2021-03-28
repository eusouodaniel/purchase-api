package com.challenge.purchase.controller.dto.product;

import java.util.List;
import java.util.stream.Collectors;

import com.challenge.purchase.controller.dto.category.CategoryDto;
import com.challenge.purchase.model.Product;

public class ProductDetailsDto {
	
	private Long id;
	private String name;
	private String description;
	private CategoryDto category;
	
	public ProductDetailsDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.category = new CategoryDto(product.getCategory());
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

	public CategoryDto getCategory() {
		return category;
	}

	public static List<ProductDto> convert(List<Product> products) {
		return products.stream().map(ProductDto::new).collect(Collectors.toList());
	}
	
}