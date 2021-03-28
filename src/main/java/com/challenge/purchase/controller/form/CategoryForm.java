package com.challenge.purchase.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.challenge.purchase.model.Category;


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
	
	

}
