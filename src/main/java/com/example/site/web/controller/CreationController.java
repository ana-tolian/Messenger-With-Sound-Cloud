package com.example.site.web.controller;

import com.example.site.data.upload.MusicService;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/creation")
public class CreationController {

    private final MusicService musicService;

    @Autowired
    public CreationController (MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping
    public String sendCreationPage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("playlistDefaultHref", "/images/noimage.png");
        model.addAttribute("soundtracks", musicService.loadAllSoundtracks());
        return "create-album";
    }
}
