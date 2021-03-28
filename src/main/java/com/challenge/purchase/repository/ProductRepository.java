package com.challenge.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.purchase.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
