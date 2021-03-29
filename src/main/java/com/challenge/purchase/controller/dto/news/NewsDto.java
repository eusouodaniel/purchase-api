package com.challenge.purchase.controller.dto.news;

import java.util.List;
import java.util.stream.Collectors;

import com.challenge.purchase.model.News;

public class NewsDto {
	
	private Long id;
	private String author;
	private String description;
	private String sourceName;
	
	public NewsDto(News news) {
		this.id = news.getId();
		this.author = news.getAuthor();
		this.description = news.getDescription();
		this.sourceName = news.getSourceName();
	}

	public Long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public String getSourceName() {
		return sourceName;
	}

	public static List<NewsDto> convert(List<News> news) {
		return news.stream().map(NewsDto::new).collect(Collectors.toList());
	}
	
}
