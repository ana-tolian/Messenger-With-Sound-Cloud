package com.example.site.data;

import com.example.site.entity.Playlist;
import com.example.site.entity.Soundtrack;

import java.util.List;

public interface SoundtrackRepository extends Repository<Soundtrack> {

    List<Soundtrack> findSoundtracksFromPlaylist(Playlist playlist);
    List<Soundtrack> findByPlaylistId(int id);

}
