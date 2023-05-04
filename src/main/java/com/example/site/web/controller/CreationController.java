package com.example.site.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreationController {

    @GetMapping("/creation")
    public String sendCreationPage () {
        return "create-album";
    }
}
