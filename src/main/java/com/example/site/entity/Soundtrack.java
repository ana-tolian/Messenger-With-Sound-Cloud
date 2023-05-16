package com.example.site.entity;


public class Soundtrack {

    private int id;
    private String name;
    private String artist;
    private String trackHref;
    private int duration;
    private String playlistId;
    private String imgHref;
    private String time;

    public Soundtrack(String name, String artist, String trackHref, int duration, String playlistId, String imgHref) {
        this(0, name, artist, trackHref, duration, playlistId, imgHref);
    }

    public Soundtrack(int id, String name, String artist, String trackHref, int duration, String playlistId, String imgHref) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.trackHref = trackHref;
        this.duration = duration;
        this.playlistId = playlistId;
        this.imgHref = imgHref;
        this.time = (duration / 60) + ":" + ((duration % 60 >= 10) ? duration % 60 : "0" + duration % 60);
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTrackHref() {
        return trackHref;
    }

    public void setTrackHref(String trackHref) {
        this.trackHref = trackHref;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playListId) {
        this.playlistId = playListId;
    }

    public String getImgHref() {
        return imgHref;
    }

    public void setImgHref(String imgHref) {
        this.imgHref = imgHref;
    }

    public String getTime() {
        return time;
    }
}
