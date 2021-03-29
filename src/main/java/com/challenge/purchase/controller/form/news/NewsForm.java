package com.challenge.purchase.controller.form.news;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.challenge.purchase.model.Category;
import com.challenge.purchase.model.News;
import com.challenge.purchase.repository.CategoryRepository;


public class NewsForm {
	
	@NotNull @NotEmpty
	private String sourceName;
	@NotNull @NotEmpty
	private String author;
	@NotNull @NotEmpty
	private String title;
	@NotNull @NotEmpty
	private String description;
	@NotNull @NotEmpty
	private String url;
	@NotNull @NotEmpty
	private String urlToImage;
	@NotNull @NotEmpty
	private String publishedAt;
	@NotNull @NotEmpty
	private Long categoryId;

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public News convert(CategoryRepository categoryRepository) {
		Category category = categoryRepository.getOne(categoryId);
		
		return new News(sourceName, author, title, description, url, urlToImage, publishedAt, category);
	}
}
