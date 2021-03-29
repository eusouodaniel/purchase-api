package com.challenge.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;

@Service
@Configuration
@EnableScheduling
public class NewsService {
	
	@Value("${purchase.news-api.key}")
	private String key;
	
	private static final long TIME_DELAY = 1000;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	NewsRepository newsRepository;
	
	@Scheduled(fixedDelay = TIME_DELAY)
	public void getNews() {
		List<CategoryDto> categories = this.getAllCategories();

		categories.forEach(category -> {
			RequestBuilder requestBuilder = this.getNewsByApi(category.getName());
			ApiArticlesResponse responseNews = this.sendRequest(requestBuilder);
			
			NewsForm form = this.mountForm(responseNews, category.getId());
			this.create(form);
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
	
	private NewsForm mountForm(ApiArticlesResponse responseNews, Long categoryId) {
		NewsForm form = new NewsForm();
		
		form.setSourceName(responseNews.articles().get(0).source().name());
		form.setAuthor(responseNews.articles().get(0).author());
		form.setTitle(responseNews.articles().get(0).title());
		form.setDescription(responseNews.articles().get(0).description());
		form.setUrl(responseNews.articles().get(0).description());
		form.setUrlToImage(responseNews.articles().get(0).urlToImage());
		form.setPublishedAt(responseNews.articles().get(0).publishedAt());
		form.setCategoryId(categoryId);
		
		return form;
	}
	
	private News create(NewsForm form) {
		News news = form.convert(categoryRepository);
		
		newsRepository.save(news);
		
		return news;
	}
}

