package com.challenge.purchase.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.purchase.controller.dto.category.CategoryDto;
import com.challenge.purchase.controller.form.category.CategoryForm;
import com.challenge.purchase.model.Category;
import com.challenge.purchase.repository.CategoryRepository;

@Service
public class CategoryService implements BaseService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryDto> listAll() {
		List<Category> categories = categoryRepository.findAll();
		
		logger.info("ListAll categories");
		
		return CategoryDto.convert(categories);
	}

	public Category create(CategoryForm form) {
		Category category = form.convert();
		categoryRepository.save(category);
		
		logger.info("New category inserted - ID: {} - Date: {}", category.getId(), LocalDateTime.now());
		
		return category;
	}
	
	public Optional<Category> getCategory(Long id) {
		logger.info("Get category");
		
		return categoryRepository.findById(id);
	}
	
	public Category update(Long id, CategoryForm form) {
		Category category = form.update(id, categoryRepository);
		
		logger.info("Category has edited - ID: {} - Date: {}", category.getId(), LocalDateTime.now());
		
		return category;
	}
	
	public void delete(Long id) {
		categoryRepository.deleteById(id);
		logger.info("Category has deleted - ID: {} - Date: {}", id, LocalDateTime.now());
	}
}
