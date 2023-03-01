package com.example.site.control;

import com.example.site.control.upload.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String sendAlbumsPage (Model model) {
        model.addAttribute("playlists", musicService.getAllPlaylists());

        return "albums";
    }
}
