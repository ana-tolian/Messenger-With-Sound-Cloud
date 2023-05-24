package com.example.site.web.controller;

import com.example.site.data.PlaylistRepository;
import com.example.site.data.UserRepository;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    @Autowired
    public AlbumController (PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String sendAlbumsPage (Authentication authentication, Model model) {
        User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
        model.addAttribute("user", user);
        model.addAttribute("main", "Main");
        model.addAttribute("playlists", playlistRepository.getUserPlaylists(user));

        return "albums";
    }

}
