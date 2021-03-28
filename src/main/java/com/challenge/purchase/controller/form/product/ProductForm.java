package com.challenge.purchase.controller.form.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.challenge.purchase.model.Category;
import com.challenge.purchase.model.Product;
import com.challenge.purchase.model.User;
import com.challenge.purchase.repository.CategoryRepository;
import com.challenge.purchase.repository.UserRepository;


public class ProductForm {
	
	@NotNull @NotEmpty
	private String name;
	@NotNull @NotEmpty
	private String description;
	@NotNull @NotEmpty
	private Long categoryId;
	@NotNull @NotEmpty
	private Long sellerId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Product convert(CategoryRepository categoryRepository, UserRepository userRepository) {
		Category category = categoryRepository.getOne(categoryId);
		User seller = userRepository.getOne(sellerId);
		
		return new Product(name, description, category, seller);
	}
	

}
