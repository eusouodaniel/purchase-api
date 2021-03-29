package com.challenge.purchase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.purchase.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {

	Optional<News> findByTitle(String title);
	
}
