package com.challenge.purchase.controller.form.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.challenge.purchase.model.Category;
import com.challenge.purchase.model.Product;
import com.challenge.purchase.repository.CategoryRepository;
import com.challenge.purchase.repository.ProductRepository;

public class ProductEditForm {
	
	@NotNull @NotEmpty
	private String name;
	@NotNull @NotEmpty
	private String description;
	@NotNull @NotEmpty
	private Long categoryId;
	

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}


	public Long getCategoryId() {
		return categoryId;
	}


	public Product update(Long id, ProductRepository productRepository, CategoryRepository categoryRepository) {
		Product product = productRepository.getOne(id);
		
		Category category = categoryRepository.getOne(categoryId);
		
		product.setName(this.name);
		product.setDescription(this.description);
		product.setCategory(category);
		
		return product;
	}
	
}
