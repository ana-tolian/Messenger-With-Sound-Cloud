package com.example.site.data;

import com.example.site.entity.Soundtrack;

import java.util.List;
import java.util.Optional;

public interface SoundtrackRepository extends Repository<Soundtrack> {

    List<Soundtrack> findAll();
    Optional<Soundtrack> findByPlaylistId(String id);
}
