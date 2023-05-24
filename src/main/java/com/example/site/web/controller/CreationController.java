package com.example.site.web.controller;

import com.example.site.data.PlaylistRepository;
import com.example.site.data.SoundtrackRepository;
import com.example.site.data.UserRepository;
import com.example.site.entity.Playlist;
import com.example.site.entity.Soundtrack;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/creation")
public class CreationController {

    private final SoundtrackRepository soundtrackRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    @Autowired
    public CreationController (SoundtrackRepository soundtrackRepository,
                               PlaylistRepository playlistRepository,
                               UserRepository userRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String sendCreationPage (Authentication authentication, Model model) {
        User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
        model.addAttribute("user", user);
        model.addAttribute("playlistDefaultHref", "/images/noimage.png");
        model.addAttribute("soundtracks", soundtrackRepository.findSoundtracksFromPlaylist(playlistRepository.getMainPlaylist(user)));
        return "create-album";
    }

    @PostMapping
    public String createPlaylist (@RequestParam(value="playlist", required = true) String name,
                                  @RequestParam(value="id") int[] soundtracks,
                                  Authentication authentication) {
        User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
        Playlist playlist = new Playlist(name, "/images/noimage.png", user);

        playlist = playlistRepository.save(playlist);

        for (int soundtrackId : soundtracks) {
            playlistRepository.addToPlaylist(playlist, new Soundtrack(soundtrackId, null, null, null, 0, null));
        }

        return "redirect:/albums";
    }
}
