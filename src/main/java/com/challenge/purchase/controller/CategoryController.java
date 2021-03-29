package com.challenge.purchase.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.purchase.controller.dto.category.CategoryDetailsDto;
import com.challenge.purchase.controller.dto.category.CategoryDto;
import com.challenge.purchase.controller.form.category.CategoryForm;
import com.challenge.purchase.model.Category;
import com.challenge.purchase.service.CategoryService;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	@Cacheable(value = "listCategories")
	public List<CategoryDto> index() {
		return categoryService.listAll();
	}
	
	@PostMapping
	@CacheEvict(value = "listCategories")
	public ResponseEntity<CategoryDto> store(@RequestBody @Valid CategoryForm form, UriComponentsBuilder uriBuilder) {
		Category category = categoryService.create(form);
		
		URI uri = uriBuilder.path("/categories/{id}").buildAndExpand(category.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new CategoryDto(category));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDetailsDto> show(@PathVariable Long id) {
		Optional<Category> category = categoryService.getCategory(id);
		if (category.isPresent()) {
			return ResponseEntity.ok(new CategoryDetailsDto(category.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listCategories")
	public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody @Valid CategoryForm form) {
		Optional<Category> categoryOptional = categoryService.getCategory(id);
		if (categoryOptional.isPresent()) {
			Category category = categoryService.update(id, form);
			return ResponseEntity.ok(new CategoryDto(category));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "listCategories")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Category> category = categoryService.getCategory(id);
		if (category.isPresent()) {
			categoryService.delete(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
