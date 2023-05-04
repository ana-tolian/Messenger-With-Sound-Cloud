package com.example.site.web.controller;

import com.example.site.data.UserRepository;
import com.example.site.data.upload.MusicService;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;


@Controller
public class HomeController {

    private final MusicService musicService;

    @Autowired
    public HomeController (MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/")
    public String sendHomePage (Model model) {
        model.addAttribute("soundtracks", musicService.loadAllSoundtracks());

        return "home";
    }

}
