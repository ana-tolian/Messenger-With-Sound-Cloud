package com.example.site.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DebugController {

    @GetMapping("/debug")
    public String sendCreationPage () {
        return "debug";
    }
}
