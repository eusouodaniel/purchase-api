package com.challenge.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.challenge.purchase.controller.dto.category.CategoryDto;

import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;

@Service
public class NewsService {
	
	@Value("${purchase.news-api.key}")
	private String key;
	
	@Autowired
	CategoryService categoryService;
	
	public void getNews() {
		NewsApi newsApi = new NewsApi(key);
		
		List<CategoryDto> categories = categoryService.listAll();

		categories.forEach(category -> {
			RequestBuilder bitcoinRequest = new RequestBuilder()
				    .setQ(category.getName())
				    .setLanguage("pt");

			ApiArticlesResponse apiArti = newsApi.sendEverythingRequest(bitcoinRequest);
			
			System.out.println(apiArti.articles().get(0).title());
		});
	}

}

