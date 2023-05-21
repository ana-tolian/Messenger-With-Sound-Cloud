package com.example.site.entity;

import java.util.List;


public class Playlist {
    private final int id;
    private final String name;
    private final String imgHref;
    private final User user;
    private int count = 0;
    private List<Soundtrack> soundtracks;


    public Playlist(String name, String imgHref, User user) {
       this(0, name, imgHref, user, null);
    }

    public Playlist(int id, String name, String imgHref, User user) {
        this(id, name, imgHref, user, null);
    }

    public Playlist(int id, String name, String imgHref, User user, List<Soundtrack> soundtracks) {
        this.id = id;
        this.name = name;
        this.imgHref = imgHref;
        this.user = user;
        this.soundtracks = soundtracks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgHref() {
        return imgHref;
    }

    public User getUser() {
        return user;
    }

    public int getCount() {
        return count;
    }

    public List<Soundtrack> getSoundtracks() {
        return soundtracks;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSoundtracks(List<Soundtrack> soundtracks) {
        this.soundtracks = soundtracks;
    }
}
