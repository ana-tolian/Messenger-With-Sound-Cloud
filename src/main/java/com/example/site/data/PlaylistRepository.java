package com.example.site.data;

import com.example.site.entity.Playlist;
import com.example.site.entity.Soundtrack;
import com.example.site.entity.User;

import java.util.List;

public interface PlaylistRepository extends Repository<com.example.site.entity.Playlist>{

    List<Playlist> getUserPlaylists(User user);
    Playlist getMainPlaylist(User user);
    void addToPlaylist(Playlist playlist, Soundtrack soundtrack);
}
