package com.example.site.repository;

import com.example.site.entity.Soundtrack;

import java.util.List;
import java.util.Optional;

public interface SoundtrackRepository {

    List<Soundtrack> findAll();
    Optional<Soundtrack> findByPlaylistId(String id);
    Soundtrack save(Soundtrack soundtrack);
}
