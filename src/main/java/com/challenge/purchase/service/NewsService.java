package com.challenge.purchase.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.challenge.purchase.controller.dto.category.CategoryDto;
import com.challenge.purchase.controller.form.news.NewsForm;
import com.challenge.purchase.model.News;
import com.challenge.purchase.repository.CategoryRepository;
import com.challenge.purchase.repository.NewsRepository;

import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;

@Service
@Configuration
@EnableScheduling
public class NewsService implements BaseService {
	
	@Value("${purchase.news-api.key}")
	private String key;
	
	private static final long TIME_DELAY = 21600000;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	NewsRepository newsRepository;
	
	@Scheduled(fixedDelay = TIME_DELAY)
	@Cacheable(value = "listNews")
	public void getNews() {
		List<CategoryDto> categories = this.getAllCategories();

		categories.forEach(category -> {
			RequestBuilder requestBuilder = this.getNewsByApi(category.getName());
			ApiArticlesResponse responseNews = this.sendRequest(requestBuilder);
			
			responseNews.articles().forEach(article -> {
				Optional<News> news = newsRepository.findByTitle(article.title());
				
				if (news.isEmpty()) {
					NewsForm form = this.mountForm(article, category.getId());
					this.create(form);
				}
			});
		});
	}
	
	private List<CategoryDto> getAllCategories() {
		return categoryService.listAll();
	}
	
	private RequestBuilder getNewsByApi(String categoryName) {
		return new RequestBuilder()
			    .setQ(categoryName)
			    .setLanguage("pt");
	}
	
	private ApiArticlesResponse sendRequest(RequestBuilder requestBuilder) {
		NewsApi newsApi = this.connectNewsApi();
		return newsApi.sendEverythingRequest(requestBuilder);
	}
	
	private NewsApi connectNewsApi() {
		return new NewsApi(key);
	}
	
	private NewsForm mountForm(Article article, Long categoryId) {
		NewsForm form = new NewsForm();
		
		form.setSourceName(article.source().name());
		form.setAuthor(article.author());
		form.setTitle(article.title());
		form.setDescription(article.description().substring(0, 30)+"...");
		form.setUrl(article.url());
		form.setUrlToImage(article.urlToImage());
		form.setPublishedAt(article.publishedAt());
		form.setCategoryId(categoryId);
		
		return form;
	}
	
	@CacheEvict(value = "listNews")
	private News create(NewsForm form) {
		News news = form.convert(categoryRepository);
		newsRepository.save(news);
		
		logger.info("Insertion news - ID: {} - Date: {}", news.getId(), LocalDateTime.now());
		
		return news;
	}
}

