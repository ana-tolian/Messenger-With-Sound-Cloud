package com.example.site.control;

import com.example.site.control.upload.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class FileUploadController {

    private final MusicService musicService;

    @Autowired
    public FileUploadController (MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/load")
    public String sendLoadPage () {
        return "load";
    }

    @PostMapping("/load")
    public String handleFileUpload(Model model, @RequestParam("soundtrack") MultipartFile[] files) {
        ArrayList<String> fileList = new ArrayList<>();
        musicService.store(files, fileList);

        model.addAttribute("message", "Вы успешно загрузили следующие файлы:");
        model.addAttribute("files", fileList);

        return "load";
    }
}
