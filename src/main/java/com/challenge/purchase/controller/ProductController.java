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
import com.challenge.purchase.service.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<ProductDto> index() {
		return productService.listAll();
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> store(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder) {
		Product product = productService.create(form);
		
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new ProductDto(product));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDetailsDto> show(@PathVariable Long id) {
		Optional<Product> product = productService.getProduct(id);
		if (product.isPresent()) {
			return ResponseEntity.ok(new ProductDetailsDto(product.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody @Valid ProductEditForm form) {
		Optional<Product> productOptional = productService.getProduct(id);
		if (productOptional.isPresent()) {
			Product product = productService.update(id, form);
			return ResponseEntity.ok(new ProductDto(product));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Product> product = productService.getProduct(id);
		if (product.isPresent()) {
			productService.delete(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
