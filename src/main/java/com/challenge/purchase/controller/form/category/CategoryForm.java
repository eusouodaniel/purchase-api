package com.challenge.purchase.controller.form.category;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.challenge.purchase.model.Category;
import com.challenge.purchase.repository.CategoryRepository;


public class CategoryForm {
	
	@NotNull @NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category convert() {
		return new Category(name);
	}

	public Category update(Long id, CategoryRepository categoryRepository) {
		Category category = categoryRepository.getOne(id);
		category.setName(this.name);
		
		return category;
	}
	
	

}
