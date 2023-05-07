package com.example.site.entity;

import java.util.Date;

public class Message {

    private final int id;
    private final String content;
    private final String imgHref;
    private final Dialog dialog;
    private final Date date;

    public Message(Dialog dialog) {
        this(0, "", "", dialog, null);
    }

    public Message(String content, String imgHref, Dialog dialog, Date date) {
        this(0, content, imgHref, dialog, date);
    }

    public Message(int id, String content, String imgHref, Dialog dialog, Date date) {
        this.id = id;
        this.content = content;
        this.imgHref = imgHref;
        this.dialog = dialog;
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

    public Date getDate() {
        return date;
    }

}
