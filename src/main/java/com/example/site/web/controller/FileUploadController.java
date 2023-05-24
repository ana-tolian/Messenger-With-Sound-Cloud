package com.example.site.web.controller;

import com.example.site.data.UserRepository;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/load")
public class FileUploadController  {

    private final MusicService musicService;
    private final BasicService basicService;
    private UserRepository userRepository;


    @Autowired
    public FileUploadController (MusicService musicService, BasicService basicService, UserRepository userRepository) {
        this.musicService = musicService;
        this.basicService = basicService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String sendLoadPage (Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "load";
    }

    @PostMapping
    public String handleSoundtrackUpload (@RequestParam("soundtrack") MultipartFile[] files,
                                   Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        ArrayList<String> fileList = new ArrayList<>();

        musicService.store(files, fileList, user);

        model.addAttribute("user", user);
        model.addAttribute("message", "Вы успешно загрузили следующие файлы:");
        model.addAttribute("files", fileList);

        return "load";
    }

    @PostMapping("/another/image")
    public String handleProfileImageUpload (MultipartHttpServletRequest request,
                                   Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        ArrayList<String> fileList = new ArrayList<>();

        basicService.store(request, fileList, user);
        User newUser = new User(user.getId(),
                        null,
                        null,
                        null,
                        fileList.get(0));
        userRepository.update(newUser);
        model.addAttribute("user", user);

        return "profile-pane";
    }

    @PostMapping("/another/file")
    @ResponseBody
    public String handleMessengerFileUpload (MultipartHttpServletRequest request,
                                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ArrayList<String> fileList = new ArrayList<>();

        basicService.store(request, fileList, user);

        return listToResponseBody(fileList);
    }

    private String listToResponseBody (List<String> list) {
        String body = "";

        for (int i = 0; i < list.size(); i++)
            if (i != 0)
                body += " " + list.get(i);
            else
                body += list.get(i);

        return body;
    }
}
