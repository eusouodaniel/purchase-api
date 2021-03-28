package com.challenge.purchase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.purchase.controller.dto.category.CategoryDto;
import com.challenge.purchase.controller.form.category.CategoryForm;
import com.challenge.purchase.model.Category;
import com.challenge.purchase.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryDto> listAll() {
		List<Category> categories = categoryRepository.findAll();
		
		return CategoryDto.convert(categories);
	}

	public Category create(CategoryForm form) {
		Category category = form.convert();
		categoryRepository.save(category);
		
		return category;
	}
	
	public Optional<Category> getCategory(Long id) {
		return categoryRepository.findById(id);
	}
	
	public Category update(Long id, CategoryForm form) {
		Category category = form.update(id, categoryRepository);
		
		return category;
	}
	
	public void delete(Long id) {
		categoryRepository.deleteById(id);
	}
}
