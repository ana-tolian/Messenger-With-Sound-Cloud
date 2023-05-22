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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/soundtracks")
public class SoundtracksController {

    private final SoundtrackRepository soundtrackRepository;
    private final PlaylistRepository playlistRepository;

    @Autowired
    public SoundtracksController(SoundtrackRepository soundtrackRepository, PlaylistRepository playlistRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping
    public String sendHomePage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
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
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());

        List<Soundtrack> soundtrackList = soundtrackRepository.findByPlaylistId(id);

        if (soundtrackList.isEmpty())
            model.addAttribute("playlist", false);
        else
            model.addAttribute("playlist", true);

        model.addAttribute("soundtracks", soundtrackList);

        return "soundtracks";
    }

}
