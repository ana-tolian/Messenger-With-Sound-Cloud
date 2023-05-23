package com.example.site.web.controller;

import com.example.site.data.upload.BasicService;
import com.example.site.data.upload.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileDownloadController {

    private final MusicService musicService;
    private final BasicService basicService;


    @Autowired
    public FileDownloadController (MusicService musicService, BasicService basicService) {
        this.musicService = musicService;
        this.basicService = basicService;
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> soundtrackDownloader (@PathVariable String filename) {
        Resource file = musicService.loadAsResource(filename);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());

        return ResponseEntity.ok().headers(header)
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> imageDownloader (@PathVariable String filename) {
        Resource file = basicService.loadAsResource(filename);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());

        String type = filename.substring(filename.lastIndexOf('.') + 1);

        if (type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("jpg"))
            return ResponseEntity.ok().headers(header)
                    .contentType(MediaType.IMAGE_JPEG).body(file);

        else if (type.equalsIgnoreCase("png"))
            return ResponseEntity.ok().headers(header)
                    .contentType(MediaType.IMAGE_PNG).body(file);

        else if (type.equalsIgnoreCase("gif"))
            return ResponseEntity.ok().headers(header)
                    .contentType(MediaType.IMAGE_GIF).body(file);

        else
            return ResponseEntity.ok().headers(header)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);

    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> fileDownloader (@PathVariable String filename) {
        Resource file = basicService.loadAsResource(filename);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());

        return ResponseEntity.ok().headers(header)
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
    }

}