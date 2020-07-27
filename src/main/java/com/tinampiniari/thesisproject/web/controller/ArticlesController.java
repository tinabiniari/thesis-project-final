package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.data.model.Tags;
import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.integration.news.Article;
import com.tinampiniari.thesisproject.integration.news.NewsService;
import com.tinampiniari.thesisproject.integration.news.TagsService;
import com.tinampiniari.thesisproject.integration.ows.CurrentService;
import com.tinampiniari.thesisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ArticlesController {

    @Autowired
    CurrentService currentService;

    @Autowired
    ThesisProjectProperties projectProperties;

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @Autowired
    TagsService tagsService;

    @GetMapping("/articles")
    ModelAndView getArticles(ModelAndView modelAndView, @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.setViewName("articles");
        if(user!=null) {
            ArrayList<Tags> tagMap = tagsService.getTagsByUser(user.getUserId());
            modelAndView.addObject("tags", tagMap);
            modelAndView.addObject("title", "Weather News");
            modelAndView.addObject("description", "Please choose one of your saved tags to see the relative articles");
        }
        else {
            LocalDate lastMonthNews = LocalDate.now().minusMonths(1);
            modelAndView.addObject("news",newsService.getNews(lastMonthNews," κλιματική").getArticles());
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(5);
            Page<Article> articlePage = newsService.paginateArticles(lastMonthNews, "κλιματική", PageRequest.of(currentPage - 1, pageSize));
            modelAndView.addObject("articlePage", articlePage);
            int totalPages = articlePage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelAndView.addObject("pageNumbers", pageNumbers);
            }
            modelAndView.addObject("title", "Weather News");
            modelAndView.addObject("description", "See the most relative news about weather and climate");
        }

        return modelAndView;
    }

    @GetMapping("/articles/{tag}")
    ModelAndView getArticlesByTag(ModelAndView modelAndView, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size, @PathVariable("tag") String tag) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.setViewName("articles");
        modelAndView.addObject("title", "Weather News");
        modelAndView.addObject("description", "Check out the latest news");
        ArrayList<Tags> tagMap = tagsService.getTagsByUser(user.getUserId());
        modelAndView.addObject("tags", tagMap);
        LocalDate lastMonthNews = LocalDate.now().minusMonths(1);
        modelAndView.addObject("news",newsService.getNews(lastMonthNews,tag).getArticles());
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Article> articlePage = newsService.paginateArticles(lastMonthNews, tag, PageRequest.of(currentPage - 1, pageSize));
        modelAndView.addObject("articlePage", articlePage);

        int totalPages = articlePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        if(articlePage.getTotalElements() == 0){
            modelAndView.addObject("errorField","Unfortunately, there are no articles about: "+tag);
            modelAndView.addObject("class","my-4 p-2 rounded");
            modelAndView.addObject("style","color:#680e0e;font-size:16px;background:#c1172f36");
        }
        return modelAndView;
    }


}
