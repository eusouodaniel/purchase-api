package com.challenge.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class NewsService {
	
	@Value("${purchase.news-api.key}")
	private String key;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	NewsRepository newsRepository;
	
	public void getNews() {
		NewsApi newsApi = new NewsApi(key);
		
		List<CategoryDto> categories = categoryService.listAll();

		categories.forEach(category -> {
			RequestBuilder bitcoinRequest = new RequestBuilder()
				    .setQ(category.getName())
				    .setLanguage("pt");

			ApiArticlesResponse apiArti = newsApi.sendEverythingRequest(bitcoinRequest);
			
			NewsForm form = new NewsForm();
			
			form.setSourceName(apiArti.articles().get(0).source().name());
			form.setAuthor(apiArti.articles().get(0).author());
			form.setTitle(apiArti.articles().get(0).title());
			form.setDescription(apiArti.articles().get(0).description());
			form.setUrl(apiArti.articles().get(0).description());
			form.setUrlToImage(apiArti.articles().get(0).urlToImage());
			form.setPublishedAt(apiArti.articles().get(0).publishedAt());
			form.setCategoryId(category.getId());
			
			News news = form.convert(categoryRepository);
			
			newsRepository.save(news);
		});
	}

}

