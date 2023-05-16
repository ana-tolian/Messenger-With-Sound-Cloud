package com.example.site.web.controller;

import com.example.site.data.upload.MusicService;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbumController {

    private MusicService musicService;

    @Autowired
    public AlbumController (MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/albums")
    public String sendAlbumsPage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("playlists", musicService.getAllPlaylists());

        return "albums";
    }
}
