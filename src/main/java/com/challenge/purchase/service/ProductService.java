package com.challenge.purchase.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.purchase.controller.dto.product.ProductDto;
import com.challenge.purchase.controller.form.product.ProductEditForm;
import com.challenge.purchase.controller.form.product.ProductForm;
import com.challenge.purchase.model.Product;
import com.challenge.purchase.repository.CategoryRepository;
import com.challenge.purchase.repository.ProductRepository;
import com.challenge.purchase.repository.UserRepository;

@Service
public class ProductService implements BaseService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	
	public List<ProductDto> listAll() {
		List<Product> products = productRepository.findAll();
		
		logger.info("ListAll products");
		
		return ProductDto.convert(products);
	}

	public Product create(ProductForm form) {
		Product product = form.convert(categoryRepository, userRepository);
		productRepository.save(product);
		
		logger.info("New product inserted - ID: {} - Date: {}", product.getId(), LocalDateTime.now());
		
		return product;
	}
	
	public Optional<Product> getProduct(Long id) {
		logger.info("Get product - ID: {} - Date: {}", id, LocalDateTime.now());
		
		return productRepository.findById(id);
	}
	
	public Product update(Long id, ProductEditForm form) {
		Product product = form.update(id, productRepository, categoryRepository);
		
		logger.info("Product has edited - ID: {} - Date: {}", product.getId(), LocalDateTime.now());
		
		return product;
	}
	
	public void delete(Long id) {
		productRepository.deleteById(id);
		
		logger.info("Product has deleted - ID: {} - Date: {}", id, LocalDateTime.now());
	}
}
