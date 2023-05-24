package com.example.site.web.controller;

import com.example.site.data.PlaylistRepository;
import com.example.site.data.SoundtrackRepository;
import com.example.site.data.UserRepository;
import com.example.site.entity.Soundtrack;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/soundtracks")
public class SoundtracksController {

    private final SoundtrackRepository soundtrackRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    @Autowired
    public SoundtracksController(SoundtrackRepository soundtrackRepository,
                                 PlaylistRepository playlistRepository,
                                 UserRepository userRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String sendHomePage (Authentication authentication, Model model) {
        User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
        model.addAttribute("user", user);
        model.addAttribute("playlist", true);
        model.addAttribute("soundtracks", soundtrackRepository
                                                            .findSoundtracksFromPlaylist(
                                                                playlistRepository
                                                                        .getMainPlaylist(user)));
        return "soundtracks";
    }

    @GetMapping("/playlist")
    public String getAlbum (@RequestParam(value = "id", required = true) Integer id,
                            Authentication authentication, Model model) {
        User user = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername());
        model.addAttribute("user", user);

        List<Soundtrack> soundtrackList = soundtrackRepository.findByPlaylistId(id);

        if (soundtrackList.isEmpty())
            model.addAttribute("playlist", false);
        else
            model.addAttribute("playlist", true);

        model.addAttribute("soundtracks", soundtrackList);

        return "soundtracks";
    }

}
