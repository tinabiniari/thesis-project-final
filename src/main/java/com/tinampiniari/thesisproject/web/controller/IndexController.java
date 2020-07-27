package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.data.model.Tags;
import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.data.repository.TagsRepository;
import com.tinampiniari.thesisproject.data.repository.UserRepository;
import com.tinampiniari.thesisproject.integration.news.Article;
import com.tinampiniari.thesisproject.integration.news.NewsService;
import com.tinampiniari.thesisproject.integration.news.TagsService;
import com.tinampiniari.thesisproject.integration.ows.*;
import com.tinampiniari.thesisproject.integration.stormglass.HistoryService;
import com.tinampiniari.thesisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IndexController {

    @Autowired
    CurrentService currentService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ThesisProjectProperties projectProperties;

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @Autowired
    ArticlesController articlesController;

    @Autowired
    TagsService tagsService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/index")
    ModelAndView getIndex(ModelAndView modelAndView, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.setViewName("index");
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");
        modelAndView.addObject("lat", lat);
        modelAndView.addObject("lon", lon);
        LocalDate lastMonthNews = LocalDate.now().minusMonths(1);
        if (user != null) {
            modelAndView.addObject("welcomeMessage", "Welcome " + user.getFirstName() + " ");
            ArrayList<Tags> tagMap = tagsService.getTagsByUser(user.getUserId());
            HashMap<String,Page<Article>> articlesByTagsList=new HashMap<>();
            for (Tags tag: tagMap) {
                Page<Article> articlePage = newsService.paginateArticles(lastMonthNews, tag.getTagName(), PageRequest.of(0, 4));
                articlesByTagsList.put(tag.getTagName(),articlePage);
            }
            modelAndView.addObject("tags", tagMap);
            modelAndView.addObject("articlePage", articlesByTagsList);


        }
        else{
            modelAndView.addObject("news", newsService.getNews(lastMonthNews, "κλιματική").getArticles());
            Tags tag = new Tags();
            tag.setId(Long.valueOf(1));
            tag.setTagName("κλιματική");
            ArrayList<Tags> tagMap = new ArrayList<>();
            tagMap.add(tag);
            HashMap<String,Page<Article>> articlesByTagsList=new HashMap<>();
            for (Tags t: tagMap) {
                Page<Article> articlePage = newsService.paginateArticles(lastMonthNews, tag.getTagName(), PageRequest.of(0, 4));
                articlesByTagsList.put(tag.getTagName(),articlePage);
            }
            modelAndView.addObject("articlePage", articlesByTagsList);
        }
        if (lat != null) {
            modelAndView.addObject("cityList",
                    currentService.getCurrentByCoord(Double.valueOf(lat), Double.valueOf(lon)));
        }
        return modelAndView;
    }

    @GetMapping("/tags")
    ModelAndView getTagsPage(ModelAndView modelAndView) {
        modelAndView.setViewName("chooseTags");
        modelAndView.addObject("headerMessage", "Please choose your favourite topics");
        modelAndView.addObject("buttonText", "Submit");
        modelAndView.addObject("tags", tagsService.getTags());
        return modelAndView;
    }

    @PostMapping("/saveTags")
    ModelAndView saveTags(ModelAndView modelAndView, @RequestParam("checkedTags") List<Long> checkedTags) {
        modelAndView.setViewName("register");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        user.setTags(checkedTags);
        userRepository.save(user);
        modelAndView.addObject("headerMessage", "You are successfully registered!");
        return modelAndView;
    }

    @GetMapping("/about")
    ModelAndView modelAndView(ModelAndView modelAndView){
        modelAndView.setViewName("about");
        modelAndView.addObject("title","Welcome to MyWeather");
        return modelAndView;
    }
}
