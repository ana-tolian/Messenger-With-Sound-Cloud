package com.example.site.web.controller;

import com.example.site.data.PlaylistRepository;
import com.example.site.data.SoundtrackRepository;
import com.example.site.data.upload.MusicService;
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

    @Autowired
    public CreationController (SoundtrackRepository soundtrackRepository, PlaylistRepository playlistRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping
    public String sendCreationPage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("playlistDefaultHref", "/images/noimage.png");
        model.addAttribute("soundtracks", soundtrackRepository.findSoundtracksFromPlaylist(playlistRepository.getMainPlaylist(user)));
        return "create-album";
    }

    @PostMapping
    public String createPlaylist (@RequestParam(value="playlist", required = true) String name,
                                  @RequestParam(value="id") int[] soundtracks,
                                  Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Playlist playlist = new Playlist(name, "/images/noimage.png", user);

        playlist = playlistRepository.save(playlist);

        for (int soundtrackId : soundtracks) {
            playlistRepository.addToPlaylist(playlist, new Soundtrack(soundtrackId, null, null, null, 0, null));
        }

        return "redirect:/albums";
    }
}
