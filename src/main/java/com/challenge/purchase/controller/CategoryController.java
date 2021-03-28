package com.challenge.purchase.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.purchase.controller.dto.CategoryDetailsDto;
import com.challenge.purchase.controller.dto.CategoryDto;
import com.challenge.purchase.controller.form.CategoryForm;
import com.challenge.purchase.model.Category;
import com.challenge.purchase.repository.CategoryRepository;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public List<CategoryDto> index() {
		List<Category> categories = categoryRepository.findAll();
		return CategoryDto.convert(categories);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> store(@RequestBody @Valid CategoryForm form, UriComponentsBuilder uriBuilder) {
		Category category = form.convert();
		categoryRepository.save(category);
		
		URI uri = uriBuilder.path("/categories/{id}").buildAndExpand(category.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new CategoryDto(category));
	}
	
	@GetMapping("/{id}")
	public CategoryDetailsDto show(@PathVariable Long id) {
		Category category = categoryRepository.getOne(id);
		return new CategoryDetailsDto(category);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody @Valid CategoryForm form) {
		Category category = form.update(id, categoryRepository);
		
		return ResponseEntity.ok(new CategoryDto(category));
	}
}
