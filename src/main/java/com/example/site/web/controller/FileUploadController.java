package com.example.site.web.controller;

import com.example.site.data.upload.BasicService;
import com.example.site.data.upload.MusicService;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/load")
public class FileUploadController  {

    private final MusicService musicService;
    private final BasicService basicService;


    @Autowired
    public FileUploadController (MusicService musicService, BasicService basicService) {
        this.musicService = musicService;
        this.basicService = basicService;
    }

    @GetMapping
    public String sendLoadPage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "load";
    }

    @PostMapping
    public String handleSoundtrackUpload(@RequestParam("soundtrack") MultipartFile[] files,
                                   Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        ArrayList<String> fileList = new ArrayList<>();

        musicService.store(files, fileList, user);

        model.addAttribute("user", user);
        model.addAttribute("message", "Вы успешно загрузили следующие файлы:");
        model.addAttribute("files", fileList);

        return "load";
    }

    @PostMapping("/another")
    public String handleFileUpload(@RequestParam("file") MultipartFile[] files,
                                   Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ArrayList<String> fileList = new ArrayList<>();

        basicService.store(files, fileList, user);

        return "redirect:/profile";
    }

    private String listToResponseBody (List<String> list) {
        String body = "";

        for (String str : list)
            body += str + " /n";

        return body;
    }
}
