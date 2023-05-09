package com.example.site.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Message {

    private final int id;
    private final String content;
    private final String imgHref;
    private final Dialog dialog;
    private final User user;
    private final LocalDateTime date;

    public Message(Dialog dialog) {
        this(0, "", "", dialog, null, null);
    }

    public Message(String content, String imgHref, Dialog dialog, User user, LocalDateTime date) {
        this(0, content, imgHref, dialog, user, date);
    }

    public Message(int id, String content, String imgHref, Dialog dialog, User user, LocalDateTime date) {
        this.id = id;
        this.content = content;
        this.imgHref = imgHref;
        this.dialog = dialog;
        this.user = user;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImgHref() {
        return imgHref;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
