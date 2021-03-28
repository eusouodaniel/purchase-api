package com.challenge.purchase.controller.dto.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.challenge.purchase.controller.dto.product.ProductDto;
import com.challenge.purchase.model.Category;

public class CategoryDetailsDto {
	
	private Long id;
	private String name;
	private List<ProductDto> products;
	
	public CategoryDetailsDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.products = new ArrayList<>();
		this.products.addAll(category.getProducts().stream().map(ProductDto::new).collect(Collectors.toList()));
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public List<ProductDto> getProducts() {
		return products;
	}

	public static List<CategoryDto> convert(List<Category> categories) {
		return categories.stream().map(CategoryDto::new).collect(Collectors.toList());
	}
	
}