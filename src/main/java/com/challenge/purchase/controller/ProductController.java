package com.challenge.purchase.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.challenge.purchase.controller.dto.product.ProductDetailsDto;
import com.challenge.purchase.controller.dto.product.ProductDto;
import com.challenge.purchase.controller.form.product.ProductEditForm;
import com.challenge.purchase.controller.form.product.ProductForm;
import com.challenge.purchase.model.Product;
import com.challenge.purchase.repository.CategoryRepository;
import com.challenge.purchase.repository.ProductRepository;
import com.challenge.purchase.repository.UserRepository;

@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<ProductDto> index() {
		List<Product> products = productRepository.findAll();
		return ProductDto.convert(products);
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> store(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder) {
		Product product = form.convert(categoryRepository, userRepository);
		productRepository.save(product);
		
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new ProductDto(product));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDetailsDto> show(@PathVariable Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return ResponseEntity.ok(new ProductDetailsDto(product.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody @Valid ProductEditForm form) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			Product product = form.update(id, productRepository, categoryRepository);
			return ResponseEntity.ok(new ProductDto(product));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
