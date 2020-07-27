package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.data.model.Profile;
import com.tinampiniari.thesisproject.data.model.Tags;
import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.integration.news.TagsService;
import com.tinampiniari.thesisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TagsService tagsService;

    @ModelAttribute("user")
    User getUser() {
        return new User();
    }

    @ModelAttribute("profile")
    Profile getProfile() {
        return new Profile();
    }

    @GetMapping("/myProfile")
    ModelAndView getProfile(Principal principal,ModelAndView modelAndView){
        User user = userService.findByEmail(principal.getName());
        modelAndView.setViewName("myProfile");
        modelAndView.addObject("user",user);
        String emptyChar = "";
        modelAndView.addObject("emptyChar",emptyChar);
        ArrayList<Tags> tags = tagsService.getTagsByUser(user.getUserId());
        modelAndView.addObject("tags",tags);
        return modelAndView;
    }

    @GetMapping("/editProfile")
    ModelAndView editProfile(@ModelAttribute("profile") Profile profile,Principal principal,ModelAndView modelAndView){
        User user = userService.findByEmail(principal.getName());
        modelAndView.setViewName("editProfile");
        modelAndView.addObject("profile",user.getProfile());
        if(user.getProfile().getBirthDate() != null) {
            String birthdate = user.getProfile().getBirthDate().toString();
            modelAndView.addObject("birthDay",getBirthdate(birthdate));

        }
        modelAndView.addObject("user",user);
        modelAndView.addObject("tags",tagsService.getTags());
        modelAndView.addObject("myTags",tagsService.getTagsByUser(user.getUserId()));
        return modelAndView;
    }

    @PostMapping("/editProfile")
    ModelAndView submitProfile(@Valid @ModelAttribute("profile") Profile profile,BindingResult result, HttpServletRequest request, Principal principal, ModelAndView modelAndView){
        User user = userService.findByEmail(principal.getName());
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        modelAndView.setViewName("editProfile");
        if (result.hasErrors()) {
            modelAndView.addObject("user",user);
            modelAndView.addObject("birthDay",request.getParameter("birthDate"));
            return modelAndView;
        }
        Profile savedProfile = userService.updateProfile(user,profile);
        User updatedUser = userService.updateUser(user);

        modelAndView.addObject("profile",savedProfile);
        modelAndView.addObject("user",updatedUser);
        modelAndView.addObject("success", user.getFirstName() + "sucesss");
        return getProfile(principal,modelAndView);
    }

    public String getBirthdate(String birthdate) {
        String[] split = birthdate.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2].substring(0, 2);
        String newBirthdate = day + '-' + month + '-' + year;
        return newBirthdate;
    }

}
