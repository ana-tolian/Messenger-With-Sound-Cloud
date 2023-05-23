package com.example.site.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Message {

    private final int id;
    private final String content;
    private final List<FileRow> fileHref;
    private final Dialog dialog;
    private final User user;
    private final LocalDateTime date;

    public Message(Dialog dialog) {
        this(0, "", null, dialog, null, null);
    }

    public Message(String content, List<FileRow> fileHref, Dialog dialog, User user, LocalDateTime date) {
        this(0, content, fileHref, dialog, user, date);
    }

    public Message(int id, String content, List<FileRow> fileHref, Dialog dialog, User user, LocalDateTime date) {
        this.id = id;
        this.content = content;
        this.fileHref = fileHref;
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

    public List<FileRow> getFileHref() {
        return fileHref;
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
