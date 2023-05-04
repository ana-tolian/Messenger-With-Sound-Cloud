package com.example.site.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/creation")
public class CreationController {

    @GetMapping
    public String sendCreationPage () {
        return "create-album";
    }
}
