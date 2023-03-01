package com.example.site.entity;


public class Soundtrack {

    private int id;
    private String name;
    private String artist;
    private String trackHref;
    private String playlistId;
    private String imgHref;


    public Soundtrack(String name, String artist, String trackHref, String playlistId, String imgHref) {
        this.name = name;
        this.artist = artist;
        this.trackHref = trackHref;
        this.playlistId = playlistId;
        this.imgHref = imgHref;
    }

    public Soundtrack(int id, String name, String artist, String trackHref, String playlistId, String imgHref) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.trackHref = trackHref;
        this.playlistId = playlistId;
        this.imgHref = imgHref;
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
}
