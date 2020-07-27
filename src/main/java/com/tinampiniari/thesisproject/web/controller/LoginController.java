package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.integration.news.TagsService;
import com.tinampiniari.thesisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    TagsService tagsService;

    @ModelAttribute("user")
    User getUser() {
        return new User();
    }


    @GetMapping("/login")
    ModelAndView getLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("active","show active");
        modelAndView.addObject("tags",tagsService.getTags());
        return modelAndView;
    }

}
