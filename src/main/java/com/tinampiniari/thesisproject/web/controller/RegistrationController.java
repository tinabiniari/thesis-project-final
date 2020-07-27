package com.tinampiniari.thesisproject.web.controller;


import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.data.repository.UserRepository;
import com.tinampiniari.thesisproject.integration.news.TagsService;
import com.tinampiniari.thesisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    TagsService tagsService;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("user")
    User getUser() {
        return new User();
    }

    @GetMapping
    ModelAndView getMain(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("active","show active");
        return modelAndView;
    }

    @PostMapping
    ModelAndView saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelAndView modelAndView,@RequestParam(value = "checkedTags",required = false) List<Long> checkedTags) {
        if (userService.findByEmail(user.getEmail()) != null) {
            modelAndView.addObject("tags",tagsService.getTags());
            bindingResult.rejectValue("email", "error.user", "This email already exists");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("show","show active");
            modelAndView.addObject("tags",tagsService.getTags());
            return modelAndView;
        }
        if(checkedTags ==  null){
            modelAndView.addObject("show","show active");
            modelAndView.addObject("nullTag","Please choose at least one tag to continue");
            modelAndView.addObject("tags",tagsService.getTags());
            return modelAndView;
        }

        User regUser = userService.saveUser(user);
        user.setTags(checkedTags);
        userRepository.save(user);
        modelAndView.setViewName("register");
        modelAndView.addObject("successMessage", "User " + regUser.getEmail() + " has being successfully registered");
        modelAndView.addObject("show","show active");
        modelAndView.addObject("visible","d-block");
        modelAndView.addObject("labelClass","p-3 bg-light rounded d-flex");
        modelAndView.addObject("iClass","d-block p-1 text-success");
        modelAndView.addObject("tags",tagsService.getTags());
        return modelAndView;
    }




}
