package com.example.site.control;

import com.example.site.control.upload.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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
