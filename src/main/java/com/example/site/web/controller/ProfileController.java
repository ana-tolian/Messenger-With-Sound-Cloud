package com.example.site.web.controller;

import com.example.site.data.DialogRepository;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final DialogRepository dialogRepository;

    @Autowired
    public  ProfileController (DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @GetMapping
    public void get (Model model, Authentication authentication) {
        post(model, authentication);
    }

    @PostMapping
    public String post (Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("imgHref", user.getImgHref());

        model.addAttribute("lastMessages", dialogRepository.getDialogsForModel(user));

        return "profile";
    }
}
