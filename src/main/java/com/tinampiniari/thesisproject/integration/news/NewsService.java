package com.tinampiniari.thesisproject.integration.news;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.integration.ows.ForecastService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    private final static String NEWS_SERVICE_URL = "http://newsapi.org/v2/everything?from={date}&sortBy=publishedAt&apiKey={newsApiKey}&q={keyword}";

    private final String newsApiKey;

    private final ForecastService forecastService;

    public NewsService(ForecastService forecastService, ThesisProjectProperties properties) {
        this.newsApiKey = properties.getApi().getNewsApiKey();
        this.forecastService = forecastService;
    }

    public NewsApi getNews(LocalDate date, String keyword) {
        URI url = new UriTemplate(NEWS_SERVICE_URL).expand(date,this.newsApiKey,keyword);
        return forecastService.invoke(url, NewsApi.class);
    }

    public  List<Article> getArticlesList(LocalDate date, String keyword) {
        List<Article> articles = getNews(date, keyword).getArticles();
        return articles;
    }

    public Page<Article> paginateArticles(LocalDate date, String keyword,Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Article> articlesList;

        if (getArticlesList(date,keyword).size() < startItem) {
            articlesList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, getArticlesList(date,keyword).size());
            articlesList = getArticlesList(date,keyword).subList(startItem, toIndex);
        }

        Page<Article> bookPage
                = new PageImpl<Article>(articlesList, PageRequest.of(currentPage, pageSize), getArticlesList(date,keyword).size());

        return bookPage;
    }
}
