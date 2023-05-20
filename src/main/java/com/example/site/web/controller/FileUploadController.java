package com.example.site.web.controller;

import com.example.site.data.upload.MusicService;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;

@Controller
public class FileUploadController  {

    private final MusicService musicService;

    @Autowired
    public FileUploadController (MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/load")
    public String sendLoadPage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
        return "load";
    }

    @PostMapping("/load")
    public String handleFileUpload(@RequestParam("soundtrack") MultipartFile[] files,
                                   Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        ArrayList<String> fileList = new ArrayList<>();

        musicService.store(files, fileList, user);

        model.addAttribute("imgHref", user.getImgHref());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("message", "Вы успешно загрузили следующие файлы:");
        model.addAttribute("files", fileList);

        return "load";
    }
}
