package com.example.site.web.controller;

import com.example.site.data.DialogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchEngineController {

    private final DialogRepository dialogRepository;

    @Autowired
    public SearchEngineController (DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @GetMapping("/dialog")
    public String findDialog (@RequestBody String dialogName,
                              Authentication authentication, Model model) {
        return ""; //TODO
    }

    @GetMapping("/contact")
    public String findContact (@RequestBody String contactName,
                              Authentication authentication, Model model) {
        return ""; //TODO
    }
}
