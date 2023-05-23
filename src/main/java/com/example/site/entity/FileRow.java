package com.example.site.entity;

public class FileRow {

    private final int id;
    private final String fileHref;
    private final String type;

    public FileRow(String fileHref, String type) {
        this(0, fileHref, type);
    }

    public FileRow(int id, String fileHref, String type) {
        this.id = id;
        this.fileHref = fileHref;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getFileHref() {
        return fileHref;
    }

    public String getType() {
        return type;
    }
}
