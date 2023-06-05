package com.example.site.entity;

import com.example.site.data.upload.FilenameParser;
import com.example.site.data.upload.MusicService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileRow {

    private final int id;
    private final String fileHref;
    private final String originalName;
    private final String type;
    private String meta;

    public FileRow(String fileHref, String originalName, String type) {
        this(0, fileHref, originalName, type);
    }

    public FileRow(int id, String fileHref, String originalName, String type) {
        this.id = id;
        this.fileHref = fileHref;
        this.originalName = originalName;
        this.type = type;

        if (type.equals("audio"))
            setAudioMetaData();
    }

    public int getId() {
        return id;
    }

    public String getFileHref() {
        return fileHref;
    }

    public String getOriginalName() {
        System.out.println(originalName + " origin");
        return originalName;
    }

    public String getType() {
        return type;
    }

    public String getFileName () {
        return FilenameParser.getFilenameFromHref(fileHref);
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    private void setAudioMetaData () {
        int dur = MusicService.getAudioDuration(new File(fileHref));
        setMeta(dur + "&" + Soundtrack.convertDurationIntoString(dur));
    }

    public static List<FileRow> getRows (String[] fileHref) {
        List<FileRow> rows = new ArrayList<>();

        if (fileHref == null)
            return rows;

        for (String row : fileHref) {
            String href = row.substring(0, row.indexOf('_'));
            String filename = row.substring(row.indexOf('_') + 1);

            System.out.println("===============================> " + href + " " + filename);
            System.out.println("===============================> " + href + " " + filename);
            System.out.println("===============================> " + href + " " + filename);

            String type = FilenameParser.getFileType(FilenameParser.getExtension(href));

            FileRow fileRow = new FileRow(href, filename, type);

            if (type.equals("audio"))
                fileRow.setAudioMetaData();

            System.out.println("meta " + fileRow.getMeta());

            rows.add(fileRow);
        }

        return rows;
    }
}
