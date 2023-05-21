package com.example.site.web.controller;

import com.example.site.data.upload.MusicService;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/soundtracks")
public class SoundtracksController {

    private final MusicService musicService;

    @Autowired
    public SoundtracksController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping
    public String sendHomePage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("playlist", false);
        model.addAttribute("soundtracks", musicService.loadAllSoundtracks());
        return "soundtracks";
    }

    @GetMapping("/playlist")
    public String getAlbum (@RequestParam(value = "id", required = true) Integer id,
                            Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("playlist", true);
        model.addAttribute("soundtracks", musicService.loadSoundtrack(id));

        return "soundtracks";
    }

}
