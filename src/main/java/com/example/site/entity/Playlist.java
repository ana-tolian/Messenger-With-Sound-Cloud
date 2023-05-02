package com.example.site.entity;

import java.util.List;


public class Playlist {
    private int id;
    private String name;
    private String imgHref;
    private int count = 0;
    private List<Soundtrack> soundtracks;

    public Playlist(int id, String name, String imgHref) {
        this.id = id;
        this.name = name;
        this.imgHref = imgHref;
        this.soundtracks = null;
    }

    public Playlist(int id, String name, String imgHref, List<Soundtrack> soundtracks) {
        this.id = id;
        this.name = name;
        this.imgHref = imgHref;
        this.soundtracks = soundtracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgHref() {
        return imgHref;
    }

    public void setImgHref(String imgHref) {
        this.imgHref = imgHref;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Soundtrack> getSoundtracks() {
        return soundtracks;
    }

    public void setSoundtracks(List<Soundtrack> soundtracks) {
        this.soundtracks = soundtracks;
    }
}
