package com.challenge.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.purchase.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
