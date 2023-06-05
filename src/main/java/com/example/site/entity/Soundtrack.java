package com.example.site.entity;


public class Soundtrack {

    private int id;
    private final String name;
    private final String artist;
    private final String trackHref;
    private final int duration;
    private final String imgHref;
    private final String time;

    public Soundtrack(String name, String artist, String trackHref, int duration, String imgHref) {
        this(0, name, artist, trackHref, duration, imgHref);
    }

    public Soundtrack(int id, String name, String artist, String trackHref, int duration, String imgHref) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.trackHref = trackHref;
        this.duration = duration;
        this.imgHref = imgHref;
        this.time = convertDurationIntoString(duration);
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

    public String getArtist() {
        return artist;
    }

    public String getTrackHref() {
        return trackHref;
    }

    public int getDuration() {
        return duration;
    }

    public String getImgHref() {
        return imgHref;
    }

    public String getTime() {
        return time;
    }

    public static String convertDurationIntoString (int duration) {
        return (duration / 60) + ":" + ((duration % 60 >= 10) ? duration % 60 : "0" + duration % 60);
    }
}
