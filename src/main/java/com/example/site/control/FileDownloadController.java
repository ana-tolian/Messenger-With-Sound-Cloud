package com.example.site.control;

import com.example.site.control.upload.MusicService;
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

    private MusicService musicService;

    public FileDownloadController (MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile (@PathVariable String filename) {
        Resource file = musicService.loadAsResource(filename);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());

        return ResponseEntity.ok().headers(header)
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
    }

}