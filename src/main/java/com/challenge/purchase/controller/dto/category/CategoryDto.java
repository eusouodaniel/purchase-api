package com.challenge.purchase.controller.dto.category;

import java.util.List;
import java.util.stream.Collectors;

import com.challenge.purchase.model.Category;

public class CategoryDto {
	
	private Long id;
	private String name;
	
	public CategoryDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public static List<CategoryDto> convert(List<Category> categories) {
		return categories.stream().map(CategoryDto::new).collect(Collectors.toList());
	}
	
}
