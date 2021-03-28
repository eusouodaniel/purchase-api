package com.challenge.purchase.service;

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
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	
	public List<ProductDto> listAll() {
		List<Product> products = productRepository.findAll();
		
		return ProductDto.convert(products);
	}

	public Product create(ProductForm form) {
		Product product = form.convert(categoryRepository, userRepository);
		productRepository.save(product);
		
		return product;
	}
	
	public Optional<Product> getProduct(Long id) {
		return productRepository.findById(id);
	}
	
	public Product update(Long id, ProductEditForm form) {
		Product product = form.update(id, productRepository, categoryRepository);
		
		return product;
	}
	
	public void delete(Long id) {
		productRepository.deleteById(id);
	}
}
